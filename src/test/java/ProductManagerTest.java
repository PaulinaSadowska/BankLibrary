
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;

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
    public void createAccountWithoutDebitTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Product account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                    Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        assertEquals((int)ownerId, account.getOwnerId());
    }

    @Test
    public void createAccountWithDebitTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Product account = _manager.createNewProduct(Account.class, ownerId, new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3),
                new Debit(new BigDecimal(123)));
        assertNotNull(account);
        assertEquals((int)ownerId, account.getOwnerId());
    }

    @Test
    public void createLoanWithAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
            Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        Loan loan = _manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(loan);
        assertEquals(loan.getOwnerId(), account.getOwnerId());
    }

   @Test
    public void createInvestmentWithAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
   {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        Investment investment = _manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(investment);
        assertEquals(investment.getOwnerId(), account.getOwnerId());
    }

    @Test
    public void getAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Product account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        assertEquals((int)ownerId, account.getOwnerId());
        assertTrue(_manager.getAccount(ownerId).contains(account));
    }

    @Test
    public void getInvestmentTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        Investment investment = _manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(investment);
        assertTrue(_manager.getInvestment(ownerId).contains(investment));
    }

    @Test
    public void getLoanTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        Loan loan = _manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(loan);
        assertTrue(_manager.getLoan(ownerId).contains(loan));
    }

    @Test
    public void getProductListTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        Loan loan = _manager.createNewProduct(Loan.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(loan);
        Investment investment = _manager.createNewProduct(Investment.class, ownerId, new BigDecimal(1500),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3), account);
        assertNotNull(investment);
        assertTrue(_manager.getProductList(ownerId).contains(loan));
        assertTrue(_manager.getProductList(ownerId).contains(account));
        assertTrue(_manager.getProductList(ownerId).contains(investment));
    }

    @Test
    public void deleteAccountTest() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Integer ownerId = _manager.getAvailableOwnerId();
        Account account = _manager.createNewProduct(Account.class, ownerId,  new BigDecimal(1200),
                Calendar.getInstance().getTime(), new Interest(mock(TimeDependentInterestCalculationStrategy.class), 0.3));
        assertNotNull(account);
        assertTrue(_manager.getProductList(ownerId).contains(account));
        _manager.deleteProduct(account);
        assertFalse(_manager.getProductList(ownerId).contains(account));
    }
}