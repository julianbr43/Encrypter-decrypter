package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.EncrypterDecrypter;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * This class defines the connection of the model with the visual part of the program
 * @author: Juli�n Andr�s Brito
 * @version: 5/06/2021
 */
public class Main extends Application {
	private static EncrypterDecrypter model = new EncrypterDecrypter();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("AppScreen.fxml"));
			primaryStage.getIcons().add(new Image(new File("resources/images/Icon.png").toURI().toString()));
			 Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Method that allows us to use the model
	 * 
	 * @return the main class of the model
	 */
	public static EncrypterDecrypter getmodel() {
		return model;
	}
}
