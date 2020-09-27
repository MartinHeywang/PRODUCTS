package com.martinheywang.products.model.bundles;

import java.util.ListResourceBundle;

public class GameLoopUpdate extends ListResourceBundle {
    
    @Override
	protected Object[][] getContents() {
		return new Object[][] {
                { "950", "22000" },
                { "900", "50000" },
                { "850", "75000" },
                { "800", "100000" },
                { "750", "150000" },
                { "700", "300000" },
                { "650", "400000" },
                { "600", "600000" },
                { "550", "800000" },
                { "500", "1000000" },
                { "450", "1200000" },
                { "400", "1500000" },
                { "350", "1800000" },
                { "300", "2000000" }
		};

	}
}
