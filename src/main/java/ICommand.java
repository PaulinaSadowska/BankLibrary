/**
 * Created by arasz on 15.04.2016.
 */
public interface ICommand
{
    /**
     * Executes command
     */
    void execute() throws BankException;
}