package GameUI;
/**
 * @author Kenneth Dale
 *
 */
import java.io.IOException;

import DataModels.Player;
import DataModels.Unit;
import UIFramework.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GameCreationController {
	@FXML Button teamMember1, teamMember2, teamMember3;
	
	@FXML HBox typeSelectContainer;
	
	Player thisPlayer = new Player();
	int teamMemberIndex;
	
	@FXML
	private void handlePlayerSelect(ActionEvent event) throws IOException {
		//teamMemberIndex = Integer.parseInt(((Button) event.getSource()).getId());
		typeSelectContainer.setVisible(true);
		
	}
	
	@FXML
	private void handleTypeSelect(ActionEvent event) throws IOException {
		String type = ((Button) event.getSource()).getId();
		
		switch(type) {
			case "fire":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				break;
			case "earth":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				break;
			case "water":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				break;
				
			
		}
		
		
	}
	
	/** Send view change request */ 
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.BATTLE);
	}
}
