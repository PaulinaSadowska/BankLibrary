package Operations;

import Bank.*;
import Products.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class InterbankTransferOperation extends Operation implements ICommand
{
    private CentralBank centralBank;
    private Account sourceAccount;
    private int targetAccountId;
    private int targetBankId;
    private BigDecimal amount;
    private PaymentOperation paymentFromSourceAccount;

    public InterbankTransferOperation(CentralBank centralBank, Account source, int targetAccountId, int targetBankId, BigDecimal amount)
    {
        super(OperationType.InterbankTransferOperation);
        this.centralBank = centralBank;
        this.sourceAccount = source;
        this.targetAccountId = targetAccountId;
        this.targetBankId = targetBankId;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        if (getExecuted())
            return;

        if (sourceAccount == null)
            throw new NullPointerException("source account is null");

        setExecuted(true);

        paymentFromSourceAccount = new PaymentOperation(sourceAccount, PaymentDirection.Out, amount, OperationType.Payment);
        paymentFromSourceAccount.execute();

        centralBank.transfer(this);
    }

    @Override
    public void undo() throws BankException
    {
        //TODO - delete me!
    }

    public int getTargetAccountId()
    {
        return targetAccountId;
    }

    public int getTargetBankId()
    {
        return targetBankId;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public Account getSourceAccount()
    {
        return sourceAccount;
    }
}
