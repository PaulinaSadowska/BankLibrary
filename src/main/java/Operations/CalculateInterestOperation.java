package Operations;

import Bank.BankException;
import Products.Interest;
import Products.Product;
import Utils.IInterestCalculationStrategy;

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
        if(getExecuted())
            return;

        BigDecimal interestValue = _interest.getStrategy().calculateInterest(_product, _interest.getPercent());
        BigDecimal newBalance = _product.getBalance().add(interestValue);

        _product.setBalance(newBalance);
        _executed = true;
    }

    @Override
    public void undo() throws BankException
    {
        //TODO is it necessary there?
    }
}
