package Products;

import Bank.BankException;
import Operations.Operation;
import Operations.OperationType;
import Products.Account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Kredyt.
 */
public class Loan extends Product
{

    private Account _baseAccount;

    public Loan(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        _history.add(new Operation(OperationType.MakeLoan));
        _baseAccount = baseAccount;
    }

    public Account getBaseAccount()
    {
        return _baseAccount;
    }
}
