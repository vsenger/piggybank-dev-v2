package piggybank;

import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ecr.assets.DockerImageAsset;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.*;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.List;
import java.util.Map;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class PbnkFargateStack extends Stack {
    public PbnkFargateStack(final Construct scope, final String id, StackProps props) {
        this(scope, id, null, null, null);
    }

    public PbnkFargateStack(final Construct scope, final String id, final StackProps props, PBNKNetwork network, PBNKDatabase database) {
        super(scope, id, props);
        var vpc = network.getVPC();

        var adminPolicy = ManagedPolicy.fromManagedPolicyArn(this,
                "AdministratorAccess",
                "arn:aws:iam::aws:policy/AdministratorAccess");


        var fargateRole = Role.Builder.create(this, "pbnk-fargate-role")
                .assumedBy(ServicePrincipal.Builder.create("ec2.amazonaws.com").build())
                .managedPolicies(List.of(adminPolicy))
                .build();

        var cluster = Cluster.Builder.create(this, "pbnk-api-cluster")
                .vpc(vpc)
                .build();

        // Create a load-balanced Fargate service and make it public
        var imageName="caravanacloud/pbnk-api";

        var env = Map.of(
                "QUARKUS_DATASOURCE_JDBC_URL",database.getJdbcURL(),
                "QUARKUS_DATASOURCE_PASSWORD",database.getPassword(),
                "QUARKUS_DATASOURCE_USERNAME",database.getUsername()
        );

        var imgOpts =
                ApplicationLoadBalancedTaskImageOptions.builder()
                        .image(ContainerImage.fromRegistry(imageName))
                        .environment(env)
                        .build();

        var hc = HealthCheck
                .builder()
                .path("/api/")
                .port("13372")
                .build();

        var lb = ApplicationLoadBalancer.Builder.create(this, "pbnk-api-alb")
                .vpc(vpc)
                .build();

        var stamp = "" + System.currentTimeMillis();
        var tg = ApplicationTargetGroup.Builder.create(this, "pbnk-api-atg")
                .targetGroupName("pbnk-api-atg-"+stamp)
                .vpc(vpc)
                .port(13372)
                .protocol(ApplicationProtocol.HTTP)
                .targetType(TargetType.IP)
                .healthCheck(hc)
                .build();

        /*
        var listener = ApplicationListener.Builder.create(this, "pbnk-api-listener")
                .loadBalancer(lb)
                .protocol(ApplicationProtocol.HTTP)
                .defaultTargetGroups(List.of(tg))
                .build();
        */

        var cpu = 2048;
        var mem = 8192;

        var task = FargateTaskDefinition.Builder.create(this, "pbnk-api-task")
                .taskRole(fargateRole)
                .cpu(cpu)
                .memoryLimitMiB(mem)
                .build();


        var containerImage = RepositoryImage.fromRegistry("caravanacloud/pbnk-api");

        var p13372 = PortMapping.builder()
                .containerPort(13372)
                .build();

        var container = ContainerDefinitionOptions
                .builder()
                .image(containerImage)
                .cpu(cpu)
                .memoryLimitMiB(mem)
                .portMappings(List.of(p13372))
                .environment(env)
                .build();

        task.addContainer("pbnk-api", container);

        var fargateSG = SecurityGroup.Builder.create(this, "pbnk-sg-fargate-web")
                .vpc(vpc)
                .build();

        fargateSG.addIngressRule(Peer.anyIpv4(), Port.allTcp());

    var service = ApplicationLoadBalancedFargateService.Builder.create(this, "MyFargateService")
                .cluster(cluster)           // Required
                .cpu(cpu)                   // Default is 256
                .desiredCount(1)
                .memoryLimitMiB(mem)       // Default is 512
                .loadBalancer(lb)
                .taskDefinition(task)
                .securityGroups(List.of(fargateSG))
                .assignPublicIp(true)
                .publicLoadBalancer(false)// Default is false
                .build();
    }
}
