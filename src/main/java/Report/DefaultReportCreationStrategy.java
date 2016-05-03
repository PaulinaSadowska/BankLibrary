package Report;

import Products.Product;
import Report.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created by arasz on 03.04.2016.
 */
public class DefaultReportCreationStrategy implements IReportCreationStrategy<String>
{
    public ReportDocument<String> createReport(List<Product> products)
    {
        StringBuilder report = new StringBuilder();

        report.append("Report created on "+ Calendar.getInstance().getTime().toString());
        report.append("Number of products: "+ products.size());
        report.append("\n");

       for(Product product : products)
       {
           report.append("Product type: "+product.getClass());
           report.append("Owner id: "+ product.getOwnerId());
           report.append("Balance: "+ product.getBalanceValue());
           report.append("Interest:"+ product.getInterest());
           report.append("Creation date: "+ product.getCreationDate());
           report.append("Expire date: "+ product.getExpireDate());
           report.append("\n");
       }

        return new ReportDocument<String>(report.toString());
    }
}
