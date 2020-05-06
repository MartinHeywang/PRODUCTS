package com.martinheywang.model.bundles;

import java.util.ListResourceBundle;

/**
 * This class is list resou
 */
public class GrilleUpdate extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
				{ "4", "20000" },
				{ "5", "50000" },
				{ "6", "200000" },
				{ "7", "600000" },
				{ "8", "1000000" },
				{ "9", "1500000" },
				{ "10", "2500000" }
		};

	}

}
