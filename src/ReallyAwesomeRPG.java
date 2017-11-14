/**
 * Main Application for ReallyAwesomeRPG<p>
 * 
 * @author Lucas Mailander
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import UIFramework.NavigationController;

public class ReallyAwesomeRPG extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// create the root
			Pane root = new Pane();
			//let nav control know what/where root is
			NavigationController.setRoot(root);
			// start at the start menu
		    NavigationController.loadView(NavigationController.STARTMENU);
			
		    // create the scene
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("UIFramework/rarpg.css").toExternalForm());
			
			// put the scene on the window
			primaryStage.setScene(scene);
			primaryStage.setTitle("Really Awesome RPG");
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}