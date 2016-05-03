package OperationsTests;

import Bank.BankException;
import Operations.CalculateInterestOperation;
import Products.Account;
import Products.Balance.Balance;
import Products.Interest;
import Products.Product;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 18.04.2016.
 */
public class CalculateInterestOperationTest
{

    @Test
    public void calculateInterestTest() throws BankException
    {
        BigDecimal balanceValue = new BigDecimal(1000);
        BigDecimal expectedInterestValue = new BigDecimal(1234);

        Balance balance = new Balance(balanceValue);
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        Interest interest = new Interest(strategyMock, 0.3);
        Product product = new Account(1234, balance, mock(Date.class), interest, 123);

        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);

        BigDecimal expectedProductBalance  = balanceValue.add(expectedInterestValue);

        CalculateInterestOperation operation = new CalculateInterestOperation(product, interest);
        operation.execute();

        assertEquals(expectedProductBalance, product.getBalanceValue());
    }
}
