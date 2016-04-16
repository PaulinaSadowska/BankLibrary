package Operations;

import Bank.BankException;
import Products.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arasz on 15.04.2016.
 */
public class TransferOperation extends Operation implements ICommand
{
    private Account sourceAccount;
    Account targetAccount;
    BigDecimal amount;
    List<ICommand> operationsList;

    public TransferOperation(Account sourceAccount, Account targetAccount, BigDecimal amount, OperationType operationType)
    {
        super(operationType);
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        operationsList = new ArrayList<ICommand>();
        operationsList.add(new PaymentOperation(targetAccount, PaymentDirection.In, amount, OperationType.Payment));
        operationsList.add(new PaymentOperation(sourceAccount, PaymentDirection.Out, amount, OperationType.Payment));
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        if(targetAccount == null || sourceAccount == null)
            throw new NullPointerException("One of the accounts is null");

        try
        {
            setExecuted(true);
            for(ICommand operation : operationsList)
                operation.execute();
        }
        catch (BankException exe)
        {
            undo();
            throw exe;
        }
    }

    @Override
    public void undo() throws BankException
    {
        if(!getExecuted())
            return;

        for(ICommand operation : operationsList)
            operation.undo();
    }
}
