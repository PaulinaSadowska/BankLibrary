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
        Account account = null;

        Date expireDate = getExpireDate(_duration);
        if(_ownerId != null) //klient posiada juz konto
        {
            account = _productManager.getAccount(_ownerId).get(0);
        }
        if(account == null) //klient nie posiada konta
        {
            _ownerId = _productManager.getAvailableOwnerId();
            if (_productType.getName().equals(Account.class.getName())){
                 _productManager.createNewProduct(_productType, _ownerId, _balance, expireDate, _interest);
            }
            _productManager.createNewProduct(Account.class, _ownerId, _balance, expireDate, _interest);
        }
        account = _productManager.getAccount(_ownerId).get(0);
        _productManager.createNewProduct(_productType, _ownerId, _balance, expireDate, _interest, account);
    }

    @Override
    public void undo() throws BankException
    {

    }

    private Date getExpireDate(ProductDuration duration)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, duration.getNumOfMonths());
        cal.add(Calendar.YEAR, duration.getNumOfYears());
        return cal.getTime();
    }

}
