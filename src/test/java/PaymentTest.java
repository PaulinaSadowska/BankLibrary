import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by arasz on 18.03.2016.
 */
public class PaymentTest
{
    private Payment _payment;
    private Product _productMock;

    private void createPayment(Product productMock)
    {
        _payment = new Payment(OperationType.Payment, new Date(), "Wpłata testowa", productMock);
    }


    private void initialization(int balance, Class mockType) throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        _productMock = MockFactory.CreateProductMock(new BigDecimal(balance), null, mockType);
        createPayment(_productMock);
    }

    private void initialization(int balance, int debit, Class mockType) throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        _productMock = MockFactory.CreateProductMock(new BigDecimal(balance), new BigDecimal(debit), mockType);
        createPayment(_productMock);
    }

    @Test
    public void  paymentInExpectedInputTest() throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        int balance = 500;
        int paymentValue = 100;
        int expectedValue = balance+paymentValue;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        initialization(balance, Account.class);

        //Test
        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.In);
        //Sprawdzenie
        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertTrue(isPaymentSuccesful);
    }

    @Test(expected = IllegalArgumentException.class)
    public void paymentInNegativeAmountTest() throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        int balance = 0;
        int paymentValue = -1000;

        //Inicjalizacja
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        initialization(balance, Account.class);

        _payment.payment(paymentAmount, PaymentDirection.In);
    }

    @Test
    public void paymentOutNoDebitAmountLessThanBalanceTest() throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        int balance = 500;
        int paymentValue = 400;
        int expectedValue = balance - paymentValue;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        initialization(balance, Account.class);

        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertTrue(isPaymentSuccesful);
    }

    @Test
    public void paymentOutNoDebitAmountGreaterThanBalanceTest() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        initialization(balance, Account.class);


        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertFalse(isPaymentSuccesful);
    }

    @Test
    public void paymentOutAmountLessThanBalancePlusDebitTest() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        int debit = 200;
        int balance = 500;
        int paymentValue = 600;
        int expectrdValue = balance - paymentValue;

        initialization(balance, debit, Account.class);

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(expectrdValue);

        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertTrue(isPaymentSuccesful);
    }


    @Test
    public void paymentOutAmountEqualToBalancePlusDebitTest() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        int debit = 100;
        int balance = 500;
        int paymentValue = 600;
        int expectrdValue = balance - paymentValue;

        initialization(balance, debit, Account.class);

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(expectrdValue);

        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertTrue(isPaymentSuccesful);
    }


    @Test
    public void paymentOutAmountGreaterThanBalancePlusDebitTest() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        int debit = 50;
        int balance = 500;
        int paymentValue = 600;
        int expectrdValue = balance ;

        initialization(balance, debit, Account.class);

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(expectrdValue);

        boolean isPaymentSuccesful = _payment.payment(paymentAmount, PaymentDirection.Out);

        Assert.assertEquals(expectedAmount, _productMock.getBalance());
        Assert.assertFalse(isPaymentSuccesful);
    }


    @After
    public void tearDown() throws Exception
    {
        _payment = null;
    }
}