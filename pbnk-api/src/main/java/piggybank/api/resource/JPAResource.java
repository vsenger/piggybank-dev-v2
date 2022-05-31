package piggybank.api.resource;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;

@Path("/jpa")
public class JPAResource {
    @Inject
    EntityManager em;

    /*@Path("/new")
    @GET
    @Transactional
    public Transaction getNew(){
        var tx = new Transaction(LocalDateTime.now(),
                BigDecimal.TEN,
                "Pagamentos gerais", new Category("Alcohol"));
        em.persist(tx);
        return tx;
    }*/
}