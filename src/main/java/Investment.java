import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Lokata.
 */
public class Investment extends Product implements IClosable
{

    protected Account _baseAccount;

    public Investment(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        this._baseAccount = baseAccount;

    }

    public boolean close()
    {
        _history.add(new Operation(OperationType.CloseInvestment, this));

        BigDecimal initialBalance = _baseAccount.getBalance();
        BigDecimal interest = null;

        if(Calendar.getInstance().after(_expireDate))
            interest = _interest.calculateInterest(this);

        _baseAccount.setBalance(initialBalance.add(_balance.add(interest)));

        return true;
    }
}
