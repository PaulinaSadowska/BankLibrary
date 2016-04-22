package Products;

import Operations.ICommand;

import java.math.BigDecimal;

/**
 * Created by arasz on 22.04.2016.
 */
public class DebitAccount extends AccountDecorator
{
    private Debit _debit;

    public DebitAccount(Account account)
    {
        super(account);
    }

    @Override
    public void doOperation(ICommand operation) throws Exception
    {

    }

    public boolean hasDebit()
    {
        return _debit!=null;
    }

    public Debit getDebit()
    {
        return  _debit;
    }

    public void setDebit(Debit debit) { _debit = debit;}


    /**
     * @return Saldo konta powiększone o debet
     */
    public BigDecimal getBalanceWithDebit()
    {
        BigDecimal initialBalance;
        if(hasDebit())
            initialBalance = getDebit().getDebitValue().add(getBalance());
        else
            initialBalance = getBalance();

        return initialBalance;
    }
}
