package com.martin.view;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Partie;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Accueil2Contrôle {

	@FXML
	VBox listePartie;
	@FXML
	TextArea field;
	Main main;

	@FXML
	private void nouvelle() {
		if (!field.getText().isEmpty()) {
			try {
				Partie partie = new Partie(field.getText());
				main.initGame(partie);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>
	 * Sets the object main
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;

		Session session = Connect_SQLite.getSession();

		try {
			Query<Partie> query = session.createQuery("from Partie",
					Partie.class);
			List<Partie> list = query.list();

			for (Partie partie : list) {
				Displayer displayer = new Displayer(partie);
				listePartie.getChildren().add(displayer);
				displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						try {
							main.initGame(((Displayer) event.getSource())
									.getPartie());
						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				});
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
