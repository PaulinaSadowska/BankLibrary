import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arasz on 18.03.2016.
 */
public class ProductManager {

    private ArrayList<Product> _productList;
    private ProductFactory _productFactory;

    public ProductManager(){
        _productFactory = new ProductFactory();
        _productList = new ArrayList<Product>();
    }

    public ArrayList<Product> getProductList() {
        return _productList;
    }

    public boolean createNewAccount(BigDecimal balance, Date expireDate, Interest interest,
                                    int ownerId , BigDecimal debitValue){
        try{
            _productList.add(_productFactory.createAccount(balance, expireDate, interest, ownerId, debitValue));
        }
        catch(Exception e){
            return false;
        }
        return true;
    }


}
