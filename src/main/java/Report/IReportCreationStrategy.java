package Report;

import Products.Product;

import java.util.List;

/**
 * Created by arasz on 03.04.2016.
 * Strategia generowania raportów
 */
public interface IReportCreationStrategy<T>
{
    ReportDocument<T> createReport(List<Product> products);
}
