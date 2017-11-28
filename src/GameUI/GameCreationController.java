package GameUI;
/**
 * @author Kenneth Dale
 *
 */
import DataModels.Player;
import DataModels.Unit;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import UIFramework.NavigationController;

public class GameCreationController {
	@FXML
	private Button teamMember1;
	
	@FXML
	private Button teamMember2;
	
	@FXML
	private Button teamMember3;
	
	
	
	Player thisPlayer = new Player();
	TypeSelectController tsc = new TypeSelectController();
	Stage stage;
	int teamMemberIndex;
	
	@FXML
	private void handlePlayerSelect(ActionEvent event) throws IOException {
		//teamMemberIndex = Integer.parseInt(((Button) event.getSource()).getId());
		
		tsc.displayModal();
	}
	
	@FXML
	private void handleTypeSelect(ActionEvent event) throws IOException {
		String type = ((Button) event.getSource()).getId();
		Button thisButton = (Button) event.getSource();
		Stage currentStage = (Stage) thisButton.getScene().getWindow();
		
		switch(type) {
			case "fire":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				currentStage.hide();
				break;
			case "earth":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				currentStage.hide();
				break;
			case "water":
				thisPlayer.unitList.add(teamMemberIndex, new Unit(type, 10));
				currentStage.hide();
				break;
		}
		
	}
	
	
	
	/** Send view change request */ 
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.BATTLE);
	}
}