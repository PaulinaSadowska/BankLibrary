import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by arasz on 18.03.2016.
 */
public class PaymentTest
{
    private Payment _payment;
    private Product _productMock;

    @Before
    public void setUp() throws Exception
    {
        _productMock = MockFactory.CreateProductMock(new BigDecimal(0), Account.class);
        _payment = new Payment(OperationType.Payment, new Date(), "Wpłata testowa", _productMock );
    }

    @Test
    public void  paymentInExpectedInputTest()
    {
        BigDecimal expectedAmount = new BigDecimal(1000);

        _payment.payment(expectedAmount, PaymentDirection.In);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
    }

    @Test(expected = Exception.class)
    public void paymentInTestNegativeAmountTest()
    {
        BigDecimal expectedAmount = new BigDecimal(-1000);

        _payment.payment(expectedAmount, PaymentDirection.In);
    }

    @Test
    public void paymentOutTest()
    {

    }

    @Test
    public void paymentOutTestNegativeBalance()
    {

    }

    @Test
    public void transferTest()
    {

    }


    @After
    public void tearDown() throws Exception
    {
        _payment = null;
    }
}