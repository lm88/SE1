package DataModels;

import java.util.ArrayList;

public class Player {
	public String name;
	public int level;
	public int currency;
	public ArrayList<Unit> unitList = new ArrayList<Unit>(); 
	
	public Player() {
		name = "";
		level = 0; 
		currency = 0; 
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
