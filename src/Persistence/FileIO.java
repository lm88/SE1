/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import "c:\users\becky\desktop\school\softeng1\reallyawesomerpg\gson-master\gson";
/**
 *
 * @author becky
 */
public class FileIO {
    
    private static String[] SAVESLOT;
    
    public FileIO()
    {
        SAVESLOT = new String[3];
        SAVESLOT[0] = "./save1.txt";
        SAVESLOT[1] = "./save2.txt";
        SAVESLOT[3] = "./save3.txt";
    }
    
    public void saveFile(int saveSlot)
    {
        
    }
    
    public String serialize()
    {
        Gson ser = new Gson();
    }
}

