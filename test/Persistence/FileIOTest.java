/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

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
    
    private final String testJson = "{"
	    + "\"name\":\"\","
	    + "\"level\":1,"
	    + "\"currency\":10,"
	    + "\"unitList\":[{"
		+ "\"type\":\"water\","
		+ "\"health\":5,"
		+ "\"resource\":5,"
		+ "\"damage\":5},"
	    + "{\"type\":\"fire\","
		+ "\"health\":5,"
		+ "\"resource\":5,"
		+ "\"damage\":5},"
	    + "{\"type\":\"earth\","
		+ "\"health\":5,"
		+ "\"resource\":5,"
		+ "\"damage\":5}]}";
    
    private final Player testPlayer = new Player();
    
    public FileIOTest() {
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
     * Test of saveFile method, of class FileIO.
     * @throws java.lang.Exception
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
     * @throws java.lang.Exception
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
	String expResult = "";
	String result = instance.serialize();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
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
	fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class FileIO.
     */
    @Test
    public void testWriteToFile() throws Exception {
	System.out.println("writeToFile");
	String text = "";
	String fileName = "";
	FileIO instance = new FileIO();
	instance.writeToFile(text, fileName);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of readFromFile method, of class FileIO.
     */
    @Test
    public void testReadFromFile() throws Exception {
	System.out.println("readFromFile");
	String fileName = "";
	FileIO instance = new FileIO();
	String expResult = "";
	String result = instance.readFromFile(fileName);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
    
}
