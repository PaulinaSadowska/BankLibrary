package Products;

import Operations.ICommand;
import Utils.OperationsHistory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 22.04.2016.
 */
 abstract class AccountDecorator implements IAccount
{
    protected Account _decoratedAccount;

    public AccountDecorator(Account account)
    {
        _decoratedAccount = account;
    }

    @Override
    public void setBalance(BigDecimal newBalance)
    {
        _decoratedAccount.setBalance(newBalance);
    }

    @Override
    public void addToBalance(BigDecimal amount)
    {
        _decoratedAccount.addToBalance(amount);
    }

    @Override
    public BigDecimal getBalance()
    {
        return _decoratedAccount.getBalance();
    }

    @Override
    public Interest getInterest()
    {
        return _decoratedAccount.getInterest();
    }

    @Override
    public int getOwnerId()
    {
        return _decoratedAccount.getOwnerId();
    }

    @Override
    public Date getCreationDate()
    {
        return _decoratedAccount.getCreationDate();
    }

    @Override
    public Date getExpireDate()
    {
        return _decoratedAccount.getExpireDate();
    }

    @Override
    public OperationsHistory getOperationsHistory()
    {
        return _decoratedAccount.getOperationsHistory();
    }

    @Override
    public boolean expired()
    {
        return _decoratedAccount.expired();
    }
}
