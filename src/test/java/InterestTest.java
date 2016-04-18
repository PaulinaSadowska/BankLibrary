import Bank.BankException;
import Products.Interest;
import Utils.IInterestCalculationStrategy;
import Utils.TimeDependentInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Paulina Sadowska on 07.04.2016.
 */
public class InterestTest
{
    private Interest _interest;

    @Before
    public void create()
    {
        _interest = new Interest(mock(IInterestCalculationStrategy.class), 0.3);
    }

    @Test
    public void setStrategyTest() throws IllegalAccessException, BankException
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

        _interest._setStrategy(new TimeDependentInterestCalculationStrategy());
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

}
