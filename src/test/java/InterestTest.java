import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Time;
import static org.mockito.Matchers.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Paulina Sadowska on 07.04.2016.
 */
public class InterestTest
{
    private Interest _interest;

    @Before
    public void create(){
        _interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
    }

    @Test
    public void setStrategyTest() throws IllegalAccessException
    {
        Class<?> secretClass = _interest.getClass();
        Field fields[] = secretClass.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            if (field.getType() == IInterestCalculationStrategy.class)
            {
                String className = field.get(_interest).getClass().getName();
                assertNotEquals(className, TimeDependentInterestCalculationStrategy.class.getName());
            }
        }

        _interest.setStrategy(new TimeDependentInterestCalculationStrategy());

        fields = secretClass.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            if (field.getType() == IInterestCalculationStrategy.class)
            {
                String className = field.get(_interest).getClass().getName();
                assertEquals(className, TimeDependentInterestCalculationStrategy.class.getName());
            }
        }
    }

    @Test
    public void calculateInterestTest(){
        BigDecimal expectedInterestValue = new BigDecimal(1234);
        TimeDependentInterestCalculationStrategy strategyMock = mock(TimeDependentInterestCalculationStrategy.class);
        Product productMock = mock(Product.class);
        when(productMock.getOperationsHistory()).thenReturn(new OperationsHistory());
        when(strategyMock.calculateInterest(any(Product.class), any(double.class))).thenReturn(expectedInterestValue);
        _interest.setStrategy(strategyMock);
        assertEquals(_interest.calculateInterest(productMock), expectedInterestValue);
    }
}
