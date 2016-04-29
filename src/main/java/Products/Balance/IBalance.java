package Products.Balance;

import java.math.BigDecimal;

/**
 * Created by arasz on 29.04.2016.
 */
public interface IBalance
{
    /**
     * Adds given ammount from balance.
     * @param amount amount to substract. Can't be greater than balance and negative
     */
    void addToBalance(BigDecimal amount) throws BalanceException;

    /**
     * Substracts given ammount from balance.
     * @param amount amount to substract. Can't be greater than balance and negative
     */
    void substractFromBalance(BigDecimal amount) throws BalanceException;

    /**
     * Gets balance
     * @return balance
     */
    BigDecimal getBalanceValue();
}
