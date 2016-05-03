package Report;

import Products.*;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class PassAllReport extends Report
{
    @Override
    public IProduct visit(Account account)
    {
        return  account;
    }

    @Override
    public IProduct visit(DebitAccount debitAccount)
    {
        return debitAccount;
    }

    @Override
    public IProduct visit(Loan loan)
    {
        return loan;
    }

    @Override
    public IProduct visit(Investment investment)
    {
        return investment;
    }
}
