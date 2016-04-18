package Operations;

import Bank.BankException;
import Products.ProductDuration;
import Utils.IInterestCalculationStrategy;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeLoanOperation extends Operation implements ICommand
{
    private Integer _ownerId;
    private BigDecimal _balance;
    private ProductDuration _duration;
    private IInterestCalculationStrategy _interestStrategy;
    private double _interestPercent;

    public MakeLoanOperation(BigDecimal balance, ProductDuration duration,
                             IInterestCalculationStrategy interestStrategy, double interestPercent){
        super(OperationType.MakeLoan);
        this._balance = balance;
        this._duration = duration;
        this._interestStrategy = interestStrategy;
        this._interestPercent = interestPercent;
    }

    public MakeLoanOperation(Integer ownerId, BigDecimal balance, ProductDuration duration,
                             IInterestCalculationStrategy interestStrategy, double interestPercent){
        super(OperationType.MakeLoan);
        this._balance = balance;
        this._duration = duration;
        this._interestStrategy = interestStrategy;
        this._interestPercent = interestPercent;
        this._ownerId = ownerId;
    }

    @Override
    public void execute() throws BankException
    {
        //TODO - how? what about productManager?
    }

    @Override
    public void undo() throws BankException
    {
        //TODO - can you undo loan?
    }
}
