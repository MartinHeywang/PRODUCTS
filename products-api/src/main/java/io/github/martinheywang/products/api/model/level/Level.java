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

/**
 * <p>
 * The level is a value that defines the efficiency of a device.<br>
 * More the level is high, more the device is performant.<br>
 * So it is directly associated with device.
 * </p>
 * <p>
 * It also helps defining the folder in which the image / 
 * view of the device can be found, as below :
 * </p>
 * <ul>
 * <li>Level 1 : [resource_folder]/images/devices_level_1/</li>
 * <li>Level 2 : [resource_folder]/images/devices_level_2/</li>
 * <li>Level 3 : [resource_folder]/images/devices_level_3/</li>
 * </ul>
 * 
 * <p>
 * Each constant defines also an accessible name and an efficiency value. 
 * The accessible name is displayed in the UI and the efficency value is used to 
 * generate the 'max act count' of a device.
 * </p>
 */
public enum Level {
	/**
	 * Level 1.
	 * 
	 * <ul>
	 * <li>Folder : [resource_folder]/images/devices_level_1/</li>
	 * <li>Efficiency: 1</li>
	 * <li>Accesible name : Niveau 1</li>
	 * </ul>
	 */
	LEVEL_1("devices_level_1", 1, "Niveau 1"), 

	/**
	 * Level 2.
	 * 
	 * <ul>
	 * <li>Folder : [resource_folder]/images/devices_level_2/</li>
	 * <li>Efficiency: 2</li>
	 * <li>Accesible name : Niveau 2</li>
	 * </ul>
	 */
	LEVEL_2("devices_level_2", 2, "Niveau 2"),

	/**
	 * Level 3.
	 * 
	 * <ul>
	 * <li>Folder : [resource_folder]/images/devices_level_3/</li>
	 * <li>Efficiency: 4</li>
	 * <li>Accesible name : Niveau 3</li>
	 * </ul>
	 */
	LEVEL_3("devices_level_3", 4, "Niveau 3");

	/**
	 * The folder url of the level
	 */
	private String url;

	/**
	 * The accesible name, displayed in the UI
	 */
	private String accessibleName;

	/**
	 * The efficiency value.
	 */
	private int efficiency;

	/**
	 * Creates a new level
	 */
	Level(String url, int efficiency, String accessibleName) {
		this.url = "/"+url+"/";
		this.efficiency = efficiency;
		this.accessibleName = accessibleName;
	}

	/**
	 * Returns the next value in this enum. For example, this method invoked on
	 * a LEVEL_1 will return a LEVEL_2. If the level, is already at maximum,
	 * will return the max.
	 * 
	 * @return the next ordinal
	 */
	public Level getNext() {
		final int ordinal = this.ordinal();
		return Level.values()[ordinal + 1];
	}

	/**
	 * Returns the URL of this level.
	 * 
	 * @return the url of the level
	 */	
	public String getURL() {
		return this.url;
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
}
