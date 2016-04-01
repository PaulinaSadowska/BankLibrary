/**
 * Created by palka on 11.03.2016.
 */
public class Interest extends Product {

    private double _interestValue;

    public  Interest(){_interestValue = 0;}

    public Interest(double interestValue){
        _interestValue = interestValue;
    }

    public double get_interestValue(){
        return _interestValue;
    }

}
