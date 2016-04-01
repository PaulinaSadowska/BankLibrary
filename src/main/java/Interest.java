import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by palka on 11.03.2016.
 */
public class Interest{

    private double _interestValue;

    public Interest(double interestValue)
    {
        _interestValue = interestValue;
    }

    public Interest()
    {
        _interestValue = 0;
    }

    public double getInterestValue(){
        return _interestValue;
    }

}
