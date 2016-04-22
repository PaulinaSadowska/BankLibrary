package Bank;

import Operations.ICommand;
import Operations.TransferOperation;

import java.util.HashMap;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
public class RealCentralBank implements CentralBank
{
    private HashMap<Integer, Bank> banks = new HashMap<Integer, Bank>();

    public void registerBank(Bank bank) {
        bank.registerCentralBank(this);
        banks.put(bank.getId(), bank);
    }

    @Override
    public void transfer(Integer bankId, ICommand operation)
    {
        //send money to bank with given id
        banks.get(bankId);
    }
}
