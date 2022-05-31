package piggybank.api;

import piggybank.api.model.Category;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;
//aaa
@Path("/category")
public class CategoryController {
    @Path("new")
    @GET
    @Transactional
    public Category getNewCategory(){
        var tx = new Category("teste");
        tx.persist();
        return tx;
    }

    @Path("findAll")
    @GET
    @Transactional
    public List<Category> findAll(){
        return Category.findAll().list();
    }

    @Path("find")
    @GET
    @Transactional
    public List<Category> findByDescription(@QueryParam("description") String description){
        return Category.find("description", description).list();
    }

}
