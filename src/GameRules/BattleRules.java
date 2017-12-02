package GameRules;

import java.util.ArrayList;

import DataModels.BattleUnit;

public class BattleRules {
	
	public BattleRules() { }
	
	
	/** Determines the moves available to a selected unit
	 * @param x x-coordinate of unit to be moved
	 * @param y y-coordinate of unit to be moved
	 * @return 2D boolean array where true elements are valid moves */
	public boolean[][] isMoveValid(int x, int y) {
		boolean[][] moveList = new boolean[8][8];
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row <= x + 2 && row >= x - 2) && (col <= y + 2 && col >= y - 2)) {
					if (row == x && col == y)
						moveList[row][col] = false;
					else
						moveList[row][col] = true;
				} else {
					moveList[row][col] = false;
				}
			}
		}
		
		return moveList;
	}

	/** Executes a combat action if it is allowable
	 * @param activeUnit the unit performing the action
	 * @param targetUnit the unit being targeted
	 * @param action the action being performed: "attack","ability","heal"
	 * @return 2D boolean array where true elements are valid moves */
	public boolean isActionValid(BattleUnit activeUnit, BattleUnit targetUnit, String action) {
		switch (action) {
			case "attack":
				// TODO check attack is successful: if target is already dead, fail
				// TODO add attack results
				break;
			case "ability":
				// TODO check ability is successful: usually on self
				// TODO add ability results
				break;
			case "heal":
				// TODO check heal is successful: if target is already dead, fail
				// TODO add heal results
				break;
			default:
				return false;
		}
		return false;
	}
	
	/** Check the condition of the opposing team
	 * @param units the opposing team's unit list */
	public boolean isEnemyDefeated(ArrayList<BattleUnit> units) {
		// if any unit on the other team still has health, the battle is not over
		for (BattleUnit unit : units) {
			if (unit.health != 0)
				return false;
		}
		return true;
	}
}
