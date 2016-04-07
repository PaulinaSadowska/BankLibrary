import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Paulina Sadowska on 07.04.2016.
 */
public class BankExceptionTest
{
    private String _message;
    private OperationType _operationType;

    @Before
    public void create(){
        _message = "some error message";
        _operationType = OperationType.MakeDebit;
    }

    @Test
    public void CreateExceptionWithDescriptionTest(){
        String expectedMessage = _message + "\n Operation which caused exception: "+ _operationType.toString();
        BankException exception = new BankException(_message, _operationType);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test (expected=BankException.class)
    public void throwBankException() throws BankException
    {
        throw new BankException(_message, OperationType.Payment);
    }
}
