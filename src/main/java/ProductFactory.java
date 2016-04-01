import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductFactory {

    public Product createLoan(Account baseAccount, BigDecimal amountOfMoney, Date expireDate, Interest interest){

        if(baseAccount==null || amountOfMoney == null || expireDate == null || interest == null)
            throw new NullPointerException();

        if((new Date()).after(expireDate))
            throw new IllegalArgumentException();


        return new Loan(amountOfMoney, expireDate, interest, baseAccount);
    }


    public Product createInvestment(Account baseAccount, BigDecimal amountOfMoney, Date expireDate, Interest interest){

        if(baseAccount==null || amountOfMoney == null || expireDate == null || interest == null)
            throw new NullPointerException();

        if((new Date()).after(expireDate))
            throw new IllegalArgumentException();


        return new Investment(amountOfMoney, expireDate, interest, baseAccount);
    }

    public Product createAccount(BigDecimal balance, Date expireDate, Interest interest,
                                 int ownerId, BigDecimal debit)
    {

        //TODO: Czy nie sadzisz, ze fabryka powinna ulatwiac tworzenie obiektow a nie tylko dodawac opakowanie na konstruktor?
        // - TAK - 

        OperationsHistory history = new OperationsHistory();

        if (balance == null || expireDate == null || interest == null)
            throw new NullPointerException();

        if((new Date()).after(expireDate))
            throw new IllegalArgumentException();


        return new Account(balance, expireDate, interest, ownerId, history, new Debit(debit));
    }

}
