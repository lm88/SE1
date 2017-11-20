package GameUI;

import javafx.fxml.FXML;
import UIFramework.NavigationController;

public class GameCreationController {
	
	/** Send view change request */ 
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.BATTLE);
	}
}