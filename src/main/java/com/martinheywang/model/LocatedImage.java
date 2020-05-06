package com.martinheywang.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

/**
 * 
 * @author Martin Heywang
 * 
 *         LocatedImage represents an image, that's why she extends
 *         <a>javafx.scene.image.Image</a> The only difference with
 *         its parent is that she save the url in a field. You can at
 *         any time found wich URL was used. The only constructor
 *         takes as argument a String for URL. She doesn't give it to
 *         its parents, but within a FileInputStream and a File.
 *
 */
public class LocatedImage extends Image {
	private String str;

	/**
	 * 
	 * @param str the url of the image
	 * @throws FileNotFoundException
	 */
	public LocatedImage(String str) throws FileNotFoundException {
		super(new FileInputStream(new File(str)));
		this.str = str;
	}

	/**
	 * 
	 * @return str the url
	 */
	public String getURL() {
		return str;
	}

}
