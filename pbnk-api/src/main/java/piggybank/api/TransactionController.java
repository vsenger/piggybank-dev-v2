package piggybank.api;

import piggybank.api.model.Account;
import piggybank.api.model.Category;
import piggybank.api.model.Transaction;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/transaction")
public class TransactionController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Path("new")
    @GET
    @Transactional
    public Transaction getNew(@QueryParam("accountID") long accountId,
                              @QueryParam("categoryID") long categoryId,
                              @QueryParam("description") String description,
                              @QueryParam("amount") BigDecimal amount,
                              @QueryParam("date") String date){
        //#podeArnaldo
        Account account = Account.findById(Long.valueOf(accountId));
        Category category = Category.findById(Long.valueOf(categoryId));
        var tx = new Transaction(LocalDate.parse(date, formatter),
                amount,
                description, category, account);
        tx.persist();
        return tx;
    }
    @Path("findAll")
    @GET
    @Transactional
    public List<Transaction> findAll(){
        return Transaction.findAll().list();
    }

    @Path("find")
    @GET
    @Transactional
    public List<Transaction> findByDescription(@QueryParam("description") String description){
        return Transaction.find("description", description).list();
    }


}
