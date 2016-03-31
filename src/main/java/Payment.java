package main.java;

import java.math.BigDecimal;

/**
 * Created by arasz on 18.03.2016.
 */
public class Payment implements IPaymentable{

    @Override
    public void transfer(Product product, BigDecimal amount) {
        //TODO
    }

    @Override
    public void payment(BigDecimal amount, PaymentDirection direction) {
        if(direction == PaymentDirection.In) {
            //TODO
        }
        else if(direction == PaymentDirection.Out){
            //TODO
        }
    }
}
