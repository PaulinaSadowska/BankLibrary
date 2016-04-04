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
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest
{

    private ProductManager _manager;
    Calendar _nextMonth;
    Interest _interest;
    Debit _debit;

    @Before
    public void setUp(){
        _manager = new ProductManager();

        _nextMonth = Calendar.getInstance();
        _nextMonth.add(Calendar.MONTH, 1);
        Injector injector = Guice.createInjector(new InterestModule());
        _interest = injector.getInstance(Interest.class);
        _debit = new Debit(new BigDecimal(2345));

    }


    @Test
    public void createAccountTest(){
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
    }

    @Test
    public void createAccountWithDebitTest(){
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, _debit));
    }

    @Test
    public void createLoanWithAccouuntTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        _manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest);
        Account account = _manager.getAccount(ownerId).get(0);
        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, account));
    }

    @Test
    public void createInvestmentWithAccountTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        _manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest);
        Account account = _manager.getAccount(ownerId).get(0);
        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(700), _nextMonth.getTime(), _interest, account));
    }

    @Test
    public void getAccountTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
        assertEquals(ownerId, account.getOwnerId());
    }

    @Test
    public void getInvestmentTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, account));
        Investment investment =_manager.getInvestment(ownerId).get(0);
        assertEquals(ownerId, investment.getOwnerId());
    }

    @Test
    public void getLoanTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        Account account =_manager.getAccount(ownerId).get(0);
        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, account));
        Loan loan =_manager.getLoan(ownerId).get(0);
        assertEquals(ownerId, loan.getOwnerId());
    }

    @Test
    public void getProductListTest()
    {
        int ownerId = _manager.getAvailableOwnerId();
        assertTrue(_manager.createNewProduct(Account.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest));
        List<Account> accounts =_manager.getAccount(ownerId);
        assertTrue(_manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));
        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));
        assertTrue(_manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500), _nextMonth.getTime(), _interest, accounts.get(0)));

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