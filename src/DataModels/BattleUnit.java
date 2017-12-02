package DataModels;

public class BattleUnit extends Unit {
	
	private int xPos;
	private int yPos;
	
	public BattleUnit(String newType, int newDamage, int newHealth, int newResource, int xPos, int yPos) {
		super(newType, newDamage, newHealth, newResource);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/** @return the xPos */
	public int getxPos() {
		return xPos;
	}

	/** Set the xPos
	@param xPos horizontal grid coordinate*/
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/** @return the yPos */
	public int getyPos() {
		return yPos;
	}

	/** Set the yPos
	@param yPos vertical grid coordinate */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
