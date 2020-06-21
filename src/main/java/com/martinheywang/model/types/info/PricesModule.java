package com.martinheywang.model.types.info;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * A PricesModule is data stock for the prices of the devices. It
 * should only be used by types implementing {@link Type}.<br>
 * <br>
 * It represents the prices that a device can have for :
 * <ul>
 * <li>level_1_build: the cost of building a device</li>
 * <li>level_2_build: the cost of upgrading a device to level 2</li>
 * <li>level_3_build: the cost of upgrading a device to level 3</li>
 * <li>level_1_delete: the gain of deleting a device of level 1</li>
 * <li>level_2_delete: the gain of deleting a device of level 2</li>
 * <li>level_3_delete: the gain of deleting a device of level 3</li>
 * </ul>
 * 
 * A price in this module is sotred as a {@link BigInteger}, so the
 * corresponding method of each value will return this type. Note that
 * you can use {@link PricesModule#getPriceFromKey(String)} for a
 * shorcut to the value using the key. <br>
 * <br>
 * 
 * Each Device's type must have an associated PricesModule.
 * 
 * @author Heywang
 */
public class PricesModule {

	/**
	 * The main list of data
	 */
	private final HashMap<String, BigInteger> prices = new HashMap<>();

	/**
	 * Creates a new Prices Module, with the attributed values.
	 * 
	 * @param level1build
	 * @param level2build
	 * @param level3build
	 * @param level1delete
	 * @param level2delete
	 * @param level3delete
	 */
	public PricesModule(BigInteger level1build, BigInteger level2build,
			BigInteger level3build, BigInteger level1delete,
			BigInteger level2delete, BigInteger level3delete) {

		prices.put("level_1_build", level1build);
		prices.put("level_2_build", level2build);
		prices.put("level_3_build", level3build);

		prices.put("level_1_delete", level1delete);
		prices.put("level_2_delete", level2delete);
		prices.put("level_3_delete", level3delete);
	}

	/**
	 * 
	 * Creates a new Prices Module, with attributed values.
	 * 
	 * @param level1build
	 * @param level2build
	 * @param level3build
	 * @param level1delete
	 * @param level2delete
	 * @param level3delete
	 */
	public PricesModule(String level1build, String level2build,
			String level3build, String level1delete,
			String level2delete, String level3delete) {

		prices.put("level_1_build", new BigInteger(level1build));
		prices.put("level_2_build", new BigInteger(level2build));
		prices.put("level_3_build", new BigInteger(level3build));

		prices.put("level_1_delete", new BigInteger(level1delete));
		prices.put("level_2_delete", new BigInteger(level2delete));
		prices.put("level_3_delete", new BigInteger(level3delete));
	}

	/**
	 * 
	 * @return the level 1 build
	 */
	public BigInteger getLevel1Build() {
		return prices.get("level_1_build");
	}

	/**
	 * 
	 * @return the level 2 build (or upgrade)
	 */
	public BigInteger getLevel2Build() {
		return prices.get("level_2_build");
	}

	/**
	 * 
	 * @return the level 3 build (or upgrade)
	 */
	public BigInteger getLevel3Build() {
		return prices.get("level_3_build");
	}

	/**
	 * 
	 * @return the level 1 delete
	 */
	public BigInteger getLevel1Delete() {
		return prices.get("level_1_delete");
	}

	/**
	 * 
	 * @return the level 2 delete
	 */
	public BigInteger getLevel2Delete() {
		return prices.get("level_2_delete");
	}

	/**
	 * 
	 * @return the level 3 delete
	 */
	public BigInteger getLevel3Delete() {
		return prices.get("level_3_delete");
	}

	/**
	 * Returns the prices associated to the given key. The possible values
	 * for the key are:
	 * <ul>
	 * <li>level_1_build</li>
	 * <li>level_2_build</li>
	 * <li>level_3_build</li>
	 * <li>level_1_delete</li>
	 * <li>level_2_delete</li>
	 * <li>level_3_delete</li>
	 * </ul>
	 * 
	 * More informations can be found about their significations
	 * {@link PricesModule here}.
	 * 
	 * @param key the key
	 * @return the value associated to the key, or null if none is found.
	 */
	public BigInteger getPriceFromKey(String key) {
		return prices.get(key);
	}

}
