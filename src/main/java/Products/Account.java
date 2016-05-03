package Products;

import Operations.ICommand;
import Operations.Operation;
import Products.Balance.Balance;
import Utils.Report;

import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * Konto (rachunek bankowy)
 */
public class Account extends Product implements IAccount
{
    private Integer bankId;

    public Account(Integer ownerId, Balance balance, Date expireDate, Interest interest, Integer bankId)
    {
        super(ownerId, balance, expireDate, interest);
        this.bankId = bankId;
    }

    @Override
    public int getBankId() {
        return bankId;
    }

    public void _setBankId(int bankId) { this.bankId = bankId; }

    public void doOperation(ICommand operation) throws Exception
    {
        operation.execute();
        history.add((Operation) operation);
    }

    @Override
    public IProduct accept(Report report)
    {
        return report.visit(this);
    }
}