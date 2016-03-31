package main.java;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 * stores information about the investment
 */
public class Investment extends Product{

    protected Account baseAccount;

    public Investment(Account baseAccount, BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest){
        this.baseAccount = baseAccount;
        this.amountOfMoney = amountOfMoney;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.interest = interest;
    }

    public Account getBaseAccount() {
        return baseAccount;
    }

}
