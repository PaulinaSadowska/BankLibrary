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

        try
        {
            soruceAccount.payment(amount, PaymentDirection.Out);
            targetAccount.payment(amount, PaymentDirection.In);
            _executed = true;
        }
        catch (Exception exe)
        {
            // TODO: Rollback transaction
            exe.printStackTrace();
        }
    }
}
