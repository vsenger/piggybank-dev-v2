package piggybank;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.List;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;
@SuppressWarnings("unused")
public class PbnkLambdaStack extends Stack {
    private FunctionUrl fnUrl;

    public PbnkLambdaStack(final Construct scope, final String id) {
        this(scope, id, null, null, null);
    }

    public PbnkLambdaStack(final Construct scope, final String id, final StackProps props, PBNKNetwork network, PBNKDatabase database) {
        super(scope, id, props);
        var stamp = ""+System.currentTimeMillis();
        var env = Map.of(
                "QUARKUS_DATASOURCE_JDBC_URL",database.getJdbcURL(),
                "QUARKUS_DATASOURCE_PASSWORD",database.getPassword(),
                "QUARKUS_DATASOURCE_USERNAME",database.getUsername()
        );

        var adminPolicy = ManagedPolicy.fromManagedPolicyArn(this,
                "AdministratorAccess",
                "arn:aws:iam::aws:policy/AdministratorAccess");


        var labmdaRole = Role.Builder.create(this, "pbnk-fargate-role")
                .assumedBy(ServicePrincipal.Builder.create("lambda.amazonaws.com").build())
                .managedPolicies(List.of(adminPolicy))
                .build();

        var code = Code.fromAsset("./target/function.zip");

        var function = Function.Builder.create(this, "pbnk-lambda")
                .functionName("pbnk-lambda-"+stamp)
                // .vpc(network.getVPC())
                //.allowAllOutbound(true)
                //.allowPublicSubnet(true)
                .timeout(Duration.minutes(5))
                .runtime(Runtime.JAVA_11)
                .role(labmdaRole)
                .environment(env)
                .memorySize(2048)
                .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
                .code(code)
                .build();

        var cors = FunctionUrlCorsOptions.builder()
                .allowedOrigins(List.of("*"))
                .allowedHeaders(List.of("*"))
                .allowedMethods(List.of(HttpMethod.ALL))
                .build();

        fnUrl = FunctionUrl.Builder.create(this, "pbnk-lambda-url")
                .function(function)
                .authType(FunctionUrlAuthType.NONE)
                .cors(cors)
                .build();

        var api = RestApi.Builder.create(this, "pbnk-lambda-api")
                .restApiName("pbnk-lambda-api-"+stamp)
                .build();

        var integration = LambdaIntegration
                .Builder
                .create(function)
                .requestTemplates(java.util.Map.of(   // Map.of is Java 9 or later
                        "application/json", "{ \"statusCode\": \"200\" }"))
                .build();

        api.getRoot().addMethod("GET", integration);

        var outUrl = CfnOutput.Builder.create(this, "pbnk-out-lambda-url")
                .value(fnUrl.getUrl())
                .build();
    }

    public String getFnUrl(){
        return fnUrl.getUrl();
    }
}
