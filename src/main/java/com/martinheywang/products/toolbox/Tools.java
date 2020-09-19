package com.martinheywang.products.toolbox;

import java.io.IOException;

import com.martinheywang.products.Main;

import javafx.fxml.FXMLLoader;

public final class Tools {

	private Tools() {
	}

	/**
	 * Prepares a new {@link FXMLoader} with the given string as input. Do
	 * not specify the extension nor the directory, as long as this file
	 * is in a resource directory.<br>
	 * 
	 * <pre>
	 * <code>
	 * //The loaded file here is src/main/resources/fxml/Game.fxml
	 * FXMLLoader loader = prepareFXMLLoader("Game");
	 * 
	 * </code>
	 * </pre>
	 * 
	 * @param file the name of the file to load
	 * @return a new FXMLLoader
	 * @throws IOException if the file couldn't not be loaded.
	 */
	public static FXMLLoader prepareFXMLLoader(String file) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fxml/" + file + ".fxml"));
		return loader;
	}

}
