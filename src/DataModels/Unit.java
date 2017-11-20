package DataModels;


public class Unit {
	public String type;
	public int health;
	public int resource;
	public int damage; 
	
	public Unit(String newType, int newDamage) {
		type = newType;
		damage = newDamage; 
	}
	
	public void setType(String newType) {
		type = newType; 
	}
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	public void setResource(int newResource) {
		resource = newResource;
	}
	public void setDamage(int newDamage) {
		damage = newDamage;
	}
	
	public String getType() {
		return type;
	}
	public int getHealth() {
		return health;
	}
	public int getResource() {
		return resource;
	}
	public int getDamage() {
		return damage;
	}
	
	public void modHealth(int modBy) {
		health = health - damage; 
	}
	
	public void modResource(int modBy) {
		
	}
}

