package DataModels;

import java.util.ArrayList;

public class Player {

	public static int level;
	public static int currency;
	public static ArrayList<Unit> unitList = new ArrayList<Unit>();
	
	public Player() {
		level = 0; 
		currency = 0; 
	}
	
	// The GSon serializer needs this to properly deserialize
	public Player(String name, int level, int currency, ArrayList<Unit> unitList)
	{
	    Player.level = level;
	    Player.currency = currency;
	    Player.unitList.addAll(unitList);
	}
	
	public void setLevel(int newLevel) {
		level = newLevel;
	}
	public void setCurrency(int newCurrency) {
		currency = newCurrency; 
	}
	
	public int getLevel() {
		return level;
	}
	public int getCurrency() {
		return currency; 
	}
	
}
