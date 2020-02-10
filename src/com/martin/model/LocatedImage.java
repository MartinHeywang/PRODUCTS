package com.martin.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

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
