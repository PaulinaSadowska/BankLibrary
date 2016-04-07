import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest
{

    private ProductManager _manager;

    @Before
    public void setUp(){
        _manager = new ProductManager();
    }


    @Test
    public void createAccountWithoutDebitTest(){
        try
        {
            int ownerId = _manager.getAvailableOwnerId();
            Product account = _manager.createNewProduct(Account.class, ownerId, mock(BigDecimal.class),
                    mock(Date.class), mock(Interest.class));
            assertNotNull(account);
            assertEquals(ownerId, account.getOwnerId());
        } catch (InvocationTargetException e)
        {
            fail(e.getMessage());
        } catch (NoSuchMethodException e)
        {
            fail(e.getMessage());
        } catch (InstantiationException e)
        {
            fail(e.getMessage());
        } catch (IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void createAccountWithDebitTest(){
        int ownerId = _manager.getAvailableOwnerId();
        //assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, _debit));
    }

    @Test
    public void createLoanWithAccouuntTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        //_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest);
        Account account = _manager.getAccount(ownerId).get(0);
//        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, account));
    }

    @Test
    public void createInvestmentWithAccountTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
//        _manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest);
        Account account = _manager.getAccount(ownerId).get(0);
//        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, account));
    }

    @Test
    public void getAccountTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
//        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
        assertEquals(ownerId, account.getOwnerId());
    }

    @Test
    public void getInvestmentTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
//        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
//        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, account));
        Investment investment =_manager.getInvestment(ownerId).get(0);
        assertEquals(ownerId, investment.getOwnerId());
    }

    @Test
    public void getLoanTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
//        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
//        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, account));
        Loan loan =_manager.getLoan(ownerId).get(0);
        assertEquals(ownerId, loan.getOwnerId());
    }

    @Test
    public void getProductListTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
//        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        List<Account> accounts =_manager.getAccount(ownerId);
//        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));
//        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));
//        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));

        List<Loan> loans =_manager.getLoan(ownerId);
        List<Investment> investments = _manager.getInvestment(ownerId);

        List<Product> list = _manager.getProductList(ownerId);

        for(Account account: accounts)
        {
            assertTrue(list.contains(account));
        }
        for(Loan loan : loans)
        {
            assertTrue(list.contains(loan));
        }
        for(Investment investment: investments)
        {
            assertTrue(list.contains(investment));
        }

    }

}