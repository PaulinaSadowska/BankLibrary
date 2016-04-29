package Products;

import Bank.BankException;
import Operations.ICommand;
import Operations.Operation;
import Products.Balance.Balance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Konto (rachunek bankowy)
 */
public class Account extends Product implements IAccount
{

    public Account(Integer ownerId, Balance balance, Date expireDate, Interest interest)
    {
        super(ownerId, balance, expireDate, interest);
    }


    public void doOperation(ICommand operation) throws Exception
    {
        operation.execute();
        _history.add((Operation) operation);
    }

}