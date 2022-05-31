package piggybank;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class PbnkLambdaApp {
    @SuppressWarnings("unused")
    public static void main(final String[] args) {
        App app = new App();
        StackProps props = StackProps.builder().build();

        var network = new PBNKNetwork(app,
                "PBNKLambdaNetwork",
                props);

        var database = new PBNKDatabase(app,
                "PBNKLambdaDatabase",
                props,
                network);

       var lambda = new PbnkLambdaStack(app, 
                "PBNKLambda", 
                props, 
                network, 
                database);
        
        var cognito = new PbnkCognitoStack(app,
                "PBNKCognito",
                props,
                network,
                database,
                lambda);
                

        app.synth();
    }
}

