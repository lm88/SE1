/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuUI;

import Persistence.FileIO;
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
