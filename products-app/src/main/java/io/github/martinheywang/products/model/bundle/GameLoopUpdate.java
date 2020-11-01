package io.github.martinheywang.products.model.bundle;

import java.util.ListResourceBundle;

public class GameLoopUpdate extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
	return new Object[][] { { "950", "22000" }, { "900", "50000" }, { "850", "100000" }, { "800", "500000" },
	    { "750", "1200000" }, { "700", "2000000" }, { "650", "10000000" }, { "600", "40000000" },
	    { "550", "100000000" } };

    }
}
