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
	
	@FXML Button teamMember1, teamMember2, teamMember3, battleButton; //Inject element IDs into controller
	
	@FXML HBox typeSelectContainer; //Inject element IDs into controller
	
	String teamMember; //Team member index
	int index; //Index for placement in arrayList
	
	/********************************************************
	 * Clear unitList on load.  Initialize array list to a 
	 * fixed size.
	 *******************************************************/
	@FXML 
	public void initialize() {
		Player.unitList.clear();  //Clear existing units in unitList
		Player.level = 1;
		Player.currency = 80;
		for(int i = 0; i < 3; i++) {
			Player.unitList.add(i, null);
		}
	}
	
	/********************************************************
	 * Utility method for handlePlayerSelect().  Gets index 
	 * of team member button selected for location in 
	 * arrayList.
	 * @param teamMember
	 * @return index
	 *******************************************************/
	private int getIndex(String teamMember) {
		return (teamMember.equals("teamMember1")) ? 0 : (teamMember.equals("teamMember2")) ? 1 : 2;
	}
	
	/********************************************************
	 * Handles team member select button press.  Gets index 
	 * of button pressed and displays unit type options.
	 * @param event
	 *******************************************************/
	@FXML
	private void handlePlayerSelect(ActionEvent event) throws IOException {
		teamMember = ((Button) event.getSource()).getId();
		index = getIndex(teamMember);
		typeSelectContainer.setVisible(true);
		
	}
	
	/********************************************************
	 * Utility method for handleTypeSelect().  Switches 
	 * button styling.
	 * @param index, type
	 *******************************************************/
	private void setButtonIcon(String type, Button btn) {
		btn.getStyleClass().removeAll("unitSelect");
		btn.getStyleClass().add("gcUnitButton");
		if(type.equals("fire")) btn.getStyleClass().add("fire");
		if(type.equals("earth")) btn.getStyleClass().add("earth");
		if(type.equals("water")) btn.getStyleClass().add("water");
	}
	
	/********************************************************
	 * Utility method for handleTypeSelect().  Replaces
	 * button icon with its chosen element.
	 * @param index, type
	 *******************************************************/
	private void replaceButton(int index, String type) {
		if(index == 0) {
			setButtonIcon(type, teamMember1);
		}
		else if(index == 1) {
			setButtonIcon(type, teamMember2);
		}
		else {
			setButtonIcon(type, teamMember3);
		}
	}
	
	/********************************************************
	 * Utility method for handleTypeSelect().  Sets unit in
	 * arrayList and hides type select button container.
	 * @param type
	 *******************************************************/
	private void typeSelect(String type) {
		if(type.equals("earth")) {
			Player.unitList.set(index, new Unit(type, 1, 15, 0));
		}
		else if (type.equals("water")) {
			Player.unitList.set(index, new Unit(type, 2, 10, 10));
		}
		else {
			Player.unitList.set(index, new Unit(type, 3, 10, 15));
		}
		typeSelectContainer.setVisible(false);
		replaceButton(index, type);
	}
	
	/********************************************************
	 * Sets unit to element chosen by user and replaces 
	 * team member select button with its chosen element.
	 * @param event
	 *******************************************************/
	@FXML
	private void handleTypeSelect(ActionEvent event) throws IOException {
		
		String type = ((Button) event.getSource()).getId();
		
		switch(type) {
			case "fire":  typeSelect(type);  break;
			case "earth": typeSelect(type);  break;
			case "water": typeSelect(type);  break;	
		}
		
		battleButton.setDisable(!isFull());
	}
	
	/********************************************************
	 * Checks to see if all units are set.
	 * @return boolean
	 *******************************************************/
	private boolean isFull() {
		for(int i = 0; i < 3; i++) {
			if(Player.unitList.get(i) == null)  return false;
		}
		return true;
	}
	
	/** Send view change request */ 
	@FXML private void transitionView(ActionEvent event) {
		String btn = ((Button) event.getSource()).getId();
		
		if (btn.equals("battleButton")) {
			NavigationController.loadView(NavigationController.BATTLE);
		}
		else if (btn.equals("mainMenu")) {
			NavigationController.loadView(NavigationController.PREVIOUS);
		}
	}
}
