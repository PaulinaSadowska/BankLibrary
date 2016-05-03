package Operations;

import Bank.BankException;
import Products.IAccount;
import Products.Loan;
import Utils.BigDecimalComparator;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class RepayLoanOperation extends Operation implements ICommand
{
    private Loan loan;
    private IAccount baseAccount;

    public RepayLoanOperation(Loan loan)
    {
        super(OperationType.RepayLoan);
        this.loan = loan;
        this.baseAccount = loan.getBaseAccount();
    }

    @Override
    public void execute() throws Exception
    {
        if(!canClose())
            throw new BankException("Loan can not be closed", OperationType.RepayLoan);

        BigDecimal repayAmount = loan.getBalanceValue();
        baseAccount.subtractFromBalance(repayAmount);
    }

    private boolean canClose()
    {
        BigDecimal initialBalance = baseAccount.getBalanceValue();
        BigDecimal repayAmount = loan.getBalanceValue();

        return BigDecimalComparator.GreaterOrEqual(initialBalance, repayAmount);
    }
}
