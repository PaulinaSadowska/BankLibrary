package Products;

import Operations.PaymentDirection;
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

    /**
     * Calculates how much of amount can debit take and returns this amount and residue
     * @param amount payment amount
     * @param accountBalance payment target balance
     * @param direction payment direction
     * @return amount divided into part that debit can take and rest
     */
    public DividedAmount divideAmount(BigDecimal amount, BigDecimal accountBalance, PaymentDirection direction)
    {
        DividedAmount dividedAmount = null;
        switch (direction) {
            case In:
                BigDecimal toDebit = debit.subtract(balance.getBalanceValue());
                BigDecimal residue = amount.subtract(toDebit);
                dividedAmount = new DividedAmount(residue, toDebit);
                break;
            case Out:
                if(BigDecimalComparator.GreaterThan(amount, accountBalance))
                    dividedAmount = new DividedAmount(accountBalance,amount.subtract(accountBalance));
                else
                    dividedAmount = new DividedAmount(amount, BigDecimal.ZERO);
                break;
        }
        return dividedAmount;
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