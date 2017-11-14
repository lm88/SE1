/**
 * Start Menu for ReallyAwesomeRPG
 * 
 * @author Lucas Mailander
 */
package MenuUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import UIFramework.NavigationController;

public class StartMenuController {
		
	/** Send view change request
	 * @param event - the onAction event from fxml */ 
	@FXML private void transitionView(ActionEvent event) {
		// id button caller
		String btn = ((Button) event.getSource()).getId();
		NavigationController.PREVIOUS = NavigationController.STARTMENU;
		// send load request
		if (btn.equals("newgame"))
			NavigationController.loadView(NavigationController.GAMECREATION);
		if (btn.equals("loadgame"))
			NavigationController.loadView(NavigationController.SAVELOAD);

		// if this spot is reached: do nothing
	}
    
    /** Send exit request */
	@FXML private void exit() {
		NavigationController.closeApplication();
    }
}