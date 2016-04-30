package Operations;

import Bank.BankException;
import Products.Account;

import java.math.BigDecimal;

/**
 * Created by arasz on 15.04.2016.
 */
public class PaymentOperation extends Operation implements ICommand
{
    private Account sourceAccount;
    private BigDecimal amount;
    private PaymentDirection direction;

    public PaymentOperation(Account sourceAccount, PaymentDirection direction, BigDecimal amount, OperationType operationType)
    {
        super(operationType);
        this.sourceAccount = sourceAccount;
        this.direction = direction;
        this.amount = amount;
    }

    @Override
    public void execute() throws BankException
    {
        if(getExecuted())
            return;

        if(amount.longValueExact() < 0)
            throw new BankException("Negative amount");

        switch (direction)
        {
            case In:
            {
                BigDecimal newBalance = sourceAccount.getBalance().add(amount);
                sourceAccount.setBalance(newBalance);
                break;
            }
            case Out:
            {

                BigDecimal productBalance = sourceAccount.getBalance();
                // Zwraca 1 gdy amount jest wieksza od balance
                if (amount.compareTo(productBalance) <= 0)
                {
                    BigDecimal newBalance = sourceAccount.getBalance().subtract(amount);
                    sourceAccount.setBalance(newBalance);
                }
                else
                {
                    if (!sourceAccount.hasDebit())
                        throw new BankException("Output payment amount grater than account balance", OperationType.Payment);

                    BigDecimal balancePlusDebit = sourceAccount.getBalanceWithDebit();

                    if (balancePlusDebit.compareTo(amount) < 0)
                        throw new BankException("Output payment amount grater than account balance", OperationType.Payment);

                    sourceAccount.setBalance(productBalance.subtract(amount));
                }
                break;
            }
        }
        _executed = true;
    }
}
