package Products;

import Bank.BankException;
import Operations.Operation;
import Operations.OperationType;
import Products.Account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Kredyt.
 */
public class Loan extends Product
{

    private Account _baseAccount;

    public Loan(Integer ownerId, BigDecimal balance, Date expireDate, Interest interest, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        _history.add(new Operation(OperationType.MakeLoan));
        _baseAccount = baseAccount;
    }

    public BigDecimal getLoanRepayAmount()
    {
        return _balance.add(_interest.calculateInterest(this));
    }

    private boolean canClose()
    {
        BigDecimal initialBalance = _baseAccount.getBalanceWithDebit();
        BigDecimal repayAmount = getLoanRepayAmount();

        if(initialBalance.compareTo(repayAmount) >= 0)
            return   true;
        else
            return   false;
    }

    /**
     * Spłata kredytu.
     * @pre: _baseAccount.getBalance() > _balance
     * @post: baseAccount.getBalance()=- _balance
     * @invariant:
     * @return False jesli nie mozna spłacić kredytu
     */
    public void repay() throws BankException
    {
        if(!canClose())
            throw new BankException("Loan can not be closed", OperationType.RepayLoan);

        BigDecimal repayAmount = getLoanRepayAmount();

        _baseAccount.setBalance(_baseAccount.getBalance().subtract(repayAmount));
        _history.add(new Operation(OperationType.RepayLoan));
    }
}