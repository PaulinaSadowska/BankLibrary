import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Lokata.
 */
public class Investment extends Product
{

    protected Account _baseAccount;

    public Investment(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        _history.add(new Operation(OperationType.OpenInvestment));
        this._baseAccount = baseAccount;

    }

    public void close()
    {
        _history.add(new Operation(OperationType.CloseInvestment));

        BigDecimal initialBalance = _baseAccount.getBalance();
        BigDecimal interest = new BigDecimal(0);

        if(Calendar.getInstance().getTime().after(_expireDate))
            interest = _interest.calculateInterest(this);

        _baseAccount.setBalance(initialBalance.add(_balance.add(interest)));

    }
}
