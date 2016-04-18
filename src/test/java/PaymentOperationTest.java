import Bank.BankException;
import Operations.ICommand;
import Operations.OperationType;
import Operations.PaymentDirection;
import Operations.PaymentOperation;
import Products.Account;
import Utils.ProductFactory;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static Utils.ProductFactory.createAccount;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentOperationTest
{
    Account account;

    @Test
    public void makeAndUndoPayment_DirectionInCorrectAmount_BalanceNotChanged() throws Exception
    {
        int balance = 500;
        int paymentValue = 100;
        int expectedValue = balance+paymentValue;

        //Inicjalizacja
        account = createAccount(balance);

        BigDecimal expectedAmount = new BigDecimal(expectedValue);
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        ICommand operation = new PaymentOperation(account, PaymentDirection.In, paymentAmount, OperationType.Payment);
        operation.execute();
        Assert.assertEquals(expectedAmount, account.getBalance());
        operation.undo();
        Assert.assertEquals(new BigDecimal(balance), account.getBalance());
    }

    @Test
    public void makeAndUndoPayment_DirectionOutCorrectAmount_BalanceNotChanged() throws Exception
    {
        int balance = 100;
        account = ProductFactory.createAccount(balance);
        BigDecimal amount = new BigDecimal(50);
        ICommand command = new PaymentOperation(account, PaymentDirection.Out, amount, OperationType.Payment);
        command.execute();
        Assert.assertEquals(amount, account.getBalance());
        command.undo();
        Assert.assertEquals(new BigDecimal(balance), account.getBalance());
    }

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
        ICommand operation = new PaymentOperation(account, PaymentDirection.In, paymentAmount, OperationType.Payment);
        operation.execute();
        //Sprawdzenie
        Assert.assertEquals(expectedAmount, account.getBalance());
    }

    @Test(expected = BankException.class)
    public void payment_InNegativeAmount_ThenThrowsException() throws Exception
    {
        int balance = 0;
        int paymentValue = -1000;

        //Inicjalizacja
        BigDecimal paymentAmount = new BigDecimal(paymentValue);

        account = createAccount(balance);
        ICommand operation = new PaymentOperation(account, PaymentDirection.In, paymentAmount, OperationType.Payment);
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

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount, OperationType.Payment);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalance());
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

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount, OperationType.Payment);
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
        BigDecimal expectedAmount = new BigDecimal(expectedValue);

        account = createAccount(balance, debit);

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount, OperationType.Payment);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalance());
    }


    @Test
    public void payment_OutAmountEqualToBalancePlusDebit_ThenBalanceEqualsMinusDebit() throws Exception
    {
        int debit = 100;
        int balance = 500;
        int paymentValue = 600;

        BigDecimal paymentAmount = new BigDecimal(paymentValue);
        BigDecimal expectedAmount = new BigDecimal(-debit);

        account = createAccount(balance, debit);

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount, OperationType.Payment);
        operation.execute();

        Assert.assertEquals(expectedAmount, account.getBalance());
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

        ICommand operation = new PaymentOperation(account, PaymentDirection.Out, paymentAmount, OperationType.Payment);
        operation.execute();
    }
}
