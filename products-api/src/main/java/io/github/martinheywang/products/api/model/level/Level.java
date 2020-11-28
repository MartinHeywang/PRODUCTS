/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.model.level;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;

/**
 * <p>
 * The level is a value that defines the efficiency of a device.<br>
 * More the level is high, more the device is performant.<br>
 * So it is directly associated with the
 * {@link io.github.martinheywang.products.api.model.device.Device} class.
 * </p>
 * <p>
 * Each constant defines also an accessible name and an efficiency value. The
 * accessible name is displayed in the UI and the efficency value is used to
 * generate the 'max act count' of a device.
 * </p>
 * <p>
 * It also defines an effect to apply to the image.
 * </p>
 */
public enum Level {
	/**
	 * Level 1.
	 * 
	 * <ul>
	 * <li>Efficiency: 1</li>
	 * <li>Accesible name : Niveau 1</li>
	 * </ul>
	 */
	LEVEL_1(1, "Niveau 1", new ColorAdjust(-.2, 0, -.1, 0)),

	/**
	 * Level 2.
	 * 
	 * <ul>
	 * <li>Efficiency: 2</li>
	 * <li>Accesible name : Niveau 2</li>
	 * </ul>
	 */
	LEVEL_2(2, "Niveau 2", new ColorAdjust(0, 0, 0, 0)),

	/**
	 * Level 3.
	 * 
	 * <ul>
	 * <li>Efficiency: 4</li>
	 * <li>Accesible name : Niveau 3</li>
	 * </ul>
	 */
	LEVEL_3(4, "Niveau 3", new ColorAdjust(.2, 0, .1, 0));

	/**
	 * The accesible name, displayed in the UI
	 */
	private String accessibleName;

	/**
	 * The efficiency value.
	 */
	private int efficiency;

	/**
	 * The effect to apply to on the image of the device.
	 */
	private Effect effect;

	/**
	 * Creates a new level
	 */
	Level(int efficiency, String accessibleName, Effect effect) {
		this.efficiency = efficiency;
		this.accessibleName = accessibleName;
		this.effect = effect;
	}

	/**
	 * Returns the next value in this enum. For example, this method invoked on a
	 * LEVEL_1 will return a LEVEL_2. If the level, is already at maximum, will
	 * return the max.
	 * 
	 * @return the next ordinal
	 */
	public Level getNext() {
		final int ordinal = this.ordinal();
		return Level.values()[ordinal + 1];
	}

	/**
	 * Returns the path to the image. The path doesn't depend on the level since 1.2.0
	 * and give the same result whatever the level is. Instead, to distinguish the
	 * different levels, an effect should be applied on the image.
	 * 
	 * @return the url of the level
	 */
	public String getURL() {
		return "/device/";
	}

	/**
	 * @return the efficiency value
	 */
	public int getValue() {
		return this.efficiency;
	}

	/**
	 * @return the accessible name
	 */
	public String getAccessibleName() {
		return this.accessibleName;
	}

	/**
	 * The effect to apply on the image for the given level.
	 * 
	 * @return the effect
	 */
	public Effect getEffect() {
		return this.effect;
	}
}