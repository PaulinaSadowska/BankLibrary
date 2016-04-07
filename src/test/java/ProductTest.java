import org.junit.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by arasz on 07.04.2016.
 */
public class ProductTest
{
    private Product _product;

    private Date getDate(int year)
    {
        Calendar inst = Calendar.getInstance();
        inst.set(year, 12, 2);
        return inst.getTime();
    }

    private Product createInstance(Date expireDate)
    {
        return new Account(12, mock(BigDecimal.class), expireDate, mock(Interest.class));
    }


    @Test
    public void setExpireDateBeforeCreationDate_ThenHasExpiredReturnTrue()
    {
        _product = createInstance(getDate(2015));
        Assert.assertTrue(_product.expired());
    }

    @Test
    public void setExpireDateAfterCreationDate_ThenHasExpiredReturnFalse()
    {_product = createInstance(getDate(2020));
        Assert.assertFalse(_product.expired());
    }

}