package Bank;

import Products.*;
import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
import Utils.OperationsHistory;
import com.google.inject.Inject;

import java.math.BigDecimal;

/**
 * Created by arasz on 02.04.2016.
 */
public class Bank
{
    private ProductManager _productManager;
    private OperationsHistory _globalHistory;

    @Inject
    public Bank(ProductManager productManager)
    {
        _globalHistory = new OperationsHistory();
        _productManager = productManager;
    }

    public Account createAccount(Balance balance, ProductDuration duration, IInterestCalculationStrategy interestStrategy,
                                 double interestPercent) throws Exception
    {
        Interest interest = new Interest(interestStrategy, interestPercent);
        Integer ownerId = _productManager.getAvailableOwnerId();
        return _productManager.createNewProduct(Account.class, ownerId, balance, duration, interest);
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
}
