package main.java;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class Account extends Product {

    private OperationManager mOperationManager;
    private int id;
    private String owner;
    private OperationsHistory mOperationHistory;

    public Account(BigDecimal amountOfMoney, Date dateStart, Date dateEnd, Interest interest,
                   int id, String owner, OperationManager manager, OperationsHistory history){
        this.amountOfMoney = amountOfMoney;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.interest = interest;
        this.id = id;
        this.owner = owner;
        this.mOperationManager = manager;
        this.mOperationHistory = history;
    }

    public OperationManager getOperationManager() {
        return mOperationManager;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public OperationsHistory getOperationHistory() {
        return mOperationHistory;
    }
}
