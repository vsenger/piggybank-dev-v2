package piggybank;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplicationVersion;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.iam.CfnInstanceProfile;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;

import java.util.List;

public class PbnkEbEnv extends Stack {
    public PbnkEbEnv(final Construct scope, final String id) {
        this(scope, id, null, null, null, null);
    }

    public PbnkEbEnv(final Construct scope,
                       final String id,
                       final StackProps props,
                       final PBNKNetwork netwStack,
                       final PBNKDatabase dbStack,
                       final PbnkEbStack appStack

    ) {
        super(scope, id, props);
        var stamp = "" + System.currentTimeMillis();


        var adminPolicy = ManagedPolicy.fromManagedPolicyArn(this,
                "AdministratorAccess",
                "arn:aws:iam::aws:policy/AdministratorAccess");

        var ebRole = Role.Builder.create(this, "pbnk-fargate-role")
                .assumedBy(ServicePrincipal.Builder.create("ec2.amazonaws.com").build())
                .managedPolicies(List.of(adminPolicy))
                .build();

        var instanceProfileName = "pbnk-eb-iprofile-"+stamp;

        var instanceProfile = CfnInstanceProfile.Builder.create(this,
                        "pbnk-eb-iprofile")
                .instanceProfileName(instanceProfileName)
                .roles(List.of(ebRole.getRoleName()))
                .build();

        var vpc = netwStack.getVPC();

        var srcBundle = CfnApplicationVersion.SourceBundleProperty.builder()
                .s3Bucket(appStack.getAppBucket().getBucketName())
                .s3Key("docker-compose.yml")
                .build();

        var versionName = "l"+stamp;
        var appVersion = CfnApplicationVersion.Builder.create(this, "pbnk-version")
                .applicationName(appStack.getEbApp().getApplicationName())
                .sourceBundle(srcBundle)
                .description(versionName)
                .build();

        var instanceType = CfnEnvironment.OptionSettingProperty.builder()
                .namespace("aws:autoscaling:launchconfiguration")
                .optionName("InstanceType")
                .value("t3.medium")
                .build();

        var instanceProfileOpt = option(
                "aws:autoscaling:launchconfiguration",
                "IamInstanceProfile",
                instanceProfile.getInstanceProfileName());

        var webSGOpt = option(
                "aws:autoscaling:launchconfiguration",
                "SecurityGroups",
                netwStack.getWebSG().getSecurityGroupId());

        var vpcOpt = option("aws:ec2:vpc",
                "VPCId",
                vpc.getVpcId());

        var subnetIds = vpc.getPublicSubnets()
                .stream()
                .map(s -> s.getSubnetId())
                .toList();

        var subnetIdsStr = String.join(",",subnetIds);

        var subnetsOpt = option("aws:ec2:vpc",
                "Subnets",
                subnetIdsStr);

        var publicIpsOpt = option("aws:ec2:vpc",
                "AssociatePublicIpAddress",
                "true");

        var jdbcURL = option("aws:elasticbeanstalk:application:environment",
                "QUARKUS_DATASOURCE_JDBC_URL",
                dbStack.getJdbcURL());

        var jdbcUsername = option("aws:elasticbeanstalk:application:environment",
                "QUARKUS_DATASOURCE_USERNAME",
                dbStack.getUsername());

        var jdbcPassword = option("aws:elasticbeanstalk:application:environment",
                "QUARKUS_DATASOURCE_PASSWORD",
                dbStack.getPassword());

        var envPort = option("aws:elasticbeanstalk:application:environment",
                "PORT",
                "13372");

        var noProxy = option(
                "aws:elasticbeanstalk:environment:proxy",
                "ProxyServer",
                "none");

        var opts = List.of(
                instanceType,
                instanceProfileOpt,
                vpcOpt,
                subnetsOpt,
                publicIpsOpt,
                jdbcURL,
                jdbcUsername,
                jdbcPassword,
                envPort,
                webSGOpt,
                noProxy);

        var ebEnvName = "pbnk-eb-env-"+stamp;
        var ebEnv = CfnEnvironment.Builder
                .create(this, "pbnk-eb-env")
                .applicationName(ebEnvName)
                .applicationName(appStack.getEbApp().getApplicationName())
                .versionLabel(appVersion.getRef())
                .optionSettings(opts)
                .solutionStackName("64bit Amazon Linux 2 v3.4.14 running Docker")
                .build();

        ebEnv.addDependsOn(appStack.getEbApp());
        ebEnv.addDependsOn(instanceProfile);

    }

    private CfnEnvironment.OptionSettingProperty option(String namespace,
                                                        String optionName,
                                                        String value) {
        return CfnEnvironment.OptionSettingProperty.builder()
                .namespace(namespace)
                .optionName(optionName)
                .value(value)
                .build();

    }
}
