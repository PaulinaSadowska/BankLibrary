import com.google.inject.Inject;

import javax.management.OperationsException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 * Kredyt.
 */
public class Loan extends Product implements IClosable
{

    protected Account _baseAccount;

    public Loan(BigDecimal balance, Date expireDate, Interest interest, int ownerId, Account baseAccount)
    {
        super(ownerId, balance, expireDate, interest);
        _baseAccount = baseAccount;
    }

    public Account getBaseAccount() {
        return _baseAccount;
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
    public boolean close()
    {
        BigDecimal repayAmount = getLoanRepayAmount();

        boolean canClose = canClose();

        if(canClose)
        {
            _baseAccount.setBalance(_baseAccount.getBalance().subtract(repayAmount));
            _history.add(new Operation(OperationType.RepayLoan, this));
        }

        return  canClose;


    }
}
