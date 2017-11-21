/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameUI;

import UIFramework.NavigationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author becky
 */
public class MarketController {

     private String _Previous;
     private int UpgradeCost;
    
    /** Send view change request
	 * @param event - the onAction event from fxml */ 
	@FXML 
	private void transitionView(ActionEvent event) {
		// id button caller
		String btn = ((Button) event.getSource()).getId();
		
		// send load request
		switch(btn)
		{
		    case "back":
			_Previous = NavigationController.PREVIOUS;
			NavigationController.PREVIOUS = NavigationController.MARKET;
			NavigationController.loadView(_Previous);
			break;
		    case "":
			break;
		    default:
			break;
		}
		
		// if this spot is reached: do nothing
	}   
    
	@FXML
	public void applyUpgrade()
	{
	    
	}
}
