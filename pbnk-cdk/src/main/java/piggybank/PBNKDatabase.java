package piggybank;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.SecretValue;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.List;

public class PBNKDatabase extends Stack {
    String jdbcURL;
    String username = "root";
    String password = "Masterkey123";

    public PBNKDatabase(final Construct scope, final String id) {
            this(scope, id, null, null);
        }

    public PBNKDatabase(final Construct scope,
                        final String id,
                        final StackProps props,
                        final PBNKNetwork network) {
        super(scope, id, props);

        var vpc = network.vpc;
        var publicNets = SubnetSelection
                .builder()
                .subnets(network.vpc.getPublicSubnets())
                .build();

        var subnetGroup = SubnetGroup.Builder.create(this, "pbnk-net-grp")
                .vpc(network.vpc)
                .description("piggybank net group")
                .vpcSubnets(publicNets)
                .build();

        var mysql8 = DatabaseInstanceEngine.mysql(
                MySqlInstanceEngineProps
                        .builder()
                        .version(MysqlEngineVersion.VER_8_0)
                        .build()
        );

        var creds = Credentials
                .fromPassword(username,
                        SecretValue.unsafePlainText(password));

        var databaseName = "pbnkdb";
        var databaseOpts = "?useSSL=false";

        var dbSG = SecurityGroup.Builder.create(this, "pbnk-rds-sg")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();

        dbSG.addIngressRule(Peer.anyIpv4(), Port.tcp(3306), "Allow MySQL IN");

        var rds = DatabaseInstance.Builder.create(this, "pbnk-rds")
                .vpc(vpc)
                .subnetGroup(subnetGroup)
                .securityGroups(List.of(dbSG))
                .engine(mysql8)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MEDIUM))
                .credentials(creds)
                .multiAz(false)
                .databaseName(databaseName)
                .publiclyAccessible(true)
                .build();

        CfnOutput outVpcId = CfnOutput.Builder.create(this, "instanceIdentifier")
                .value(rds.getInstanceIdentifier())
                //.exportName("PBNK:RDS:ID")
                .build();

        var instanceEndpointAddress = rds.getDbInstanceEndpointAddress();
        var instanceEndpointPort = rds.getDbInstanceEndpointPort();

        CfnOutput instanceEndpointAddressOut = CfnOutput.Builder.create(this, "instanceEndpointAddress")
                .value(instanceEndpointAddress)
        //        .exportName("PBNK:RDS:ADDR")
                .build();

        var cliHelpout = "mysql -h%s -P%s -u%s -p %s".formatted(
                instanceEndpointAddress,
                instanceEndpointPort,
                username,
                databaseName);

        CfnOutput cliHelpoutOut = CfnOutput.Builder.create(this, "pbnk-rds-cliHelpout")
                .value(cliHelpout)
                .build();

        jdbcURL = "jdbc:mysql://" + instanceEndpointAddress
                + ":"
                + instanceEndpointPort
                + "/"
                + databaseName
                + databaseOpts;

    }

    public String getJdbcURL(){
        return jdbcURL;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

}
