package Operations;

import Bank.BankException;
import Products.Account;
import Products.Debit;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeDebitOperation extends Operation implements ICommand
{
    private Account _account;
    private BigDecimal _debitValue;

    public MakeDebitOperation(Account account, int debitValue)
    {
        super(OperationType.MakeDebit);
        this._account = account;
        this._debitValue = new BigDecimal(debitValue);
    }

    @Override
    public void execute() throws BankException
    {
        _account.setDebit(new Debit(_debitValue));
    }

    @Override
    public void undo() throws BankException
    {
        //todo - is it necessary here?
    }
}
