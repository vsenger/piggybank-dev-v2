package piggybank.api.resource;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.sql.SQLException;

@Path("/ds")
public class DatasourceResource {
    @Inject
    DataSource datasource;

    @GET
    public String getDS() {
        try (var con = datasource.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery("SELECT 1+1")) {
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new WebApplicationException(e);
        }
    }
}
