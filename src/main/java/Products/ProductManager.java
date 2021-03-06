package Products;

import Bank.BankException;
import Products.Balance.Balance;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductManager
{
    private ListMultimap<Integer, IProduct> products;

    public ProductManager()
    {
        products = ArrayListMultimap.create();
    }

    public List<IProduct> getProductList(int ownerId)
    {
        return products.get(ownerId);
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

    private <T extends Product> T _createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                    Interest interest, IAccount baseAccount, Integer bankId)
            throws Exception
    {
        Date expireDate = getExpireDate(duration);
        validateInput(ownerId);
        validateInput(balance);
        validateInput(expireDate);

        T product;
        //find proper constructor and initialize
        Constructor<T> constructor;
        if (baseAccount == null && bankId != null) //account
        {
            constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), Integer.class);
            product = constructor.newInstance(ownerId, balance, expireDate, interest, bankId);
            products.put(ownerId, product);
        }
        else if (baseAccount != null)  //investment or loan
        {
            constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                    expireDate.getClass(), interest.getClass(), baseAccount.getClass().getInterfaces()[0]);
            product = constructor.newInstance(ownerId, balance, expireDate, interest, baseAccount);
            products.put(ownerId, product);
        }
        else
        {
            throw new BankException("Not valid parameters passed to create product");
        }
        return product;
    }


    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                  Interest interest, IAccount baseAccount)
            throws Exception
    {
        return _createNewProduct(clazz, ownerId, balance, duration, interest, baseAccount, -1);
    }


    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                  Interest interest, Integer bankId)

            throws Exception
    {
        return _createNewProduct(clazz, ownerId, balance, duration, interest, null, bankId);
    }


    public int getAvailableOwnerId()
    {
        int i = 0;
        while (products.containsKey(i))
        {
            i++;
        }
        return i;
    }

    private <T> List<T> getProduct(Class<T> clazz, Integer ownerId)
    {
        List<IProduct> productList = products.get(ownerId);
        List<T> out = new ArrayList<T>();
        if (productList != null)
        {
            for (IProduct product : productList)
            {
                if (clazz.isInstance(product))
                {
                    out.add(clazz.cast(product));
                }
            }
        }
        return out;
    }

    public List<IAccount> getAccount(Integer ownerId)
    {
        return getProduct(IAccount.class, ownerId);
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

    public void setBankIdToAllAccounts(int bankId)
    {
        for (Integer ownerId : products.keySet())
        {
            List<IProduct> productList = products.get(ownerId);
            for (IProduct product : productList)
            {
                if (product instanceof Account)
                {
                    Account account = (Account) product;
                    account._setBankId(bankId);
                }
            }
        }
    }

    public List<IProduct> getProductList()
    {
        List<IProduct> productList = new ArrayList<IProduct>();
        for (Integer key : products.keySet())
        {
            for (IProduct product : products.get(key))
            {
                productList.add(product);
            }
        }
        return productList;
    }
}