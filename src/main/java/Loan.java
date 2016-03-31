package main.java;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public class Loan extends Product{

    protected Account baseAccount;

    public Loan(Account baseAccount, BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest){
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
