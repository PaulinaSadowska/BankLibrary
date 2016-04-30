package Operations;

import Bank.BankException;
import Products.Interest;
import Utils.IInterestCalculationStrategy;

/**
 * Created by arasz on 15.04.2016.
 */
public class ChangeInterestMechanismOperation extends Operation implements ICommand
{
    private Interest _interest;
    private IInterestCalculationStrategy _strategy;

    public ChangeInterestMechanismOperation(Interest interest, IInterestCalculationStrategy strategy){
        super(OperationType.ChangeInterestMechanism);
        this._interest = interest;
        this._strategy = strategy;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        _interest._setStrategy(_strategy);
        _executed = true;
    }
}
