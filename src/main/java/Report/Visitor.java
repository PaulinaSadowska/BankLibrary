package Report;

import Products.*;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public interface Visitor
{
    IProduct visit(Account account);
    IProduct visit(DebitAccount debitAccount);
    IProduct visit(Loan loan);
    IProduct visit(Investment investment);
}
