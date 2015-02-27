/**
 * @author Merv Fansler
 * @since February 19, 2015
 * @version 0.1.0
 */

package edu.millersville.cs.bitsplease;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Penultimate UML Builder Application
 */
public class PUMLBuilder extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GUIController controller = new GUIController();
		Scene scene = new Scene(controller.getEditorPane(), 800, 600);
		
		primaryStage.setTitle("Penultimate UML Builder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(PUMLBuilder.class, args);
	}

}
