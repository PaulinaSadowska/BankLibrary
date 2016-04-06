import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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


    public  <T extends Product> void deleteProduct(T product)
    {
        /// kiedy moze byc usuniete?
        if((product instanceof Account))
        {
            _products.removeAll(product.getOwnerId());
        }
    }

    private <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                      Interest interest, Account baseAccount, Debit debit)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
            T product = null;
            //find proper constructor and initialize
            Constructor<T> constructor;
            if (baseAccount == null && debit == null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass());
                product = constructor.newInstance(ownerId, balance, expireDate, interest);
                _products.put(ownerId, product);
            }
            if (debit == null && baseAccount != null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), baseAccount.getClass());
                product = constructor.newInstance(ownerId, balance, expireDate, interest, baseAccount);
                _products.put(ownerId, product);
            }
            if (baseAccount == null && debit != null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), debit.getClass());
                product = constructor.newInstance(ownerId, balance, expireDate, interest, debit);
                _products.put(ownerId, product);
            }
        return product;
    }


    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                         Interest interest)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, null, null);
    }

    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                        Interest interest, Account baseAccount)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, baseAccount, null);
    }

    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                        Interest interest, Debit debit)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
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
