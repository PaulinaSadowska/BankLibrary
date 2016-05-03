package Bank;

import Operations.*;
import Products.Account;
import Products.IAccount;
import Products.Product;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
public class RealCentralBank implements CentralBank
{
    private HashMap<Integer, Bank> banks = new HashMap<Integer, Bank>();

    public void registerBank(Bank bank) {
        int key = -1;
        while(banks.containsKey(key)||key<1){
            key=(int)(Math.random()*Integer.MAX_VALUE);
        }
        bank.registerCentralBank(this, key);
        banks.put(bank.getId(), bank);
    }

    @Override
    public void transfer(InterbankTransferOperation operation) throws BankException
    {
        //send money to bank with given id
        Bank targetBank = banks.get(operation.getTargetBankId());
        if(targetBank == null)
        {
            transferPayback(operation.getSourceAccount(), operation.getAmount());
            return;
        }
        IAccount targetAccount = targetBank.getAccount(operation.getTargetAccountId());
        if(targetAccount == null){
            transferPayback(operation.getSourceAccount(), operation.getAmount());
            return;
        }
        PaymentOperation payment = new PaymentOperation(targetAccount, PaymentDirection.In, operation.getAmount());
        try
        {
            payment.execute();
        }
        catch (BankException e)
        {
            e.printStackTrace();
            transferPayback(operation.getSourceAccount(), operation.getAmount());
        }
    }

    @Override
    public void transferPayback(IAccount targetAccount, BigDecimal amount) throws BankException
    {
        RefusedTransferPayback payback =
                new RefusedTransferPayback(targetAccount, amount);
        payback.execute();
    }

}
