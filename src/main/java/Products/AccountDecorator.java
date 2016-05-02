package Products;

import Products.Balance.BalanceException;
import Utils.OperationsHistory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 22.04.2016.
 */
 abstract class AccountDecorator implements IAccount
{
    protected IAccount _decoratedAccount;

    public AccountDecorator(IAccount account)
    {
        _decoratedAccount = account;
    }

    @Override
    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        _decoratedAccount.addToBalance(amount);
    }

    @Override
    public BigDecimal getBalanceValue()
    {
        return _decoratedAccount.getBalanceValue();
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

    @Override
    public void subtractFromBalance(BigDecimal amount) throws BalanceException
    {
        _decoratedAccount.subtractFromBalance(amount);
    }
}
