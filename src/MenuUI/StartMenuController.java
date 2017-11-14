/**
 * Start Menu for ReallyAwesomeRPG<p>
 * 
 * @author Lucas Mailander
 */
package MenuUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import UIFramework.NavigationController;
import Persistence.FileIO;

public class StartMenuController {
		
	/** Send view change request
	 * @param event - the onAction event from fxml */ 
	@FXML private void transitionView(ActionEvent event) {
		// id button caller
		String btn = ((Button) event.getSource()).getId();
		String selection;
		NavigationController.PREVIOUS = NavigationController.STARTMENU;
		// send load request
                if (btn.startsWith("save"))
                        selection = btn;
		if (btn.equals("save"))
			// Save the game, stay on this view
                        saveGame(btn);
                if (btn.equals("load"))
                        // Load the game, trasition to main menu
                        
		if (btn.equals("loadgame"))
			NavigationController.loadView(NavigationController.SAVELOAD);

		// if this spot is reached: do nothing
	}
    
    private void saveGame(String fileName)
    {
        FileIO fileHandler = new FileIO();
        
        fileHandler.saveFile(fileName.charAt(-1));
    }
}