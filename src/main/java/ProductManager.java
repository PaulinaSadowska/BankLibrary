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
            //not allowed
            if(baseAccount!=null && debit != null)
            {
                return false;
            }

            //find proper constructor and initialize
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

    private <T extends Product> List<T> getProduct(Class<T> clazz, Integer ownerId)
    {
        List<Product> productList = _products.get(ownerId);
        List<T> out = new ArrayList<T>();
        if(productList!=null)
        {
            for(Product product: productList)
            {
                if (clazz.isInstance(product))
                {
                    out.add(clazz.cast(product));
                }
            }
        }
        return out;
    }

    public List<Account> getAccount(Integer ownerId)
    {
        return getProduct(Account.class, ownerId);
    }

    public List<Investment> getInvestment(Integer ownerId)
    {
        return getProduct(Investment.class, ownerId);
    }

    public  List<Loan> getLoan(Integer ownerId)
    {
        return  getProduct(Loan.class, ownerId);
    }
}
