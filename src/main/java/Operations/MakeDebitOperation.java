package Operations;

import Bank.BankException;
import Products.Balance.Balance;
import Products.Debit;
import Products.DebitAccount;
import Products.IAccount;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeDebitOperation extends Operation implements ICommand
{
    private AtomicReference<IAccount>  account;
    private BigDecimal debitValue;

    public MakeDebitOperation(AtomicReference<IAccount> account, BigDecimal debitValue)
    {
        super(OperationType.MakeDebit);
        this.account = account;
        this.debitValue = debitValue;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;
        account.set(new DebitAccount(account.get(), new Debit(debitValue)));
        executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        //todo - is it necessary here?
    }
}
