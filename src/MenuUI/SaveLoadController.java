/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuUI;

import UIFramework.NavigationController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author becky
 */
public class SaveLoadController {

 	/** Send view change request
	 * @param event - the onAction event from fxml */ 
	@FXML private void transitionView(ActionEvent event) {
		// id button caller
		String btn = ((Button) event.getSource()).getId();
                String previous = NavigationController.PREVIOUS;
		NavigationController.PREVIOUS = NavigationController.SAVELOAD;
		// send load request
		if (btn.equals("back"))
			NavigationController.loadView(previous);
		if (btn.equals("loadgame"))
			NavigationController.loadView(NavigationController.SAVELOAD);

		// if this spot is reached: do nothing
	}
    
    /** Send exit request */
	@FXML private void exit() {
		NavigationController.closeApplication();
    }
}
