import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by palka on 01.04.2016.
 */
public class ProductManagerTest {

    private ProductManager _manager;

    private int _searchedOwnerId;
    private int _otherOwnerId;
    Calendar _expectedCreationDate;
    Calendar _expectedExpireDate;
    Calendar _wrongExpireDate;
    Interest _interest;

    @Before
    public void setUp(){
        _expectedCreationDate  = Calendar.getInstance();

        _expectedExpireDate  = Calendar.getInstance();
        _expectedExpireDate.add(Calendar.DAY_OF_YEAR, 7);

        _wrongExpireDate  = Calendar.getInstance();
        _wrongExpireDate.add(Calendar.DAY_OF_YEAR, -7);

        _manager = new ProductManager();
        _manager.createNewAccount(new BigDecimal(9999), _expectedExpireDate.getTime(),
                _interest, _searchedOwnerId, new BigDecimal(300));
    }

    @Test
    public void createAccountTest(){

        assertTrue(_manager.createNewAccount(new BigDecimal(1000), _expectedExpireDate.getTime(),
                _interest, 999999, new BigDecimal(3000)));
    }

    @Test
    public void createNewAccountWrongExpireDate(){
        assertFalse(_manager.createNewAccount(new BigDecimal(3333), _wrongExpireDate.getTime(),
                _interest, _searchedOwnerId, new BigDecimal(0)));
    }

    @Test
    public void getProductsByOwnerIdTest(){
        //TODO - implement me!
    }

    @Test
    public void getProductsByProductTypeTest(){
        //todo - implement me!
    }
}