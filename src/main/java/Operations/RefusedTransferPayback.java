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

    public RefusedTransferPayback(Account targetAccount, BigDecimal amount) {
        super(OperationType.RefusedTransferPayback);
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;
        if(targetAccount == null)
            throw new BankException("target account can't be null");

        if(amount == null)
            throw new BankException("amount to transfer can't be null");

        new PaymentOperation(targetAccount, PaymentDirection.In, amount).execute();
    }
}
