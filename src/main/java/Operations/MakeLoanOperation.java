package Operations;

import Bank.BankException;
import Products.*;
import Products.Balance.Balance;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class MakeLoanOperation extends Operation implements ICommand
{
    private Integer ownerId;
    private Balance balance;
    private ProductDuration duration;
    private Interest interest;
    private ProductManager productManager;

    public MakeLoanOperation(Integer ownerId, Balance balance, ProductDuration duration, Interest interest, ProductManager productManager){
        super(OperationType.MakeLoan);
        this.balance = balance;
        this.duration = duration;
        this.interest = interest;
        this.ownerId = ownerId;
        this.productManager = productManager;
    }

    @Override
    public void execute() throws Exception
    {
        if(getExecuted())
            return;

        IAccount baseAccount = productManager.getAccount(ownerId).get(0);
        productManager.createNewProduct(Loan.class, ownerId, balance, duration, interest, baseAccount);

        executed = true;
    }
}
