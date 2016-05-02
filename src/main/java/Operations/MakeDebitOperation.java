package Operations;

import Bank.BankException;
import Products.Account;
import Products.Balance.Balance;
import Products.Debit;
import Products.DebitAccount;
import Products.IAccount;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeDebitOperation extends Operation implements ICommand
{
    private IAccount account;
    private BigDecimal debitValue;

    public MakeDebitOperation(IAccount account, BigDecimal debitValue)
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
        account.setDebit(new Debit(debitValue, new Balance(debitValue)));
        _executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        //todo - is it necessary here?
    }
}
