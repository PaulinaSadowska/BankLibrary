package Products;

import Operations.Operation;
import Operations.OperationType;
import Products.Balance.Balance;
import Report.Report;

import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Kredyt.
 */
public class Loan extends Product
{

    private IAccount baseAccount;

    public Loan(Integer ownerId, Balance balance, Date expireDate, Interest interest, IAccount baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        history.add(new Operation(OperationType.MakeLoan));
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
