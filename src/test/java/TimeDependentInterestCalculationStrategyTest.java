import Products.Product;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Paulina Sadowska on 07.04.2016.
 */
public class TimeDependentInterestCalculationStrategyTest
{
    private TimeDependentInterestCalculationStrategy interestCalculationStrategy;
    private BigDecimal _balance;
    private Product _product;
    private int _numOfDays;

    @Before
    public void create(){
        interestCalculationStrategy = new TimeDependentInterestCalculationStrategy();
        _balance = new BigDecimal(1200);
        Calendar cal = Calendar.getInstance();
        _numOfDays = 7;
        cal.add(Calendar.DAY_OF_YEAR, (-1) * _numOfDays);
        Date creationDate = cal.getTime();

        _product = mock(Product.class);
        when(_product.getBalance()).thenReturn(_balance);
        when(_product.getCreationDate()).thenReturn(creationDate);
    }

    @Test
    public void calculateInterestTest(){

        double percent = 2;
        double expectedInterestValue = _balance.doubleValue() * _numOfDays * percent * 0.01;
        BigDecimal interestValue = interestCalculationStrategy.calculateInterest(_product, percent);
        assertEquals(expectedInterestValue, interestValue.doubleValue(), 0.01);
    }

    @Test
    public void calculateInterest_2_Test(){

        double percent = 0.1;
        double expectedInterestValue = _balance.doubleValue() * _numOfDays * percent * 0.01;
        BigDecimal interestValue = interestCalculationStrategy.calculateInterest(_product, percent);
        assertEquals(expectedInterestValue, interestValue.doubleValue(), 0.01);
    }


    @Test
    public void calculateInterest_invalidPercent_Test(){

        double percent = 101;
        try{
            interestCalculationStrategy.calculateInterest(_product, percent);
            fail();
        }
        catch (InvalidParameterException e) {

        }
    }

    @Test
    public void calculateInterest_invalidPercent_2_Test(){

        double percent = -1;
        try{
            interestCalculationStrategy.calculateInterest(_product, percent);
            fail();
        }
        catch (InvalidParameterException e) {

        }
    }
}
