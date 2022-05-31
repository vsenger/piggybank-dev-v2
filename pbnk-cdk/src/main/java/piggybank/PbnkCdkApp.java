package piggybank;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class PbnkCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        StackProps props = StackProps.builder().build();
        var network = new PBNKNetwork(app, "PBNKNetwork", props);
        var database = new PBNKDatabase(app, "PBNKDatabase", props, network);
        app.synth();
    }
}

