package Operations;

import Bank.BankException;
import Products.Account;
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
                    targetAccount.addToBalance(amount);
                    break;
                }
                case Out:
                {
                    if(targetAccount instanceof DebitAccount)
                    {
                        DebitAccount debitAccount = (DebitAccount)targetAccount;
                        Debit debit = debitAccount.getDebit();

                        if(BigDecimalComparator.GreaterThan(amount, debit.getDebitValue()))
                    }

                    targetAccount.substractFromBalance(amount);
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
