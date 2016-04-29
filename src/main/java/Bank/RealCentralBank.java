package Bank;

import Operations.ICommand;
import Operations.RefusedTransferPayback;
import Operations.TransferOperation;

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
    public void transfer(TransferOperation operation)
    {
        //send money to bank with given id
    }

    @Override
    public void transferPayback(RefusedTransferPayback operation)
    {
        //send money back
    }
}
