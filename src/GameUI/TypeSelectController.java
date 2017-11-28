/**
 * 
 */
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import UIFramework.NavigationController;

/**
 * @author Kenneth Dale
 *
 */
public class TypeSelectController {
	@FXML
	private Label modalTitle;
	
	Stage stage;
	int teamMemberIndex;
	
	Player thisPlayer = new Player();
	//GameCreationController gcc = new GameCreationController();
	
	@FXML
	public void displayModal() throws IOException {
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
}
