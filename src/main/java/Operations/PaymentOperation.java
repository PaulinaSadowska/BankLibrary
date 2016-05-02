package Operations;

import Bank.BankException;
import Products.Balance.BalanceException;
import Products.Debit;
import Products.DebitAccount;
import Products.IAccount;
import Utils.BigDecimalComparator;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentOperation extends Operation implements ICommand
{
    private IAccount targetAccount;
    private BigDecimal amount;
    private PaymentDirection direction;

    public PaymentOperation(IAccount targetAccount, PaymentDirection direction, BigDecimal amount, OperationType operationType)
    {
        super(operationType);
        this.targetAccount = targetAccount;
        this.direction = direction;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        try
        {
            switch (direction)
            {
                case In:
                {
                    if(!(targetAccount instanceof DebitAccount))
                    {
                        targetAccount.addToBalance(amount);
                        break;
                    }

                    DebitAccount debitAccount = (DebitAccount)targetAccount;
                    Debit debit = debitAccount.getDebit();

                    if(!debit.wasUsed())
                    {
                        targetAccount.addToBalance(amount);
                        break;
                    }

                    BigDecimal newAmount = debit.addToBalance(amount, true);

                    targetAccount.addToBalance(newAmount);
                    break;

                }
                case Out:
                {
                    if(BigDecimalComparator.GreaterThan(amount, targetAccount.getBalanceValue()))
                    {
                        if(!(targetAccount instanceof DebitAccount))
                            throw new BalanceException("Amount greater than balance",targetAccount.getBalanceValue(),amount);

                        DebitAccount debitAccount = (DebitAccount)targetAccount;
                        Debit debit = debitAccount.getDebit();
                        BigDecimal balanceValue = debitAccount.getBalanceValue();

                        BigDecimal balanceWithDebit = debit.getBalanceValue().add(balanceValue);

                        if(BigDecimalComparator.GreaterThan(amount, balanceWithDebit ))
                            throw new BalanceException("Amount greater than balance plus debit",balanceWithDebit,amount);

                        BigDecimal balanceAmountDifference = amount.subtract(balanceValue);
                        debitAccount.subtractFromBalance(balanceValue);
                        debit.subtractFromBalance(balanceAmountDifference);

                    }

                    targetAccount.subtractFromBalance(amount);
                }
            }
        }
        catch (BalanceException exception)
        {
            throw new BankException("Error during balance modification", exception);
        }


        _executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        if(!getExecuted())
            return;

        direction = (direction == PaymentDirection.In) ? PaymentDirection.Out : PaymentDirection.In;
        setExecuted(false);
        execute();
    }
}
