package Operations;

import Bank.BankException;
import Products.Account;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class RefusedTransferPayback extends Operation implements ICommand
{
    private Account targetAccount;
    private BigDecimal amount;

    public RefusedTransferPayback(Account targetAccount, BigDecimal amount, OperationType operationType) {
        super(operationType);
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;
    }

    @Override
    public void undo() throws BankException
    {

    }

    public Account getTargetAccount()
    {
        return targetAccount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
}
