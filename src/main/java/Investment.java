import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * stores information about the investment
 */
public class Investment extends Product{

    protected Account _baseAccount;

    public Investment(BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        super(balance, expireDate, interest);
        this._baseAccount = baseAccount;

    }

    public Account getBaseAccount() {
        return _baseAccount;
    }

}
