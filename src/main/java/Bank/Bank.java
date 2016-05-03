package Bank;

import Products.*;
import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
import Utils.OperationsHistory;
import Report.Report;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private Integer _id = -1; //not assigned
    private CentralBank _centralBank;
    private ProductManager _productManager;
    private OperationsHistory _globalHistory;

    @Inject
    public Bank(ProductManager productManager)
    {
        _globalHistory = new OperationsHistory();
        _productManager = productManager;
    }

    public IAccount createAccount(Balance balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy,
                                 double interestPercent) throws Exception
    {
        Interest interest = new Interest(interestStrategy, interestPercent);
        Integer ownerId = _productManager.getAvailableOwnerId();
        return _productManager.createNewProduct(Account.class, ownerId, balance, duration, interest, _id);
    }

    public IAccount getAccount(int ownerId){

        List<IAccount> accounts = _productManager.getAccount(ownerId);
        if(accounts.size() == 0){
            return null;
        }
        return accounts.get(0);
    }

    public List<IProduct> doReport(Report report) {
        List<IProduct> result = new ArrayList<IProduct>();
        for (IProduct product : _productManager.getProductList()) {
            result.add(product.accept(report));
        }
        return result;
    }

    public int getId()
    {
        return _id;
    }

    public void registerCentralBank(CentralBank centralBank, int bankId){
        _id = bankId;
        _productManager.setBankIdToAllAccounts(bankId);
        this._centralBank = centralBank;
    }
}
