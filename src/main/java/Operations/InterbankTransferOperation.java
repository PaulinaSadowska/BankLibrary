package Operations;

import Bank.*;
import Products.Account;
import Products.IAccount;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class InterbankTransferOperation extends Operation implements ICommand
{
    private CentralBank centralBank;
    private IAccount sourceAccount;
    private int targetAccountId;
    private int targetBankId;
    private BigDecimal amount;
    private PaymentOperation paymentFromSourceAccount;

    public InterbankTransferOperation(CentralBank centralBank, IAccount source, int targetAccountId, int targetBankId, BigDecimal amount)
    {
        super(OperationType.InterbankTransfer);
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

        paymentFromSourceAccount = new PaymentOperation(sourceAccount, PaymentDirection.Out, amount);
        paymentFromSourceAccount.execute();

        centralBank.transfer(this);
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

    public IAccount getSourceAccount()
    {
        return sourceAccount;
    }
}
