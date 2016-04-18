import Bank.BankException;
import Operations.CalculateInterestOperation;
import Products.Account;
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

        BigDecimal expectedInterestValue = new BigDecimal(1234);
        BigDecimal balance = new BigDecimal(1000);
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        Interest interest = new Interest(strategyMock, 0.3);
        Product product = new Account(1234, balance, mock(Date.class), interest);

        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);

        CalculateInterestOperation operation = new CalculateInterestOperation(product, interest);
        operation.execute();

        assertEquals(balance.add(expectedInterestValue), product.getBalance());
    }
}
