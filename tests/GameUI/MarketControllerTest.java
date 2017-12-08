/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameUI;

import DataModels.Player;
import DataModels.Unit;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author becky
 */
public class MarketControllerTest {
    
    public MarketControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	Player.currency = 30;
	Player.level = 1;
	Player.unitList = new ArrayList<>();
	Player.unitList.add(0, new Unit("water", 8, 8, 5));
	Player.unitList.add(1, new Unit("fire", 8, 5, 5));
	Player.unitList.add(2, new Unit("earth", 5, 8, 5));
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	Player.currency = 30;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of upgradeWeapon method, of class MarketController.
     */
    @Test
    public void testUpgradeWeapon() {
	System.out.println("upgradeWeapon");
	MarketController instance = new MarketController();
	int unitId = 0;
	Unit u = Player.unitList.get(unitId);
	int expUnitDam = (int)(u.damage + (5*Player.level)/1.5);
	int expPlayCurr = Player.currency - (u.damage*3);
	
	instance.applyUpgradeWeapon(unitId);
	u = Player.unitList.get(unitId);
	assertEquals("Weapon failed", expUnitDam, u.damage);
	assertEquals("Currency failed", expPlayCurr, Player.currency);
    }

    /**
     * Test of upgradeArmor method, of class MarketController.
     */
    @Test
    public void testUpgradeArmor() {
	System.out.println("upgradeArmor");
	int unitId = 0;
	MarketController instance = new MarketController();
	Unit u = Player.unitList.get(unitId);
	int expUnitHealth = (int)(u.health + (5*Player.level)/1.5);
	int expPlayCurr = (int)(Player.currency - (u.health*1.5));
	
	instance.ApplyUpgradeArmor(unitId);
	
	u = Player.unitList.get(unitId);
	assertEquals("Health failed", expUnitHealth, u.health);
	assertEquals("Currency failed", expPlayCurr, Player.currency);
    }
    
}
