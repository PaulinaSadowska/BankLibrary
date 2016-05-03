package ProductsTests;

import Bank.BankException;
import Products.*;
import Products.Balance.Balance;
import Utils.IInterestCalculationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest
{

    private ProductManager manager;
    private Interest interest;

    @Before
    public void setUp(){
        manager = new ProductManager();
        interest = new Interest(mock(IInterestCalculationStrategy.class), 1.1);
    }


    @Test
    public void createAccountTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);
        assertEquals((int)ownerId, account.getOwnerId());
    }

    @Test
    public void createLoanWithAccountTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);

        Product loan = manager.createNewProduct(Loan.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
        assertNotNull(loan);
        assertTrue(loan instanceof Loan);
    }

   @Test
    public void createInvestmentWithAccountTest() throws Exception
   {
       Integer ownerId = manager.getAvailableOwnerId();
       Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
       assertNotNull(account);

       Product investment = manager.createNewProduct(Investment.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
       assertNotNull(investment);
       assertTrue(investment instanceof Investment);
    }

    @Test (expected = NoSuchMethodException.class)
    public void createLoanWithoutAccountTest_ThrowsException() throws Exception
    {
        Product loan = manager.createNewProduct(Loan.class, 123, new Balance(1233), new ProductDuration(1,1), interest, 123);
        assertNull(loan);
    }

    @Test
    public void getAccountTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);

        List<IAccount> searchedAccount = manager.getAccount(ownerId);
        assertEquals(1, searchedAccount.size());
        assertEquals(account, searchedAccount.get(0));

    }

    @Test
    public void getInvestmentTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);

        Product investment = manager.createNewProduct(Investment.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
        assertNotNull(investment);

        List<Investment> searchedInvestment = manager.getInvestment(ownerId);
        assertEquals(1, searchedInvestment.size());
        assertEquals(investment, searchedInvestment.get(0));
    }

    @Test
    public void getLoanTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);

        Product loan = manager.createNewProduct(Loan.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
        assertNotNull(loan);

        List<Loan> searchedLoan = manager.getLoan(ownerId);
        assertEquals(1, searchedLoan.size());
        assertEquals(loan, searchedLoan.get(0));
    }

    @Test
    public void getProductListTest() throws Exception
    {
        Integer ownerId = manager.getAvailableOwnerId();
        Product account = manager.createNewProduct(Account.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, 11);
        assertNotNull(account);

        Product investment = manager.createNewProduct(Investment.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
        assertNotNull(investment);

        Product loan = manager.createNewProduct(Loan.class, ownerId, new Balance(1233), new ProductDuration(1,1), interest, (Account)account);
        assertNotNull(loan);

        List<IProduct> products = manager.getProductList();
        assertEquals(3, products.size());
        assertTrue(products.contains(account));
        assertTrue(products.contains(loan));
        assertTrue(products.contains(investment));
    }

}