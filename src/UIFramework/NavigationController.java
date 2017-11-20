/**
 * Navigation Control for ReallyAwesomeRPG<p>
 * 
 * The static method in this class allows views (a container of child classes)<p>
 * to be swapped around in the root view (the parent container)<p>
 * 
 * The static Strings are constants accessible to others<p>
 * so there is only one location for the file URL to be changed<p>
 * 
 * @author Lucas Mailander
 */
package UIFramework;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class NavigationController {
	
	// list of view.fxml file locations
	public static final String BATTLE		= "/GameUI/Battle.fxml";
	public static final String GAMECREATION	= "/GameUI/GameCreation.fxml";
	public static final String MAINMENU		= "/MenuUI/MainMenu.fxml";
	public static final String MARKET		= "/MenuUI/Market.fxml";
	public static final String SAVELOAD		= "/MenuUI/SaveLoad.fxml";
	public static final String STARTMENU	= "/MenuUI/StartMenu.fxml";
	public static String PREVIOUS;
	private static Pane root;
    
	/** Set the root Pane for the application
	 * @param root - the root Pane */
    public static void setRoot(Pane root) {
    	NavigationController.root = root;
    }
    
    /** Change the current view
     * @param viewFXML - the view.fxml file location */
    public static void loadView(String viewFXML) {
        try {
            // load up the new view
		    Node node = FXMLLoader.load(NavigationController.class.getResource(viewFXML));
        	// replace the root's children (setAll = old view removed, new view added)
		    root.getChildren().setAll(node);
		    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** Close the application */
    public static void closeApplication() {
    	Platform.exit();
    }
}