package Bank;

import Operations.InterbankTransferOperation;
import Products.Account;
import Products.IAccount;

import java.math.BigDecimal;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
public interface CentralBank{
    void transfer(InterbankTransferOperation operation) throws BankException;
    void transferPayback(IAccount targetAccount, BigDecimal amount) throws BankException;
}
