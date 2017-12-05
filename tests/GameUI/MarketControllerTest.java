/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameUI;

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
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class MarketController.
     */
    @Test
    public void testInitialize() {
	System.out.println("initialize");
	MarketController instance = new MarketController();
	instance.initialize();
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeWeapon method, of class MarketController.
     */
    @Test
    public void testUpgradeWeapon() {
	System.out.println("upgradeWeapon");
	ActionEvent event = null;
	MarketController instance = new MarketController();
	instance.upgradeWeapon(event);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeArmor method, of class MarketController.
     */
    @Test
    public void testUpgradeArmor() {
	System.out.println("upgradeArmor");
	ActionEvent event = null;
	MarketController instance = new MarketController();
	instance.upgradeArmor(event);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
    
}
