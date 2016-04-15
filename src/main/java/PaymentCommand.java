import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentCommand extends Operation implements ICommand
{
    private Account sourceAccount;
    private BigDecimal amount;
    private PaymentDirection direction;

    public PaymentCommand(Account sourceAccount, PaymentDirection direction, BigDecimal amount, OperationType operationType)
    {
        super(operationType);
        this.sourceAccount = sourceAccount;
        this.direction = direction;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        checkExecuted();
        sourceAccount.payment(amount, direction);
    }
}
