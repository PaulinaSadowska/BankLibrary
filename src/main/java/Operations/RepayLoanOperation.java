package Operations;

import Bank.BankException;
import Products.Account;
import Products.Loan;
import Utils.BigDecimalComparator;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class RepayLoanOperation extends Operation implements ICommand
{
    private Loan _loan;
    private Account _baseAccount;

    public RepayLoanOperation(Loan loan)
    {
        super(OperationType.RepayLoan);
        this._loan = loan;
        this._baseAccount = loan.getBaseAccount();
    }

    @Override
    public void execute() throws Exception
    {
        if(!canClose())
            throw new BankException("Loan can not be closed", OperationType.RepayLoan);

        BigDecimal repayAmount = _loan.getBalanceValue();
        _baseAccount.subtractFromBalance(repayAmount);
    }

    @Override
    public void undo() throws BankException
    {

    }

    private boolean canClose()
    {
        BigDecimal initialBalance = _baseAccount.getBalanceValue();
        BigDecimal repayAmount = _loan.getBalanceValue();

        return BigDecimalComparator.GreaterOrEqual(initialBalance, repayAmount);
    }
}
