package Utils;

import Bank.*;
import Products.ProductManager;
import org.junit.Before;
import org.junit.Test;

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
    public void banksRegistration(){
        _centralBank.registerBank(_bank_1);
        _centralBank.registerBank(_bank_2);
    }

}
