import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest
{

    private ProductManager _manager;
    private int _ownerId;
    Calendar _nextMonth;
    Interest _interest;
    Account _baseAccount;

    @Before
    public void setUp(){
        _manager = new ProductManager();

        _ownerId = _manager.getAvailableOwnerId();
        _nextMonth = Calendar.getInstance();
        _nextMonth.add(Calendar.MONTH, 1);
        Injector injector = Guice.createInjector(new InterestModule());
        _interest = injector.getInstance(Interest.class);

    }


    @Test
    public void createAccountTest(){
       assertTrue(_manager.createNewProduct(Account.class, _ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        _baseAccount = _manager.getAccount(_ownerId);
    }

 /*   @Test
    public void createLoanTest()
    {
        _manager.createNewProduct(Loan.class, _ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, _baseAccount);
    }

    @Test
    public void createInvestmentTest()
    {
        _manager.createNewProduct(Investment.class, _ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, _baseAccount);
    }*/

    @Test
    public void getAccountTest()
    {

    }

    @Test
    public void getInvestmentTest()
    {

    }

    @Test
    public void getLoanTest()
    {

    }

    @Test
    public void createNewAccountWrongExpireDate(){

    }
}