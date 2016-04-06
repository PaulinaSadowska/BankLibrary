import javax.management.OperationsException;

/**
 * Created by arasz on 02.04.2016.
 */
public interface IClosable
{
    void close() throws BankException;
}
