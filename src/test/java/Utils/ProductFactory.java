package Utils;

import Products.Account;
import Products.Debit;
import Products.Interest;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by arasz on 15.04.2016.
 */
public class ProductFactory
{
    public static Account createAccount(int balance)
    {
        Interest interestMock = mock(Interest.class);
        return new Account(12, new BigDecimal(balance), mock(Date.class), interestMock, 1234);
    }

    public static Account createAccount(int balance, int debitValue)
    {
        Interest interestMock = mock(Interest.class);
        Debit debitMock = mock(Debit.class);
        when(debitMock.getDebitValue()).thenReturn(new BigDecimal(debitValue));

        return new Account(12, new BigDecimal(balance), mock(Date.class), interestMock, debitMock, 1234);
    }


    public static Account createAccount(int balance, int debitValue, int bankId)
    {
        Interest interestMock = mock(Interest.class);
        Debit debitMock = mock(Debit.class);
        when(debitMock.getDebitValue()).thenReturn(new BigDecimal(debitValue));

        return new Account(12, new BigDecimal(balance), mock(Date.class), interestMock, debitMock, bankId);
    }
}
