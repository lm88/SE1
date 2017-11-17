/**
 *
 * @author Rebecca Elliff
 */
package Persistence;

import DataModels.testObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileIO {

    private static String[] SAVESLOT;

    public FileIO()
    {
	SAVESLOT = new String[3];
	SAVESLOT[0] = "./save1.txt";
	SAVESLOT[1] = "./save2.txt";
	SAVESLOT[2] = "./save3.txt";
    }

    public void saveFile(int saveSlot) throws IOException
    {
	// TODO Change this to use player object

	testObject obj = new testObject("test", 100);
	String serializedObj = serialize(obj);
	writeToFile(serializedObj, SAVESLOT[saveSlot]);
    }
    
    public void loadFile(int saveSlot) throws IOException
    {
	String text = readFromFile(SAVESLOT[saveSlot]);
	deserialize(text);
    }

    // TODO Change this to use player object
    public String serialize(testObject obj)
    {
	GsonBuilder gsonBuilder  = new GsonBuilder();
	// Allowing the serialization of static fields
	gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
	
	Gson serializer = gsonBuilder.create();
	obj = new testObject("test", 100);
	String text = serializer.toJson(obj, obj.getClass());
	
	return text;
    }

    // TODO Change this to use player object
    public void deserialize(String text)
    {
	GsonBuilder gsonBuilder  = new GsonBuilder();
	// Allowing the serialization of static fields
	gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);

	Gson serializer = gsonBuilder.create();

	testObject obj = serializer.fromJson(text, testObject.class);

	System.out.println(obj.toString());
    }
    
    public void writeToFile(String text, String fileName) throws IOException
    {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(text);
	writer.close();
    }
    
    public String readFromFile(String fileName) throws IOException
    {
	try
	{
	    String text = "";
	    String line;
	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		while((line = reader.readLine()) != null) {
		    text += line;
		}
	    }
	    return text;
	}
	catch (IOException ex)
	{
	    throw ex;
	}
    }
}

