package Operations;

import Bank.BankException;
import Products.Account;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
public class TransferRejectedOperation extends Operation implements ICommand
{
    Account targetAccount;
    BigDecimal amount;

    public TransferRejectedOperation(Account targetAccount, BigDecimal amount){
        super(OperationType.TransferRejected);
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void execute() throws Exception
    {
        //transfer money back to sender
    }

    @Override
    public void undo() throws BankException
    {

    }
}
