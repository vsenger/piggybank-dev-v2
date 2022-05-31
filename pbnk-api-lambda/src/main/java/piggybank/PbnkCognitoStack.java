package piggybank;

import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.cognito.*;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class PbnkCognitoStack extends Stack {
    public PbnkCognitoStack(final Construct scope, final String id, StackProps props, PBNKNetwork network, PBNKDatabase database) {
        this(scope, id, null, null, null, null);
    }

    public PbnkCognitoStack(final Construct scope, final String id, final StackProps props, PBNKNetwork network, PBNKDatabase database, PbnkLambdaStack lambda) {
        super(scope, id, props);

        var stamp = "" + System.currentTimeMillis();
        var userPool = UserPool.Builder.create(this, "pbnk-user-pool")
                .userPoolName("pbnk-user-pool" )
                .build();

        var resourceServerName = "pbnk-resource-server-" + stamp;

        var resourcesScope = ResourceServerScope.Builder.create()
                .scopeName("resources")
                .scopeDescription("Piggybank Resources")
                .build();

        var resourceServer = UserPoolResourceServer.Builder.create(this, "pbnk-resource-server")
                .userPool(userPool)
                .userPoolResourceServerName(resourceServerName)
                .identifier("resource-server.piggybank")
                .scopes(List.of(resourcesScope))
                .build();

        /*
        var clientCredentials = OAuthFlows.builder()
                .clientCredentials(true)
                .build();
        */

        var authCodeGrant = OAuthFlows.builder()
                .authorizationCodeGrant(true)
                .build();
        
        var resourcesOauthScope = OAuthScope.resourceServer(resourceServer, resourcesScope);

        var callbackUrls = List.of(lambda.getFnUrl() + "/oauth/callback");

        var oauth = OAuthSettings
                .builder()
                .flows(authCodeGrant)
                .callbackUrls(callbackUrls)
                .scopes(List.of(resourcesOauthScope))
                .build();

        var userPoolClient = UserPoolClient.Builder.create(this, "pbnk-client")
                .userPool(userPool)
                .generateSecret(true)
                .oAuth(oauth)
                .build();

        

        var cognitoDomain = CognitoDomainOptions.builder()
                .domainPrefix("piggybank")
                .build();

        var userPoolDomain = UserPoolDomain.Builder.create(this, "pbnk-domain")
                .userPool(userPool)
                .cognitoDomain(cognitoDomain)
                .build();

        CfnOutput.Builder.create(this, "pbnk-client-id")
                .value(userPoolClient.getUserPoolClientId())
                .build();

        CfnOutput.Builder.create(this, "pbnk-cognito-domain")
                .value(userPoolDomain.getDomainName())
                .build();
    }


}
