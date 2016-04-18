package Operations;

import Bank.BankException;
import Products.*;
import Utils.IInterestCalculationStrategy;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class MakeProductOperation extends Operation implements ICommand
{

    private Class _productType;
    private Integer _ownerId;
    private BigDecimal _balance;
    private ProductDuration _duration;
    private Interest _interest;
    private ProductManager _productManager;

    public <T extends Product> MakeProductOperation(Class<T> productType, Integer ownerId, BigDecimal balance,
                                                    ProductDuration duration, Interest interest, ProductManager productManager)
    {
        super(OperationType.MakeProduct);
        this._productType = productType;
        this._ownerId = ownerId;
        this._balance = balance;
        this._duration = duration;
        this._interest = interest;
        this._productManager = productManager;

    }

    @Override
    public void execute() throws Exception
    {
        Account account = _productManager.getAccount(_ownerId).get(0);
        _productManager.createNewProduct(_productType, _ownerId, _balance, _duration, _interest, account);
    }

    @Override
    public void undo() throws BankException
    {

    }



}
