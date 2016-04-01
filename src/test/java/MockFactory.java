

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.Proxy;
import java.util.Date;

/**
 * Created by arasz on 01.04.2016.
 */
public class MockFactory
{
    public static Product CreateProductMock(BigDecimal balance, BigDecimal debit, Class<? extends Product> productClass) throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        /// Refaktoryzacja: można zastosować słownik który jako klucz przyjmuje typ i zwraca konstruktor
        Product mock = null;
        Constructor[] constructors =  productClass.getConstructors();
        for(Constructor ctor : constructors )
        {
            Class[] parameterTypes = ctor.getParameterTypes();
            if(parameterTypes.length > 1)
            {
                mock = (Product) ctor.newInstance(balance, new Date(), new Date(Long.MAX_VALUE),
                        new Interest(),11111, new OperationsHistory(), debit);
            }
        }

        return mock;
    }
}