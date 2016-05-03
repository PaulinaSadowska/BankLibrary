package Products.Balance;

import java.math.BigDecimal;

/**
 * Created by arasz on 29.04.2016.
 */
public class BalanceException extends Exception
{
    private BigDecimal balance = null;
    private BigDecimal amount = null;

    public BalanceException(String message, BigDecimal amount)
    {
            super(message);
            this.amount = amount;
    }

    public BalanceException(String message, BigDecimal balance, BigDecimal amount)
    {
        this(message, amount);
        this.balance = balance;
    }

    @Override
    public String getMessage()
    {
        return createExceptionInfo() + super.getMessage();
    }

    private String  createExceptionInfo()
    {
        if(balance == null && amount == null)
            return "";
        if(balance == null)
            return "\nAmount: "+amount+"\n";
        if(amount == null)
            return "\nBalance: "+balance+"\n";

        return "\n Balance: "+ balance +"; Amount: "+amount+"\n";
    }

    @Override
    public String toString()
    {
        return createExceptionInfo() + super.toString();
    }
}
