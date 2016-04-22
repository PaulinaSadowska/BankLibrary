package Products;

import Operations.ICommand;

/**
 * Created by arasz on 22.04.2016.
 */
public interface IAccount extends IProduct
{
    void doOperation(ICommand operation) throws Exception;
}
