package Bank;

import Operations.ICommand;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
interface CentralBank{
    public void transfer(Integer bankId, ICommand operation);
}
