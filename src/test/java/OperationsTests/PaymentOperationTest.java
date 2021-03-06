package OperationsTests;

import Bank.BankException;
import Operations.ICommand;
import Operations.PaymentDirection;
import Operations.PaymentOperation;
import Products.Debit;
import Products.DebitAccount;
import Products.IAccount;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static Utils.ProductFactory.createAccount;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentOperationTest
{
    IAccount account;

    @Test
    public void makePayment_in500_balanceIncreasedBy500() throws Exception
    {
        int balance = 500;
        int paymentValue = 100;
        int expectedValue = balance+paymentValue;

        //Inicjalizacja
        account = createAccount(balance);

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        //Test
        ICommand operation = new PaymentOperation(account, PaymentDirection.In, paymentAmount);
        operation.execute();
        //Sprawdzenie
        Assert.assertEquals(expectedAmount, account.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void payment_InNegativeAmount_ThenThrowsException() throws Exception
    {
        int balance = 0;
        int paymentValue = -1000;

        //Inicjalizacja
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        account = createAccount(balance);
        ICommand operation = new PaymentOperation(account, PaymentDirection.In, paymentAmount);
        operation.execute();
    }

    @Test
    public void payment_OutAmountLessThanBalance_ThenBalanceDecreasedByAmount() throws Exception
    {
        int balance = 500;
        int paymentValue = 400;
        int expectedValue = balance - paymentValue;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        account = createAccount(balance);

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalanceValue());
    }

    @Test(expected = BankException.class)
    public void payment_OutAmountGreaterThanBalanceNoDebit_ThrowsBankException() throws Exception
    {
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance;

        //Inicjalizacja

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        account = createAccount(balance);

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount);
        operation.execute();
    }

    @Test
    public void payment_OutAmountLessThanBalancePlusDebit_ThenBalanceDecreasedByAmount() throws Exception
    {
        int debit = 200;
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance - paymentValue;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = BigDecimal.ZERO;
        BigDecimal expectedDebitValue = new BigDecimal(balance+debit-paymentValue);

        account = createAccount(balance, debit);
        DebitAccount debitAccount = (DebitAccount)account;
        Debit accountDebit = debitAccount.getDebit();

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalanceValue());
        Assert.assertEquals(expectedDebitValue, accountDebit.getBalanceValue());

    }


    @Test
    public void payment_OutAmountEqualToBalancePlusDebit_ThenBalanceEqualsMinusDebit() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int paymentValue = 600;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = BigDecimal.ZERO;
        BigDecimal expectedDebitValue = new BigDecimal(balance+debit-paymentValue);

        account = createAccount(balance, debit);
        Debit accountDebit = ((DebitAccount)account).getDebit();

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalanceValue());
        Assert.assertEquals(expectedDebitValue, accountDebit.getBalanceValue());
    }


    @Test(expected = BankException.class)
    public void payment_OutAmountGreaterThanBalancePlusDebit_ThenThrowsBankException() throws Exception
    {
        int debit = 50;
        int balance = 500;
        int paymentValue = 600;
        int expectedValue = balance ;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        account = createAccount(balance, debit);

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount);
        operation.execute();
    }
}
