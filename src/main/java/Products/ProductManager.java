package Products;

import Products.Balance.Balance;
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
    private ListMultimap<Integer, IProduct> products;

    public ProductManager()
    {
        products = ArrayListMultimap.create();
    }

    public List<IProduct> getProductList(int ownerId)
    {
        return products.get(ownerId);
    }

    private <T> void  validateInput(T input)
    {
        T value = input;

        if(value instanceof Integer)
        {
            if(((Integer)value) < 0)
                throw new IllegalArgumentException("Owner id cant be less than 0");
        }
        else if(value instanceof BigDecimal)
        {
            if(((BigDecimal) value).longValue() < 0)
                throw new IllegalArgumentException("Balance cant be initialized with value less than  0");
        }
        else if(value instanceof Date)
        {
            if(Calendar.getInstance().getTime().after((Date)value))
                throw new IllegalArgumentException("Expire date before creation date");
        }
    }

    public  <T extends Product> void deleteProduct(T product)
    {
        /// kiedy moze byc usuniete?
        if((product instanceof Account))
        {
            products.removeAll(product.getOwnerId());
        }
    }

    private <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                   Interest interest, IAccount baseAccount, Debit debit)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
            Date expireDate = getExpireDate(duration);
            validateInput(ownerId);
            validateInput(balance);
            validateInput(expireDate);

            T product = null;
            //find proper constructor and initialize
            Constructor<T> constructor;
            if (baseAccount == null && debit == null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass());
                product = constructor.newInstance(ownerId, balance, expireDate, interest);
                products.put(ownerId, product);
            }
            if (debit == null && baseAccount != null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), baseAccount.getClass().getInterfaces()[0]);
                product = constructor.newInstance(ownerId, balance, expireDate, interest, baseAccount);
                products.put(ownerId, product);
            }
            if (baseAccount == null && debit != null)
            {
                constructor = clazz.getDeclaredConstructor(Integer.class, balance.getClass(),
                        expireDate.getClass(), interest.getClass(), debit.getClass());
                product = constructor.newInstance(ownerId, balance, expireDate, interest, debit);
                products.put(ownerId, product);
            }
        return product;
    }


    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                         Interest interest)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, null, null);
    }

    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                        Interest interest, IAccount baseAccount)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, baseAccount, null);
    }

    @Inject
    public <T extends Product> T createNewProduct(Class<T> clazz, Integer ownerId, Balance balance, ProductDuration duration,
                                                        Interest interest, Debit debit)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return createNewProduct(clazz, ownerId, balance, duration, interest, null, debit);
    }

    public int getAvailableOwnerId(){
        int i=0;
        while(products.containsKey(i))
        {
            i++;
        }
        return i;
    }

    private <T> List<T> getProduct(Class<T> clazz, Integer ownerId)
    {
        List<IProduct> productList = products.get(ownerId);
        List<T> out = new ArrayList<T>();
        if(productList!=null)
        {
            for(IProduct product: productList)
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

    public  List<Loan> getLoan(Integer ownerId)
    {
        return  getProduct(Loan.class, ownerId);
    }

    private Date getExpireDate(ProductDuration duration)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, duration.getNumOfMonths());
        cal.add(Calendar.YEAR, duration.getNumOfYears());
        return cal.getTime();
    }
}
