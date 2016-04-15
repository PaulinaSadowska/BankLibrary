package Utils;

import Products.Product;

import java.math.BigDecimal;

/**
 * Created by arasz on 02.04.2016.
 * Interfejs umożliwiający obliczanie odsetek
 */
public interface IInterestCalculationStrategy
{
    BigDecimal calculateInterest(Product product, double percent);
}
