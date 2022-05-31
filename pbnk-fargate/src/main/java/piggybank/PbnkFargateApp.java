package piggybank;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class PbnkFargateApp {
    public static void main(final String[] args) {
        App app = new App();
        StackProps props = StackProps.builder().build();
        var network = new PBNKNetwork(app,
                "PBNKFgNetwork",
                props);
        var database = new PBNKDatabase(app,
                "PBNKFgDatabase",
                props,
                network);

        var fargateStack = new PbnkFargateStack(app, "PBNKFargate",
                props,
                network,
                database);

        app.synth();
    }
}

