package piggybank;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.SecretValue;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.elasticbeanstalk.CfnApplication;
import software.amazon.awscdk.services.elasticbeanstalk.CfnEnvironment;
import software.amazon.awscdk.services.iam.CfnInstanceProfile;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.List;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class PBNKNetwork extends Stack {
    private final SecurityGroup webSG;
    Vpc vpc;

    public PBNKNetwork(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public PBNKNetwork(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
    SubnetConfiguration pubNets = SubnetConfiguration.builder()
            .cidrMask(24)
            .name("pbnk-net-pub")
            .subnetType(SubnetType.PUBLIC)
            .build();

    // The code that defines your stack goes here
    vpc = Vpc.Builder.create(this, "pbnk-vpc")
            .cidr("10.0.0.0/16")
            .maxAzs(3)
            .defaultInstanceTenancy(DefaultInstanceTenancy.DEFAULT)
            .enableDnsSupport(true)
            .enableDnsHostnames(true)
            .subnetConfiguration(List.of(pubNets))
            .build();

    CfnOutput outVpcId = CfnOutput.Builder.create(this, "pbnk-out-vpcId")
            .value(vpc.getVpcId())
            //.exportName("PBNK:VPC:ID")
            .build();

    webSG = SecurityGroup.Builder.create(this, "pbnk-sg-web")
            .vpc(vpc)
            .build();

    webSG.addIngressRule(Peer.anyIpv4(), Port.allTcp());
    }

    public Vpc getVPC(){
        return vpc;
    }

    public SecurityGroup getWebSG() {
        return webSG;
    }
}
