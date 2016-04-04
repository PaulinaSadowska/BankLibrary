import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductManager
{
    private ListMultimap<Integer, Product> _products;

    public ProductManager()
    {
        _products = ArrayListMultimap.create();
    }

    public List<Product> getProductList(int ownerId)
    {
        return _products.get(ownerId);
    }

    private <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                         Interest interest, Account baseAccount, Debit debit){
        try
        {
            Constructor<T> constructor;
            if (baseAccount == null && debit == null)
            {
                 constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass());
                _products.put(ownerId, constructor.newInstance(ownerId, balance, expireDate, interest));
            }
            if(debit == null && baseAccount !=null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), baseAccount.getClass());
                _products.put(ownerId, constructor.newInstance(ownerId, balance, expireDate, interest, baseAccount));
            }
            if(baseAccount == null && debit != null){
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), debit.getClass());
                _products.put(ownerId, constructor.newInstance(ownerId, balance, expireDate, interest, debit));
            }
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    @Inject
    public <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                         Interest interest)
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, null, null);
    }

    @Inject
    public <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                        Interest interest, Account baseAccount)
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, baseAccount, null);
    }

    @Inject
    public <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                        Interest interest, Debit debit)
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, null, debit);
    }

    public int getAvailableOwnerId(){
        int i=0;
        while(_products.containsKey(i))
        {
            i++;
        }
        return i;
    }

    // Osobną kwestia jest czy metoda generyczna nie wystarczy?

    private <T extends Product> T getProduct(Integer ownerId)
    {
        List<Product> productList = _products.get(ownerId);
        if(productList!=null)
        {
            for(Product product: productList)
            {
                T concreteProduct = (T) product;
                if(concreteProduct != null)
                    return concreteProduct;
            }
        }
        return null;
    }

    public Account getAccount(Integer ownerId)
    {
        return getProduct(ownerId);
    }

    public Investment getInvestment(Integer ownerId)
    {
        return getProduct(ownerId);
    }

    public  Loan getLoan(Integer ownerId)
    {
        return  getProduct(ownerId);
    }
}
