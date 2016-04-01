import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductFactory {

    public Loan createLoan(Account baseAccount, BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest){

        if(baseAccount==null || amountOfMoney == null || dateStart == null || dateEnd == null || interest == null)
            throw new NullPointerException();

        if(dateStart.after(dateEnd))
            throw new IllegalArgumentException();


        return new Loan(baseAccount, amountOfMoney, dateStart, dateEnd, interest);
    }


    public Investment createInvestment(Account baseAccount, BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest){

        if(baseAccount==null || amountOfMoney == null || dateStart == null || dateEnd == null || interest == null)
            throw new NullPointerException();

        if(dateStart.after(dateEnd))
            throw new IllegalArgumentException();


        return new Investment(baseAccount, amountOfMoney, dateStart, dateEnd, interest);
    }

    public Account createAccount(BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest,
                                 int id, String owner, OperationManager manager, OperationsHistory history){

        if(amountOfMoney == null || dateStart == null || dateEnd == null || interest == null
                || owner==null || manager == null || history == null)
            throw new NullPointerException();

        if(dateStart.after(dateEnd))
            throw new IllegalArgumentException();


        return new Account(amountOfMoney, dateStart, dateEnd, interest, id, history);
    }

}
