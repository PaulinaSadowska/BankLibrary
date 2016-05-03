import Products.Account;
import Products.Balance.Balance;
import Products.Interest;
import Products.Product;
import org.junit.*;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created by arasz on 07.04.2016.
 */
public class ProductTest
{
    private Product product;

    private Date getDate(int year)
    {
        Calendar inst = Calendar.getInstance();
        inst.set(year, 12, 2);
        return inst.getTime();
    }

    private Product createInstance(Date expireDate)
    {
        return new Account(12, mock(Balance.class), expireDate, mock(Interest.class));
    }


    @Test
    public void setExpireDateBeforeCreationDate_ThenHasExpiredReturnTrue()
    {
        product = createInstance(getDate(2015));
        Assert.assertTrue(product.expired());
    }

    @Test
    public void setExpireDateAfterCreationDate_ThenHasExpiredReturnFalse()
    {
        product = createInstance(getDate(2020));
        Assert.assertFalse(product.expired());
    }

}
