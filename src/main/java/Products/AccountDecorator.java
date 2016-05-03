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
    protected IAccount decoratedAccount;

    public AccountDecorator(IAccount account)
    {
        decoratedAccount = account;
    }

    @Override
    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        decoratedAccount.addToBalance(amount);
    }

    @Override
    public int getBankId()
    {
        return decoratedAccount.getBankId();
    }

    @Override
    public BigDecimal getBalanceValue()
    {
        return decoratedAccount.getBalanceValue();
    }

    @Override
    public Interest getInterest()
    {
        return decoratedAccount.getInterest();
    }

    @Override
    public int getOwnerId()
    {
        return decoratedAccount.getOwnerId();
    }

    @Override
    public Date getCreationDate()
    {
        return decoratedAccount.getCreationDate();
    }

    @Override
    public Date getExpireDate()
    {
        return decoratedAccount.getExpireDate();
    }

    @Override
    public OperationsHistory getOperationsHistory()
    {
        return decoratedAccount.getOperationsHistory();
    }

    @Override
    public boolean expired()
    {
        return decoratedAccount.expired();
    }

    @Override
    public void subtractFromBalance(BigDecimal amount) throws BalanceException
    {
        decoratedAccount.subtractFromBalance(amount);
    }
}
