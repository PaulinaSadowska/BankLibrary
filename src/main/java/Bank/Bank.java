package Bank;

import Products.*;
import Utils.IInterestCalculationStrategy;
import Utils.OperationsHistory;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.LinkedList;
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

    public Account createAccount(BigDecimal balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy,
                                 double interestPercent) throws Exception
    {
        Interest interest = new Interest(interestStrategy, interestPercent);
        Integer ownerId = _productManager.getAvailableOwnerId();
        return _productManager.createNewProduct(Account.class, ownerId, balance, duration, interest, _id);
    }

    public Account getAccount(int ownerId){

        List<Account> accounts = _productManager.getAccount(ownerId);
        if(accounts.size() == 0){
            return null;
        }
        return accounts.get(0);
    }

    /**
     * Usunięcie konta
     * @pre: account != null
     * @post: account == null
     * @invariant:
     * @return
     * Nieusuniecie konta powinno rzucać wyjatki
     */
    public void deleteAccount()
    {

    }

    public int getId()
    {
        return _id;
    }

    public void registerCentralBank(CentralBank centralBank, int bankId){
        _id = bankId;
        this._centralBank = centralBank;
    }
}
