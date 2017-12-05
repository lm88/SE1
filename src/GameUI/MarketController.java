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
     
    // View labels
    @FXML
    private Label playerCurrency;
    @FXML
    private Label insufficientFunds;
    @FXML
    private Label unit1Damage;
    @FXML
    private Label unit1Health;
    @FXML
    private Label weaponCost1;
    @FXML
    private Label armorCost1;
    @FXML
    private Label unit2Damage;
    @FXML
    private Label unit2Health;
    @FXML
    private Label weaponCost2;
    @FXML
    private Label armorCost2;
    @FXML
    private Label unit3Damage;
    @FXML
    private Label unit3Health;
    @FXML
    private Label weaponCost3;
    @FXML
    private Label armorCost3;
    
    // View buttons
    @FXML
    private Button unit1Icon;
    @FXML
    private Button unit2Icon;
    @FXML
    private Button unit3Icon;
    
    
    @FXML
    protected void initialize()
    {
	setUnitIcons();
	updateUpgradeCost();
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
	Unit u = Player.unitList.get(Integer.parseInt(id));
	if(Player.currency >= (int)(u.damage*3))
	{
	    Player.currency -= (int)(u.damage*3);
	    int pl = Player.level;

	    u.damage += (5*pl)/1.5;

	    updateUpgradeCost();
	    updateUnitStatsDisplay();
	 }
	else
	{
	    insufficientFunds.setText("Insufficient Funds");
	}
   }

   @FXML
   public void upgradeArmor(ActionEvent event)
   {
	String id = ((Button) event.getSource()).getId();
	Unit u = Player.unitList.get(Integer.parseInt(id));
	if(Player.currency >= (int)(u.health*1.5))
	{
	    Player.currency -= (int)(u.health*1.5);
	    
	    int pl = Player.level;
	    u.health += (5*pl)/1.5;
	    
	    updateUpgradeCost();
	    updateUnitStatsDisplay();
	}
	else
	{
	    insufficientFunds.setText("Insufficient Funds");
	}
   }
   
   private void setUnitIcons()
   {
       try
       {
	    unit1Icon.getStyleClass().add(determineIcon(0));
	    unit2Icon.getStyleClass().add(determineIcon(1));
	    unit3Icon.getStyleClass().add(determineIcon(2));
       }
       catch(Exception e)
       {
	   e.getMessage();
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

   private void updateUpgradeCost()
   {
       ArrayList<Unit> units = Player.unitList;
       playerCurrency.setText(Integer.toString(Player.currency));
       weaponCost1.setText(Integer.toString((int)(units.get(0).damage*3)));
       armorCost1.setText(Integer.toString((int)(units.get(0).health*1.5)));
       weaponCost2.setText(Integer.toString((int)(units.get(1).damage*3)));
       armorCost2.setText(Integer.toString((int)(units.get(1).health*1.5)));
       weaponCost3.setText(Integer.toString((int)(units.get(2).damage*3)));
       armorCost3.setText(Integer.toString((int)(units.get(2).health*1.5)));
   }
    
   private void updateUnitStatsDisplay()
   {
       
       ArrayList<Unit> units = Player.unitList;
       unit1Damage.setText("Weapon Strength: " + units.get(0).damage);
       unit1Health.setText("Armor Strength: " + units.get(0).health);
       unit2Damage.setText("Weapon Strength: " + units.get(1).damage);
       unit2Health.setText("Armor Strength: " + units.get(1).health);
       unit3Damage.setText("Weapon Strength: " + units.get(2).damage);
       unit3Health.setText("Armor Strength: " + units.get(2).health);
   }
}
