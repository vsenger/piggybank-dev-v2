package piggybank;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class PbnkEbApp {
    public static void main(final String[] args) {
        App app = new App();
        StackProps props = StackProps.builder().build();
        var network = new PBNKNetwork(app,
                "PBNKEbNetwork",
                props);
        var database = new PBNKDatabase(app,
                "PBNKEbDatabase",
                props,
                network);
        var ebApp = new PbnkEbStack(app,
                "PBNKEbApp",
                props,
                network,
                database
        );
        var ebEnv = new PbnkEbEnv(app, "PBNKEbEnv",
                props,
                network,
                database,
                ebApp);
        app.synth();
    }
}

