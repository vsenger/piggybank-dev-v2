package piggybank.api.resource.user;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.cache.NoCache;
import io.quarkus.security.identity.SecurityIdentity;

import java.util.Map;
import java.util.Set;

@Path("/user/whoami")
public class WhoAmIResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;
    
    @GET
    @RolesAllowed("user")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String> getWhoAmI() {
        var username = securityIdentity.getPrincipal().getName();
        var roles = securityIdentity.getRoles();
        var subject = jwt.getSubject();
        return Map.of(
                "username", username,
                "roles", roles.toString(),
                "subject", subject
        );

    }

}
