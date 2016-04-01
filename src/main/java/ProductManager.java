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

    public boolean createNewAccount(BigDecimal balance, Date expireDate, Interest interest, int ownerId , BigDecimal debitValue){
        return createNewProduct(ProductType.Account, null, balance, expireDate, interest, ownerId, debitValue);
    }

    public boolean createNewLoan(Account baseAccount, BigDecimal balance, Date expireDate, Interest interest){
        return createNewProduct(ProductType.Loan, baseAccount, balance, expireDate, interest, -1, null);
    }

    public boolean createNewInvestment(Account baseAccount, BigDecimal balance, Date expireDate, Interest interest){
        return createNewProduct(ProductType.Investment, baseAccount, balance, expireDate, interest, -1, null);
    }

    private boolean createNewProduct(ProductType type, Account baseAccount, BigDecimal balance, Date expireDate, Interest interest,
                                     int ownerId, BigDecimal debit){
        try{
            switch(type){
                case Investment:
                    _productList.add(_productFactory.createInvestment(baseAccount, balance, expireDate, interest));
                    break;
                case Loan:
                    _productList.add(_productFactory.createLoan(baseAccount, balance, expireDate, interest));
                    break;
                case Account:
                    _productList.add(_productFactory.createAccount(balance, expireDate, interest, ownerId, debit));
                    break;
            }
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    //pobierz produkty po ownerId lub typie


}
