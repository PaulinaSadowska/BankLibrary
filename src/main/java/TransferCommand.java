import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class TransferCommand extends Operation implements ICommand
{
    Account soruceAccount;
    Account targetAccount;
    BigDecimal amount;

    public TransferCommand(Account soruceAccount, Account targetAccount, BigDecimal amount, OperationType operationType)
    {
        super(operationType);
        this.soruceAccount = soruceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        checkExecuted();

        if(targetAccount == null || soruceAccount == null)
            throw new NullPointerException("One of the accounts is null");

        try
        {
            ICommand outPayment = new PaymentCommand(soruceAccount, PaymentDirection.Out, amount, OperationType.Payment);
            outPayment.execute();
            ICommand inPayment = new PaymentCommand(targetAccount, PaymentDirection.In, amount, OperationType.Payment);
            inPayment.execute();
            _executed = true;
        }
        catch (Exception exe)
        {
            // TODO: Rollback transaction
            exe.printStackTrace();
        }
    }
}
