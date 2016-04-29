package Bank;

import Operations.ICommand;
import Operations.RefusedTransferPayback;
import Operations.TransferOperation;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
interface CentralBank{
    public void transfer(TransferOperation operation);
    public void transferPayback(RefusedTransferPayback operation);
}
