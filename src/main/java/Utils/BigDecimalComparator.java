package Utils;

import java.math.BigDecimal;

/**
 * Created by arasz on 29.04.2016.
 */
public class BigDecimalComparator
{

    public static boolean GreaterThan(BigDecimal a ,BigDecimal b)
    {
        return a.compareTo(b) > 0;
    }

    public static boolean GreaterOrEqual(BigDecimal a, BigDecimal b)
    {
        return a.compareTo(b) >= 0;
    }

    public static boolean LessOrEqual(BigDecimal a, BigDecimal b)
    {
        return a.compareTo(b) <= 0;
    }

    public static boolean Equal(BigDecimal a ,BigDecimal b)
    {
        return a.compareTo(b) == 0;
    }

    public static boolean LessThan(BigDecimal a ,BigDecimal b)
    {
        return a.compareTo(b) < 0;
    }
}
