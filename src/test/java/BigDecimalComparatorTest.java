import Utils.BigDecimalComparator;
import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 03.05.2016.
 */
public class BigDecimalComparatorTest
{
    private final BigDecimal biggerValue = new BigDecimal(120);
    private final BigDecimal smallerValue = new BigDecimal(1);


    @Test
    public void greaterThanTest()
    {
        assertTrue(BigDecimalComparator.GreaterThan(biggerValue, smallerValue));
        assertFalse(BigDecimalComparator.GreaterThan(smallerValue, biggerValue));
        assertFalse(BigDecimalComparator.GreaterThan(smallerValue, smallerValue));
    }

    @Test
    public void greaterOrEqualTest()
    {
        assertTrue(BigDecimalComparator.GreaterOrEqual(biggerValue, smallerValue));
        assertFalse(BigDecimalComparator.GreaterOrEqual(smallerValue, biggerValue));
        assertTrue(BigDecimalComparator.GreaterOrEqual(smallerValue, smallerValue));
    }

    @Test
    public void lessThanTest()
    {
        assertFalse(BigDecimalComparator.LessThan(biggerValue, smallerValue));
        assertTrue(BigDecimalComparator.LessThan(smallerValue, biggerValue));
        assertFalse(BigDecimalComparator.LessThan(smallerValue, smallerValue));
    }

    @Test
    public void lessOrEqualTest()
    {
        assertFalse(BigDecimalComparator.LessOrEqual(biggerValue, smallerValue));
        assertTrue(BigDecimalComparator.LessOrEqual(smallerValue, biggerValue));
        assertTrue(BigDecimalComparator.LessOrEqual(smallerValue, smallerValue));
    }

    @Test
    public void EqualTest()
    {
        assertFalse(BigDecimalComparator.Equal(biggerValue, smallerValue));
        assertFalse(BigDecimalComparator.Equal(smallerValue, biggerValue));
        assertTrue(BigDecimalComparator.Equal(smallerValue, smallerValue));
    }
}
