package Products;

import Products.Balance.Balance;

import java.math.BigDecimal;

/**
 * Created by palka on 11.03.2016.
 * Reprezentuje debet który może posiadać rachunek
 */
public class Debit
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


}