import Bank.*;
import Products.ProductManager;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class CentralBankTest
{

    private Bank _bank1;
    private Bank _bank2;
    private RealCentralBank _centralBank;

    @Before
    public void setUp(){
        _centralBank = new RealCentralBank();
        _bank1 = new Bank(new ProductManager());
        _bank2 = new Bank(new ProductManager());
    }

    @Test
    public void banksRegistration_TwoBanksToCentralBankListAdded() throws NoSuchFieldException, IllegalAccessException
    {
        _centralBank.registerBank(_bank1);
        _centralBank.registerBank(_bank2);
        Field f = _centralBank.getClass().getDeclaredField("banks"); //NoSuchFieldException
        f.setAccessible(true);
        HashMap banksList = (HashMap) f.get(_centralBank); //IllegalAccessException
        assertEquals(banksList.size(), 2);
    }
}