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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameCreationController {
	
	@FXML Button teamMember1, teamMember2, teamMember3; //Inject element IDs into controller
	
	@FXML HBox typeSelectContainer; //Inject element IDs into controller
	
	Player thisPlayer = new Player(); //Instantiate Player object
	
	String teamMember; //Team member index
	int index;
	
	private int getIndex(String teamMember) {
		return (teamMember == "teamMember1") ? 0 : (teamMember == "teamMember2") ? 1 : 2;
	}
	
	@FXML
	private void handlePlayerSelect(ActionEvent event) throws IOException {
		teamMember = ((Button) event.getSource()).getId();
		index = getIndex(teamMember);
		
		typeSelectContainer.setVisible(true);
		
	}
	
	private void replaceButton(int index) {
		if(index == 0) {
			Image image = new Image(getClass().getResourceAsStream("fireIcon.png"));
			teamMember1.setGraphic(new ImageView(image));
		}
		else if(index == 1) {
			Image image = new Image(getClass().getResourceAsStream("fireIcon.png"));
			teamMember2.setGraphic(new ImageView(image));
		}
		else {
			Image image = new Image(getClass().getResourceAsStream("fireIcon.png"));
			teamMember3.setGraphic(new ImageView(image));
		}
	}
	
	@FXML
	private void handleTypeSelect(ActionEvent event) throws IOException {
		String type = ((Button) event.getSource()).getId();
		
		switch(type) {
			case "fire":
				thisPlayer.unitList.add(index, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				//replaceButton(index);
				break;
			case "earth":
				thisPlayer.unitList.add(index, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				//replaceButton(index);
				break;
			case "water":
				thisPlayer.unitList.add(index, new Unit(type, 10));
				typeSelectContainer.setVisible(false);
				//replaceButton(index);
				break;	
		}
		
		
	}
	
	/** Send view change request */ 
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.BATTLE);
	}
}
