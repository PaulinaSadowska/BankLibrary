/**
 * @author arasz
 * Umozliwia wycofanie operacji
 */
public interface IReversable
{
    /**
     *  Stan produktu przed wykonaniem operacji
     */
    Product _frozenProductState = null;
    /**
     * Odwraca operację która została wykonana na produkcie
     */
    void Reverse();
}
