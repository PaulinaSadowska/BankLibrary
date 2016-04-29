package Utils;

import Bank.*;
import Products.ProductManager;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class CentralBankTest
{

    private Bank _bank_1;
    private Bank _bank_2;
    private RealCentralBank _centralBank;

    @Before
    public void setUp(){
        _centralBank = new RealCentralBank();
        _bank_1 = new Bank(new ProductManager());
        _bank_2 = new Bank(new ProductManager());
    }

    @Test
    public void banksRegistration() throws NoSuchFieldException, IllegalAccessException
    {
        _centralBank.registerBank(_bank_1);
        _centralBank.registerBank(_bank_2);
        Field f = _centralBank.getClass().getDeclaredField("banks"); //NoSuchFieldException
        f.setAccessible(true);
        HashMap banksList = (HashMap) f.get(_centralBank); //IllegalAccessException
        assertEquals(banksList.size(), 2);
    }

}
