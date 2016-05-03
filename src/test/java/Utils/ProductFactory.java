package Utils;

import Products.*;
import Bank.*;
import Products.Balance.Balance;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 15.04.2016.
 */
public class ProductFactory
{
    public static IAccount createAccount(int balance)
    {
        Interest interestMock = mock(Interest.class);

        return new Account(12, new Balance(new BigDecimal(balance)), mock(Date.class), interestMock, 1234);
    }

    public static IAccount createAccount(int balance, int debitValue)
    {
        Interest interestMock = mock(Interest.class);
        Debit debitMock = new Debit(new BigDecimal(debitValue));
        IAccount account =  new Account(12, new Balance(new BigDecimal(balance)), mock(Date.class), interestMock, 1234);
        return new DebitAccount(account, debitMock);
    }

    public static IAccount createAccount(Bank bank, int balance) throws Exception
    {
        return bank.createAccount(new Balance(balance), new ProductDuration(1, 2),
                mock(IInterestCalculationStrategy.class), 1.2);
    }

    public static IAccount createAccount(Bank bank, int balance, int debitValue) throws Exception
    {
        Debit debitMock = new Debit(new BigDecimal(debitValue));
        IAccount account = bank.createAccount(new Balance(balance), new ProductDuration(1, 2),
                mock(IInterestCalculationStrategy.class), 1.2);
        return new DebitAccount(account, debitMock);
    }


    public static IAccount createAccount(int balance, int debitValue, int bankId)
    {
        Interest interestMock = mock(Interest.class);
        Debit debitMock = new Debit(new BigDecimal(debitValue));
        IAccount account =  new Account(12, new Balance(new BigDecimal(balance)), mock(Date.class), interestMock, bankId);
        return  account;
    }
}
