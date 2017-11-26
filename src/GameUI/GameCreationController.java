package GameUI;

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
import UIFramework.NavigationController;

public class GameCreationController {
	Player thisPlayer = new Player();
	Stage stage;
	int teamMemberNum;
	

	@FXML
	private void handlePlayerSelect(ActionEvent event) throws IOException {
		teamMemberNum = Integer.parseInt(((Button) event.getSource()).getId());
		displayModal();
	}
	
	@FXML
	private void handleTypeSelect(ActionEvent event) throws IOException {
		String type = ((Button) event.getSource()).getId();
		Button thisButton = (Button) event.getSource();
		Stage currentStage = (Stage) thisButton.getScene().getWindow();
		switch(type) {
			case "fire":
				currentStage.hide();
				break;
			case "earth":
				currentStage.hide();
				break;
			case "water":
				currentStage.hide();
				break;
		}
		
	}
	
	@FXML
	private void displayModal() throws IOException {
		FXMLLoader modal = new FXMLLoader(getClass().getResource("typeSelect.fxml"));
		Parent root1 = (Parent) modal.load();
		stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root1);
		scene.setFill(null);
		scene.getStylesheets().add(getClass().getResource("/UIFramework/rarpg.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	
	@FXML
	private void closeModal() {
		Platform.exit();
	}
	
	private void setType() {
		
	}
	
	/** Send view change request */ 
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.BATTLE);
	}
}