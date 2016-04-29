package Operations;

import Bank.BankException;
import Products.Account;
import Products.Investment;

import java.util.Calendar;

/**
 * Created by arasz on 15.04.2016.
 */
public class CloseInvestmentOperation extends Operation implements ICommand
{

    private Investment _investment;
    private Account _baseAccount;

    public CloseInvestmentOperation(Investment investment){
        super(OperationType.CloseInvestment);
        this._investment = investment;
        this._baseAccount = investment.getBaseAccount();
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        if(Calendar.getInstance().getTime().after(_investment.getExpireDate()))
        {
            CalculateInterestOperation calculateInterest = new CalculateInterestOperation(_investment, _investment.getInterest());
            calculateInterest.execute();
        }
        _baseAccount.addToBalance(_investment.getBalanceValue());
        _executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        //TODO - it cannot be undone!
    }
}
