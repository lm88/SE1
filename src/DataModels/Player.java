package DataModels;

import java.util.ArrayList;

public class Player {

	public static String name;
	public static int level;
	public static int currency;
	public static ArrayList<Unit> unitList = new ArrayList<Unit>();
	public Player() {
		name = "";
		level = 0; 
		currency = 0; 
	}
	
	// The GSon serializer needs this to properly deserialize
	public Player(String name, int level, int currency, ArrayList<Unit> unitList)
	{
	    Player.name = name;
	    Player.level = level;
	    Player.currency = currency;
	    Player.unitList.addAll(unitList);
	}
	
	public void setName(String newName) {
		name = newName;
	}
	public void setLevel(int newLevel) {
		level = newLevel;
	}
	public void setCurrency(int newCurrency) {
		currency = newCurrency; 
	}
	
	public String getName() {
		return name;
	}
	public int getLevel() {
		return level;
	}
	public int getCurrency() {
		return currency; 
	}
	
}
