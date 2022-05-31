package piggybank;

import software.amazon.awscdk.services.elasticbeanstalk.CfnApplication;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.iam.CfnInstanceProfile;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.List;

public class PbnkEbStack extends Stack {
    private CfnApplication ebApp;
    private CfnInstanceProfile instanceProfile;
    private Bucket appBucket;

    public PbnkEbStack(final Construct scope, final String id) {
        this(scope, id, null, null, null);
    }

    public PbnkEbStack(final Construct scope,
                       final String id,
                       final StackProps props,
                       final PBNKNetwork netwStack,
                       final PBNKDatabase dbStack
                       ) {
        super(scope, id, props);

        // The code that defines your stack goes here
        var vpc = netwStack.getVPC();
        ebApp = CfnApplication.Builder.create(this, "pbnk-eb-ecs-app")
                .applicationName("pbnk-eb-app")
                .build();

        var admin = ManagedPolicy.fromManagedPolicyArn(this,
                "pbnk-s3-full",
                "arn:aws:iam::aws:policy/AdministratorAccess");

        var instanceRole = Role.Builder.create(this, "pbnk-role")
                .assumedBy(ServicePrincipal.Builder.create("ec2.amazonaws.com").build())
                .managedPolicies(List.of(admin))
                .build();

        var stamp = System.currentTimeMillis();
        var instanceProfileName = "pbnk-iprofile-"+stamp;
        instanceProfile = CfnInstanceProfile.Builder.create(this,
                        "pbnk-iprofile")
                .instanceProfileName(instanceProfileName)
                .roles(List.of(instanceRole.getRoleName()))
                .build();

        instanceProfile.getNode().addDependency(instanceRole);

        appBucket = Bucket.Builder.create(this, "pbnk-app-ecs-bucket")
                .build();
    }

    public CfnInstanceProfile getInstanceProfile() {
        return instanceProfile;
    }

    public CfnApplication getEbApp() {
        return ebApp;
    }

    public Bucket getAppBucket() {
        return appBucket;
    }
}
