package Bank;

import Operations.InterbankTransferOperation;
import Products.Account;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
public interface CentralBank{
    void transfer(InterbankTransferOperation operation) throws BankException;
    void transferPayback(Account targetAccount, BigDecimal amount) throws BankException;
}
