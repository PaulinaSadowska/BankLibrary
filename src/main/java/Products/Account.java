package Products;

import Operations.ICommand;
import Operations.Operation;
import Products.Balance.Balance;

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
        history.add((Operation) operation);
    }

}