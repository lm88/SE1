/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import DataModels.Player;
import DataModels.Unit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FileIOTest {
    
    private static String testString;
	    
    public FileIOTest() {
	}
    
    @BeforeClass
    public static void setUpClass() {
	FileIOTest.testString = "{\"level\":1,\"currency\":4,\"unitList\":[{\"type\":\"water\",\"health\":8,\"resource\":5,\"damage\":8},{\"type\":\"fire\",\"health\":5,\"resource\":5,\"damage\":8},{\"type\":\"earth\",\"health\":8,\"resource\":5,\"damage\":5}]}";
    
	BufferedWriter writer;
	try {
	    writer = new BufferedWriter(new FileWriter("save1.txt"));
	    writer.write(testString);
	    writer.close();
	} catch (IOException ex) {
	    Logger.getLogger(FileIOTest.class.getName()).log(Level.SEVERE, null, ex);
	}
        
	Player.currency = 4;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveFile method, of class FileIO.
     */
    @Test
    public void testSaveFile() throws Exception {
	System.out.println("saveFile");
	int saveSlot = 0;
	FileIO instance = new FileIO();
	instance.saveFile(saveSlot);
	// TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of loadFile method, of class FileIO.
     */
    @Test
    public void testLoadFile() throws Exception {
	System.out.println("loadFile");
	int saveSlot = 0;
	FileIO instance = new FileIO();
	instance.loadFile(saveSlot);
	// TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of serialize method, of class FileIO.
     */
    @Test
    public void testSerialize() {
	System.out.println("serialize");
	FileIO instance = new FileIO();
	String result = instance.serialize();
	assertEquals(testString, result);
    }

    /**
     * Test of deserialize method, of class FileIO.
     */
    @Test
    public void testDeserialize() {
	System.out.println("deserialize");
	String text = "";
	FileIO instance = new FileIO();
	instance.deserialize(text);
	// TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of writeToFile method, of class FileIO.
     */
    @Test
    public void testWriteToFile() throws Exception {
	System.out.println("writeToFile");
	String text = "";
	String fileName = "save1.txt";
	FileIO instance = new FileIO();
	instance.writeToFile(text, fileName);
	// TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of readFromFile method, of class FileIO.
     */
    @Test
    public void testReadFromFile() throws Exception {
	System.out.println("readFromFile");
	String fileName = "save1.txt";
	FileIO instance = new FileIO();
	String expResult = testString;
	String result = instance.readFromFile(fileName);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
    }
    
}
