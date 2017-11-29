package GameUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

import DataModels.Player;
import DataModels.Unit;
import UIFramework.NavigationController;

public class BattleController {
	
	@FXML Button playerUnit1;
	@FXML Button playerUnit2;
	@FXML Button playerUnit3;
	
	@FXML private void initialize() {
		setUnitIcons();
	}
	
	/** Send tile coordinates */
	@FXML private void tilePress(ActionEvent event) {
		Button tile = (Button) event.getSource();
		int tileID = Integer.valueOf(tile.getId());
		int x = tileID / 8;
		int y = tileID % 8;
		tile.setText(x + "," + y);
	}

	/** Send view change request */
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.MAINMENU);
	}
	
	
   private void setUnitIcons()
   {
       try
       {
	    playerUnit1.getStyleClass().add(determineIcon(0));
	    playerUnit2.getStyleClass().add(determineIcon(1));
	    playerUnit3.getStyleClass().add(determineIcon(2));
       }
       catch(Exception e)
       {
	   String m = e.getMessage();
       }
   }
   
   private String determineIcon(int index)
   {
       ArrayList<Unit> u = Player.unitList;
       switch(u.get(index).type)
       {
	   case "water":
	       return "waterUnit";
	   case "fire":
	       return "fireUnit";
	   case "earth":
	       return "earthUnit";
	   default:
	       return"";
       }
   }
}