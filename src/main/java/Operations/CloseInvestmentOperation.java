package Operations;

import Bank.BankException;
import Products.Account;
import Products.Balance.BalanceException;
import Products.IAccount;
import Products.Investment;

import java.util.Calendar;

/**
 * Created by arasz on 15.04.2016.
 */
public class CloseInvestmentOperation extends Operation implements ICommand
{

    private Investment investment;
    private IAccount baseAccount;

    public CloseInvestmentOperation(Investment investment){
        super(OperationType.CloseInvestment);
        this.investment = investment;
        this.baseAccount = investment.getBaseAccount();
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        if(Calendar.getInstance().getTime().after(investment.getExpireDate()))
        {
            CalculateInterestOperation calculateInterest = new CalculateInterestOperation(investment, investment.getInterest());
            calculateInterest.execute();
        }
        try
        {
            baseAccount.addToBalance(investment.getBalanceValue());
        } catch (BalanceException e)
        {
           throw new BankException("Repay loan exception", e);
        }
        executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        //TODO - it cannot be undone!
    }
}
