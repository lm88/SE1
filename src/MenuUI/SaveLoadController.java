/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuUI;

import DataModels.Player;
import Persistence.FileIO;
import UIFramework.NavigationController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author becky
 */
public class SaveLoadController 
{
    private int saveSlotNum = -1;
    private String _Previous;
    private Button _Selection;
    
    @FXML
    private Label statusMessage;
    
    /** Send view change request
     * @param event - the onAction event from fxml */ 
    @FXML 
    private void transitionView(ActionEvent event) {
	// id button caller
	String btn = ((Button) event.getSource()).getId();
            
	// send load request
	switch (btn) {
	    case "back":
		_Previous = NavigationController.PREVIOUS;
		NavigationController.PREVIOUS = NavigationController.SAVELOAD;
		NavigationController.loadView(_Previous);
		break;
	    case "load":
		// Load the game, trasition to main menu
		NavigationController.loadView(NavigationController.MAINMENU);
		break;
	    default:
		break;
	}
    }
    
    @FXML
    private void saveGame() 
    {
	if(_Selection != null)
	{
	    statusMessage.setText("Saving...");
	    try
	    {
		FileIO fileHandler = new FileIO();

		fileHandler.saveFile(Integer.parseInt(_Selection.getId()));
		
		// Display success message
		statusMessage.setText("Game Saved Successfully");
	    }
	    catch (IOException ex)
	    {
		// Display failure message
		statusMessage.setText("Game Could Not Be Saved");
	    }
	}
	else
	{
	    // Display error message
	    statusMessage.setText("Please Select A Save Slot");
	}
        
    }
      
    @FXML
    private void setSlotNum(ActionEvent event)
    {
	if(_Selection != null)
	{
	    //ObservableList<String> styles = _Selection.getStyleClass();
	    boolean removeAll = _Selection.getStyleClass().removeAll("menuButtonSelected");
	}
	
	_Selection = ((Button) event.getSource());
	_Selection.getStyleClass().add("menuButtonSelected");
    }
    
    @FXML
    private void loadGame(ActionEvent event)
    {
	// Load game
	if(_Selection != null)
	{
	    statusMessage.setText("Loading...");
	    try
	    {
		FileIO fileHandler = new FileIO();

		fileHandler.loadFile(Integer.parseInt(_Selection.getId()));
		
		if(validateGameLoad())
		{	
		    // Transition to main menu
		    transitionView(event);
		}
		else
		{
		    statusMessage.setText("Game Could Not Be Loaded");
		}
	    }
	    catch (IOException ex)
	    {
		// Display failure message
		statusMessage.setText("Game Could Not Be Loaded");
	    }
	}
	else
	{
	    // Display error message
	    statusMessage.setText("Please Select A Save");
	}
    }


    private boolean validateGameLoad()
    {
	 if(Player.level <= 0 || Player.currency < 0 || Player.unitList.isEmpty())
	 {
	     return false;
	 }
	 
	 return true;
    }
}