package test;


import junit.framework.TestCase;
import main.java.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by arasz on 18.03.2016.
 */
public class OperationFactoryTest{

    /*OperationFactory mOperationFactory;
    Product mAccount;*/

    @Before
    public void setUp() throws Exception {
        //mOperationFactory = new OperationFactory();
        //mAccount = new Account();
    }

    @Test
    public void wykonajTest(){
        /*Operation operation = mOperationFactory.MakeOperation(OperationType.MakeDebit, new Date(2006, 6, 9), "some make debit operation", mAccount);
        assertEquals(operation.getOperationType(), OperationType.MakeDebit);
        assertEquals(operation.getDate(), new Date(2006, 6, 9));
        assertEquals(operation.getDescription(), "some make debit operation");*/
    }

    @After
    public void tearDown() throws Exception {

    }
}
