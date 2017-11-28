/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameUI;

import DataModels.Player;
import DataModels.Unit;
import UIFramework.NavigationController;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author becky
 */
public class MarketController {

     private String _Previous;
     private int UpgradeCost;
     
    @FXML
    private Label unit1Damage;
    @FXML
    private Label unit1Health;
    @FXML
    private Label unit2Damage;
    @FXML
    private Label unit2Health;
    @FXML
    private Label unit3Damage;
    @FXML
    private Label unit3Health;
    
    @FXML
    protected void initialize()
    {
	updateUnitStatsDisplay();
    }
    
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
	   default:
	       break;
       }

       // if this spot is reached: do nothing
   }   

   @FXML
   public void upgradeWeapon(ActionEvent event)
   {
       String id = ((Button) event.getSource()).getId();

       int pl = Player.level;

       Unit u = Player.unitList.get(Integer.parseInt(id));

       u.damage += (5*pl)/1.5;
       
       updateUnitStatsDisplay();
   }

   @FXML
   public void upgradeArmor(ActionEvent event)
   {
       String id = ((Button) event.getSource()).getId();

       int pl = Player.level;

       Unit u = Player.unitList.get(Integer.parseInt(id));

       u.health += (5*pl)/1.5;

       updateUnitStatsDisplay();
   }

   @FXML
   private void updateUnitStatsDisplay()
   {
       
       ArrayList<Unit> units = Player.unitList;
       unit1Damage.setText("Weapon Strength: " + Integer.toString(units.get(0).damage));
       unit1Health.setText("Armor Strength: " + Integer.toString(units.get(0).health));
       unit2Damage.setText("Weapon Strength: " + Integer.toString(units.get(1).damage));
       unit2Health.setText("Armor Strength: " + Integer.toString(units.get(1).health));
       unit3Damage.setText("Weapon Strength: " + Integer.toString(units.get(2).damage));
       unit3Health.setText("Armor Strength: " + Integer.toString(units.get(2).health));
   }
}
