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
        this._balance = amountOfMoney;
        this._creationDate = dateStart;
        this._expireDate = dateEnd;
        this._interest = interest;
    }

    public Account getBaseAccount() {
        return baseAccount;
    }

}
