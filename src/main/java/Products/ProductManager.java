package Products;

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

    private <T> void validateInput(T input)
    {
        T value = input;

        if (value instanceof Integer)
        {
            if (((Integer) value) < 0)
                throw new IllegalArgumentException("id cant be less than 0");
        } else if (value instanceof BigDecimal)
        {
            if (((BigDecimal) value).longValue() < 0)
                throw new IllegalArgumentException("Balance cant be initialized with value less than  0");
        } else if (value instanceof Date)
        {
            if (Calendar.getInstance().getTime().after((Date) value))
                throw new IllegalArgumentException("Expire date before creation date");
        }
    }

    public <T extends Product> void deleteProduct(T product)
    {
        /// kiedy moze byc usuniete?
        if ((product instanceof Account))
        {
            _products.removeAll(product.getOwnerId());
        }
    }

    private <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, ProductDuration duration,
                                                   Interest interest, Account baseAccount, Debit debit, Integer bankId)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Date expireDate = getExpireDate(duration);
        validateInput(ownerId);
        validateInput(balance);
        validateInput(expireDate);

        T product = null;
        //find proper constructor and initialize
        Constructor<T> constructor;
        if (debit == null && baseAccount != null)
        {
            constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), baseAccount.getClass());
            product = constructor.newInstance(ownerId, balance, expireDate, interest, baseAccount);
            _products.put(ownerId, product);
        }
        if (baseAccount == null && debit == null && bankId!=null)
        {
            constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), bankId.getClass());
            product = constructor.newInstance(ownerId, balance, expireDate, interest, bankId);
            _products.put(ownerId, product);
        }
        if (baseAccount == null && debit != null && bankId != null)
        {
            constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), debit.getClass(), bankId.getClass());
            product = constructor.newInstance(ownerId, balance, expireDate, interest, debit, bankId);
            _products.put(ownerId, product);
        }
        return product;
    }



    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, ProductDuration duration,
                                                  Interest interest, Account baseAccount)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, baseAccount, null, -1);
    }

    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, ProductDuration duration,
                                                  Interest interest, Debit debit, Integer bankId)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, null, debit, bankId);
    }


    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, BigDecimal balance, ProductDuration duration,
                                                  Interest interest, Integer bankId)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, null, null, bankId);
    }

    public int getAvailableOwnerId()
    {
        int i = 0;
        while (_products.containsKey(i))
        {
            i++;
        }
        return i;
    }

    private <T extends Product> List<T> getProduct(Class<T> clazz, Integer ownerId)
    {
        List<Product> productList = _products.get(ownerId);
        List<T> out = new ArrayList<T>();
        if (productList != null)
        {
            for (Product product : productList)
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

    public List<Loan> getLoan(Integer ownerId)
    {
        return getProduct(Loan.class, ownerId);
    }

    private Date getExpireDate(ProductDuration duration)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, duration.getNumOfMonths());
        cal.add(Calendar.YEAR, duration.getNumOfYears());
        return cal.getTime();
    }
}