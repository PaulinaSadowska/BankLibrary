package Products.Balance;

import java.math.BigDecimal;

/**
 * Represents balance and all operations on it
 */
public class Balance implements IBalance
{
    private BigDecimal balance;

    public Balance()
    {
        balance = BigDecimal.ZERO;
    }

    public Balance(BigDecimal initialBalance)
    {
        balance = initialBalance;
    }

    public Balance(int initialBalance)
    {
        balance = new BigDecimal(initialBalance);
    }


    @Override
    public void addToBalance(BigDecimal amount) throws BalanceException
    {
        Validiate(amount);
        balance = balance.add(amount);
    }

    @Override
    public void subtractFromBalance(BigDecimal amount) throws BalanceException
    {
        Validiate(amount);
        Validiate(amount, balance);

        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal getBalanceValue()
    {
        return balance;
    }

    private static void Validiate(BigDecimal amount) throws BalanceException
    {
        if(amount.compareTo(BigDecimal.ZERO) <0)
            throw new BalanceException("Amount can't be negative", amount);
    }

    private static void Validiate(BigDecimal amount, BigDecimal balance) throws BalanceException
    {
        if(amount.compareTo(balance)>0)
            throw new BalanceException("Amount greather than balance", balance, amount);
    }
}
