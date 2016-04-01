import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductFactory {

    public Product createLoan(Account baseAccount, BigDecimal balance, Date expireDate, Interest interest){
        return createProduct(ProductType.Account, null, balance, expireDate, interest, -1, null);
    }


    public Product createInvestment(Account baseAccount, BigDecimal balance, Date expireDate, Interest interest){
        return createProduct(ProductType.Account, null, balance, expireDate, interest, -1, null);
    }

    public Product createAccount(BigDecimal balance, Date expireDate, Interest interest,
                                 int ownerId, BigDecimal debit) {
        return createProduct(ProductType.Account, null, balance, expireDate, interest, ownerId, debit);
    }

    private Product createProduct(ProductType type, Account baseAccount, BigDecimal balance, Date expireDate, Interest interest,
                                 int ownerId, BigDecimal debit){

        OperationsHistory history = new OperationsHistory();

        if (balance == null || expireDate == null || interest == null)
            throw new NullPointerException();

        if(type!=ProductType.Account && baseAccount == null)
            throw new NullPointerException();

        if(type==ProductType.Account && debit == null)
            throw new NullPointerException();

        if((new Date()).after(expireDate))
            throw new IllegalArgumentException();

        if(type==ProductType.Account && ownerId == -1)
            throw new IllegalArgumentException();

        switch(type){
            case Loan:
                return new Loan(balance, expireDate, interest, baseAccount);
            case Account:
                return new Account(balance, expireDate, interest, ownerId, history, new Debit(debit));
            case Investment:
                return new Investment(balance, expireDate, interest, baseAccount);
        }
        return null;
    }

}
