package Operations;

import Bank.BankException;
import Products.Balance.BalanceException;
import Products.Interest;
import Products.Product;

import java.math.BigDecimal;


/**
 * Created by arasz on 15.04.2016.
 */
public class CalculateInterestOperation extends Operation implements ICommand
{
    private Product _product;
    private Interest _interest;

    public CalculateInterestOperation(Product product, Interest interest){
        super(OperationType.CalculateInterest);
        this._product = product;
        this._interest = interest;
    }

    @Override
    public void execute() throws BankException
    {
        if (getExecuted())
            return;

        BigDecimal interestValue = _interest.getStrategy().calculateInterest(_product, _interest.getPercent());
        try
        {
            _product.addToBalance(interestValue);
        } catch (BalanceException e)
        {
            throw new BankException("Error during intrest calculation", e);
        }
        executed = true;
    }
}
