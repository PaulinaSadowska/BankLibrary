package Products;

import Products.Balance.Balance;
import Products.Balance.BalanceException;
import Products.Balance.IBalance;

import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje debet który może posiadać rachunek
 */
public class Debit implements IBalance
{
    final private BigDecimal debit;

    final private Balance balance;

    public Debit(BigDecimal debitValue, Balance balance)
    {
        this.balance = balance;
        this.debit = debitValue;
    }

    public BigDecimal getDebitValue()
    {
        return debit;
    }

    public Balance getBalance()
    {
        return balance;
    }


    @Override
    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        balance.addToBalance(amount);
    }

    @Override
    public void substractFromBalance(BigDecimal amount) throws BalanceException
    {
        balance.substractFromBalance(amount);
    }

    @Override
    public BigDecimal getBalanceValue()
    {
        return balance.getBalanceValue();
    }
}