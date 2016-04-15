package Utils;

import Products.Product;

import java.util.List;

/**
 * Created by arasz on 03.04.2016.
 * Strategia generowania raportów
 */
public interface IReportCreationStrategy<T>
{
    Report<T> createReport(List<Product> products);
}
