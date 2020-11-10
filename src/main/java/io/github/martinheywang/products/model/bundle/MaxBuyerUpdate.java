package io.github.martinheywang.products.model.bundle;

import java.util.ListResourceBundle;

/**
 * Bundle class that defines the price for upgrading the independent limit count.
 * It associates the key (the count limit) with the value (the price to go to the key value)
 * 
 * @author Martin Heywang
 */
public class MaxBuyerUpdate extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
	return new Object[][] { { "8", "15000" }, { "12", "40000" }, { "16", "100000" }, { "20", "150000" },
	    { "24", "500000" }, { "28", "1000000" }, { "32", "8500000" }, { "36", "14000000" },
	    { "40", "59000000" }, { "44", "150000000" }, { "48", "8000000000" }, { "52", "96000000000" },
	    { "56", "140000000000" }, { "60", "8700000000000" } };
    }
}
