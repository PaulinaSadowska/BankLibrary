package Products;

import Operations.Operation;
import Operations.OperationType;
import Products.Account;

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

    public Account getBaseAccount()
    {
        return _baseAccount;
    }

}
