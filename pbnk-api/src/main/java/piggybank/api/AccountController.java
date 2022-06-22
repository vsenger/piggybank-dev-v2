package piggybank.api;

import piggybank.api.model.Account;
import piggybank.api.model.Transaction;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/account")
public class AccountController {
    String password="Ru!R*4tXwS3GsLam/QXYe*34lTz7M5DXA"
    @Path("new")
    @GET
    @Transactional
    public Account getNewAccount(){
        var tx = new Account("nubank");
        tx.persist();
        return tx;
    }

    @Path("findAll")
    @GET
    @Transactional
    public List<Account> findAll(){
        return Account.findAll().list();
    }

    @Path("processBalance")
    @GET
    @Transactional
    public List<Transaction> processBalance(@QueryParam("accountID") Long accountId){
        var account = Account.findById(Long.valueOf(accountId));
        return Transaction.find("account", account).list();
    }
}
