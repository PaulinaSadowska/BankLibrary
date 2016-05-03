package Utils;

import Products.*;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class PassAllReport implements Visitor
{
    @Override
    public IProduct visit(Account account)
    {
        return (IProduct) this;
    }

    @Override
    public IProduct visit(DebitAccount debitAccount)
    {
        return (IProduct) this;
    }

    @Override
    public IProduct visit(Loan loan)
    {
        return (IProduct) this;
    }

    @Override
    public IProduct visit(Investment investment)
    {
        return (IProduct) this;
    }
}
