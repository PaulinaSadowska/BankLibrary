package Products;

import Products.Balance.Balance;
import Products.Balance.BalanceException;
import Products.Balance.IBalance;
import Utils.BigDecimalComparator;

import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje debet który może posiadać rachunek
 */
public class Debit implements IBalance
{
    final private BigDecimal debit;

    final private Balance balance;

    public Debit(BigDecimal debitValue)
    {
        this.balance = new Balance(debitValue);
        this.debit = debitValue;
    }

    public Debit(BigDecimal debitValue, Balance balance)
    {
        this.balance = balance;
        this.debit = debitValue;
    }

    public boolean wasUsed()
    {
        return !BigDecimalComparator.Equal(debit, balance.getBalanceValue());
    }

    public BigDecimal getDebitValue()
    {
        return debit;
    }

    public Balance getBalance()
    {
        return balance;
    }

    public BigDecimal addToBalance(BigDecimal amount, boolean returnResidue) throws BalanceException
    {
        BigDecimal maxAmount = debit.subtract(balance.getBalanceValue());
        BigDecimal residue = amount.subtract(maxAmount);
        addToBalance(maxAmount);
        return residue;
    }

    @Override
    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        balance.addToBalance(amount);
    }

    @Override
    public void subtractFromBalance(BigDecimal amount) throws BalanceException
    {
        balance.subtractFromBalance(amount);
    }

    @Override
    public BigDecimal getBalanceValue()
    {
        return balance.getBalanceValue();
    }
}