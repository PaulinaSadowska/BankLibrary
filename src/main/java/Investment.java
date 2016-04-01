import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * stores information about the investment
 */
public class Investment extends Product{

    protected Account baseAccount;

    public Investment(Account baseAccount, BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest){
        this.baseAccount = baseAccount;
        this.balance = amountOfMoney;
        this.creationDate = dateStart;
        this.expireDate = dateEnd;
        this.interest = interest;
    }

    public Account getBaseAccount() {
        return baseAccount;
    }

}
