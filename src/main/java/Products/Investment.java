package Products;

import Operations.Operation;
import Operations.OperationType;
import Products.Balance.Balance;
import Utils.Report;

import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Lokata.
 */
public class Investment extends Product
{
    protected IAccount baseAccount;

    public Investment(Integer ownerId, Balance balance, Date expireDate, Interest interest, IAccount baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        history.add(new Operation(OperationType.OpenInvestment));
        this.baseAccount = baseAccount;

    }

    public IAccount getBaseAccount()
    {
        return baseAccount;
    }

    @Override
    public IProduct accept(Report report)
    {
        return report.visit(this);
    }
}
