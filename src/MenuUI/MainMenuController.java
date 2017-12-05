package MenuUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import UIFramework.NavigationController;

public class MainMenuController {
	
	@FXML Label bannerMsg;
	
	@FXML private void initialize() {
		bannerMsg.setText(NavigationController.WINNERMSG);
	}
	
	/** Send view change request
	 * @param event - the onAction event from fxml */ 
	@FXML private void transitionView(ActionEvent event) {
		// id button caller
		String btn = ((Button) event.getSource()).getId();
		NavigationController.PREVIOUS = NavigationController.MAINMENU;
		// send load request
		if (btn.equals("continue"))
			NavigationController.loadView(NavigationController.BATTLE);
		if (btn.equals("market"))
			NavigationController.loadView(NavigationController.MARKET);
		if (btn.equals("savegame"))
			NavigationController.loadView(NavigationController.SAVELOAD);
		if (btn.equals("exitgame"))
			NavigationController.loadView(NavigationController.STARTMENU);
		
		// if this spot is reached: do nothing
	}
}