import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

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

    public <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                         Interest interest, Account baseAccount){
        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), baseAccount != null? baseAccount.getClass():null);
            _products.put(ownerId, constructor.newInstance());
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public <T extends Product> boolean createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, Date expireDate,
                                                         Interest interest)
    {
        return createNewProduct(clazz, ownerId, balance, expireDate, interest, null);
    }

    /**
     * Czemu to jest publiczne?
     * @return
     */
    public int getAvailableOwnerId(){
        int i=0;
        while(_products.containsKey(i))
        {
            i++;
        }
        return i;
    }

    private <T> T getProduct(Integer ownerId)
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

    public IAccount getAccount(Integer ownerId)
    {
        return getProduct(ownerId);
    }

    public IInterest getInterest(Integer ownerId)
    {
        return getProduct(ownerId);
    }

    public  ILoan getLoan(Integer ownerId)
    {
        return  getProduct(ownerId);
    }
}
