package Operations;

import Bank.BankException;
import Products.*;
import Utils.IInterestCalculationStrategy;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class OpenInvestmentOperation extends Operation implements ICommand
{
    private Integer _ownerId;
    private BigDecimal _balance;
    private ProductDuration _duration;
    private IInterestCalculationStrategy _interestStrategy;
    private double _interestPercent;
    private ProductManager _productManager;

    public OpenInvestmentOperation(Integer ownerId, BigDecimal balance, ProductDuration duration,
                                   IInterestCalculationStrategy interestStrategy, double interestPercent, ProductManager productManager)
    {
        super(OperationType.OpenInvestment);
        this._balance = balance;
        this._duration = duration;
        this._interestStrategy = interestStrategy;
        this._interestPercent = interestPercent;
        this._ownerId = ownerId;
        this._productManager = productManager;
    }

    @Override
    public void execute() throws Exception
    {
        if(getExecuted())
            return;

        Account baseAccount = _productManager.getAccount(_ownerId).get(0);
        Interest interest = new Interest(_interestStrategy, _interestPercent);
        _productManager.createNewProduct(Investment.class, _ownerId, _balance, _duration, interest, baseAccount);

        _executed = true;
    }

    @Override
    public void undo() throws BankException
    {

    }
}
