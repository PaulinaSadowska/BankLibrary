import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest {

    private ProductManager _manager;

    private int _expectedOwnerId;
    private BigDecimal _expectedBalance;
    Calendar _expectedCreationDate;
    Calendar _expectedExpireDate;
    Calendar _wrongExpireDate;

    private BigDecimal _expectedDebitValue;
    private Interest _expectedInterest;

    @Before
    public void setUp(){
        _expectedCreationDate  = Calendar.getInstance();

        _expectedExpireDate  = Calendar.getInstance();
        _expectedExpireDate.add(Calendar.DAY_OF_YEAR, 7);

        _wrongExpireDate  = Calendar.getInstance();
        _wrongExpireDate.add(Calendar.DAY_OF_YEAR, -7);


        _expectedBalance = new BigDecimal(1700);
        _expectedOwnerId = 123456;
        _expectedDebitValue = new BigDecimal(6000);
        _expectedInterest = new Interest(0.5);

        _manager = new ProductManager();
    }

    @Test
    public void createAccountTest(){

        assertTrue(_manager.createNewAccount(_expectedBalance, _expectedExpireDate.getTime(),
                _expectedInterest, _expectedOwnerId, _expectedDebitValue));
    }

    @Test
    public void createNewAccountWrongExpireDate(){
        assertFalse(_manager.createNewAccount(_expectedBalance, _wrongExpireDate.getTime(),
                _expectedInterest, _expectedOwnerId, _expectedDebitValue));
    }
}