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

    public InterbankTransferOperation(CentralBank centralBank, Account source, int targetAccountId, int targetBankId, BigDecimal amount,
                                      OperationType operationType)
    {
        super(operationType);
        this.centralBank = centralBank;
        this.sourceAccount = source;
        this.targetAccountId = targetAccountId;
        this.targetBankId = targetBankId;
        this.amount = amount;
    }

    @Override
    public void execute() throws Exception
    {
        if(getExecuted())
            return;

        if(sourceAccount == null)
            throw new NullPointerException("source account is null");

        setExecuted(true);
        centralBank.transfer(this);
    }

    @Override
    public void undo() throws BankException
    {
//TODO - delete this!!!!
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
