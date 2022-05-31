package piggybank;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplicationVersion;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
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
        var instanceProfile = appStack.getInstanceProfile();
        var vpc = netwStack.getVPC();

        var srcBundle = CfnApplicationVersion.SourceBundleProperty.builder()
                .s3Bucket("ap-northeast-1.piggybank.caravana.cloud")
                .s3Key("pbnk-api/dockerrun.aws.json")
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
                webSGOpt);

        var ebEnvName = "pbnk-eb-env-"+stamp;
        var ebEnv = CfnEnvironment.Builder
                .create(this, "pbnk-eb-ecs-env")
                .applicationName(ebEnvName)
                .applicationName(appStack.getEbApp().getApplicationName())
                .versionLabel(appVersion.getRef())
                .optionSettings(opts)
                .solutionStackName("64bit Amazon Linux 2 v3.1.1 running ECS")
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
