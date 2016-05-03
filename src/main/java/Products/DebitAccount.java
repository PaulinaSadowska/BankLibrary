package Products;

import Operations.ICommand;
import Report.Report;

import java.math.BigDecimal;

/**
 * Created by arasz on 22.04.2016.
 */
public class DebitAccount extends AccountDecorator
{
    private Debit debit;

    public DebitAccount(IAccount account, Debit debit)
    {
        super(account);
        this.debit = debit;
    }

    @Override
    public void doOperation(ICommand operation) throws Exception
    {

    }

    public boolean hasDebit()
    {
        return debit !=null;
    }

    public Debit getDebit()
    {
        return debit;
    }

    public void setDebit(Debit debit) { this.debit = debit;}


    /**
     * @return Saldo konta powiększone o debet
     */
    public BigDecimal getBalanceWithDebit()
    {
        BigDecimal initialBalance;
        if(hasDebit())
            initialBalance = debit.getBalanceValue().add(getBalanceValue());
        else
            initialBalance = getBalanceValue();

        return initialBalance;
    }

    @Override
    public IProduct accept(Report report)
    {
        return report.visit(this);
    }
}
