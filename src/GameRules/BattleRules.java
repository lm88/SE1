package GameRules;

import java.util.ArrayList;

import DataModels.BattleUnit;

public class BattleRules {
	
	public BattleRules() { }
	
	
	/** Determines the moves available to a selected unit
	 * @param x x-coordinate of unit to be moved
	 * @param y y-coordinate of unit to be moved
	 * @param playerUnits ArrayList of player-owned BattleUnits
	 * @param enemyUnits ArrayList of enemy-owned BattleUnits
	 * @return 2D boolean array where true elements are valid moves */
	public boolean[][] isMoveValid(int x, int y, ArrayList<BattleUnit> playerUnits,  ArrayList<BattleUnit> enemyUnits) {
		boolean[][] moveList = new boolean[8][8];
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				boolean inHorizontalRange = (row == x) && (col >= y - 2 && col <= y + 2); // two spaces horizontally
				boolean inVerticalRange = (col == y) && (row >= x - 2 && row <= x + 2); // two spaces vertically
				boolean inDiagonalRange = (col >= y - 1 && col <= y + 1) && (row >= x - 1 && row <= x + 1); // one space diagonally
				if (inVerticalRange || inHorizontalRange || inDiagonalRange) {
					moveList[row][col] = true;			// allow the move
					if (tileOccupied(row, col, playerUnits) || tileOccupied(row, col, enemyUnits)) {
						// TODO enable the player to select the tile the unit is already on, as a way to pass on movement phase
						moveList[row][col] = false;		// deny moving to occupied tiles (includes the one the active unit is on)
					}
				} else
					moveList[row][col] = false;			// destination tile is too far away
			}
		}
		
		return moveList;
	}
	
	/** Determines if any valid targets exist
	 * @param activeUnit unit performing the action
	 * @param targetList list of units targetable by action
	 * @param action the action type
	 * @return true if any valid target exists */
	public boolean validTargetsExist(BattleUnit activeUnit, ArrayList<BattleUnit> targetList, String action) {
		boolean[][] board = isActionValid(activeUnit, targetList, action);
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col])
					return true;
			}
		}
		return false;
	}
	
	/** Determines the targets available to a selected unit
	 * @param activeUnit the unit performing an action
	 * @param targetList the appropriate target for the action (enemies for attacks, friendlies for heals)
	 * @param action the action type for setting range
	 * @return 2D boolean array where true elements are valid targets */
	public boolean[][] isActionValid(BattleUnit activeUnit, ArrayList<BattleUnit> targetList, String action) {
		boolean[][] targetArray = new boolean[8][8];
		int x = activeUnit.getxPos();
		int y = activeUnit.getyPos();
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (tileOccupied(row, col, targetList)) {
					switch (action) {
						case "attack":
							boolean inRange = (col >= y - 1 && col <= y + 1) && (row >= x - 1 && row <= x + 1); // one space adjacent
							if (inRange)
								targetArray[row][col] = true;		// mark the target location
							else
								targetArray[row][col] = false;		// target is too far away
							break;
						case "ability":
						case "heal":
							boolean inHorizontalRange = (row == x) && (col >= y - 2 && col <= y + 2); // two spaces horizontally
							boolean inVerticalRange = (col == y) && (row >= x - 2 && row <= x + 2); // two spaces vertically
							boolean inDiagonalRange = (col >= y - 1 && col <= y + 1) && (row >= x - 1 && row <= x + 1); // one space diagonally
							if (inVerticalRange || inHorizontalRange || inDiagonalRange)
								targetArray[row][col] = true;		// mark the target location
							else
								targetArray[row][col] = false;		// target is too far away
							break;
						default:
					}
					
				} else
					targetArray[row][col] = false;			// target tile is empty
				
			}
		}
		
		return targetArray;
	}
	
	/** Check the condition of the opposing team
	 * @param unitList the opposing team's unit list */
	public boolean isEnemyDefeated(ArrayList<BattleUnit> unitList) {
		// if any unit on the other team still has health, the battle is not over
		for (BattleUnit unit : unitList) {
			if (unit.health != 0)
				return false;
		}
		return true;
	}
	
	/** Determine if tile is occupied by a member of selected team 
	 * @param row tile x-coordinate
	 * @param col tile y-coordinate
	 * @param unitList team to be checked
	 * @return true if team member exists */
	private boolean tileOccupied(int row, int col, ArrayList<BattleUnit> unitList) {
		for (BattleUnit unit : unitList) {
			if (unit.getxPos() == row && unit.getyPos() == col)
				return true;
		}
		return false;
	}
}
