package GameUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import DataModels.*;
import GameRules.BattleRules;
import UIFramework.NavigationController;

public class BattleController {
	
	
	/* TODO remove state before turn-in */@FXML Label stateText;
	
	
	private BattleRules rules;
	private Button[][] tiles;
	private int turn;
	private int unitRotation;
	private String gameState = "Player: TurnStart";
	
	private ArrayList<BattleUnit> playerUnitList = new ArrayList<>();
	@FXML Button playerUnit1;
	@FXML Button playerUnit2;
	@FXML Button playerUnit3;
	@FXML Label playerUnit1health;
	@FXML Label playerUnit2health;
	@FXML Label playerUnit3health;
	@FXML Label playerUnit1resource;
	@FXML Label playerUnit2resource;
	@FXML Label playerUnit3resource;
	
	private ArrayList<BattleUnit> enemyUnitList = new ArrayList<>();
	@FXML Button enemyUnit1;
	@FXML Button enemyUnit2;
	@FXML Button enemyUnit3;
	@FXML Label enemyUnit1health;
	@FXML Label enemyUnit2health;
	@FXML Label enemyUnit3health;
	@FXML Label enemyUnit1resource;
	@FXML Label enemyUnit2resource;
	@FXML Label enemyUnit3resource;
	
	@FXML TilePane tilePane;
	@FXML Label overlord;
	@FXML Label instructions;
	@FXML HBox battleActions;
	@FXML Button attack;
	@FXML Button ability;
	@FXML Button heal;
	@FXML Button pass;
	
	
	/** <h1>Executed on first load of the view</h1>
	 * The method creates the game board 2D-button-array and enemy unit list,
	 * draws unit information on view, and prepares game board for play */
	@FXML private void initialize() {
		rules = new BattleRules();
		turn = 0;
		
		createUnitLists();
		setUnitIcons();
		updateStats();
		createBoard();
		
		updateBoard(enemyUnitList.get(0), tiles[0][0], tiles[1][1]);
		updateBoard(enemyUnitList.get(1), tiles[0][0], tiles[1][3]);
		updateBoard(enemyUnitList.get(2), tiles[0][0], tiles[1][5]);
		updateBoard(playerUnitList.get(0), tiles[0][0], tiles[6][2]);
		updateBoard(playerUnitList.get(1), tiles[0][0], tiles[6][4]);
		updateBoard(playerUnitList.get(2), tiles[0][0], tiles[6][6]);
		
		battleActions.setVisible(false);
		overlord.setText("Overlord Level " + Player.level);
		
		turnStart();
	}

	/*===           === */
	/*=== UI events === */
	/*===           === */
	
	/** Send tile coordinates */
	@FXML private void tilePress(ActionEvent event) {
		Button tile = (Button) event.getSource();
		int tileID = Integer.valueOf(tile.getId());
		int x = tileID / 10;
		int y = tileID % 10;
		processTilePress(x, y);
	}
	
	/** Identify action button selected */
	@FXML private void selectAction(ActionEvent event) {
		Button action = (Button) event.getSource();
		String actionID = action.getId();
		selectAction(actionID);
	}

	/** Send view change request */
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.MAINMENU);
	}

	/*===           === */
	/*=== UI update === */
	/*===           === */
	
	/** Update health and resource labels next to unit icons */
	private void updateStats() {
		playerUnit1health.setText("" + playerUnitList.get(0).health);
		playerUnit2health.setText("" + playerUnitList.get(1).health);
		playerUnit3health.setText("" + playerUnitList.get(2).health);
		playerUnit1resource.setText("" + playerUnitList.get(0).resource);
		playerUnit2resource.setText("" + playerUnitList.get(1).resource);
		playerUnit3resource.setText("" + playerUnitList.get(2).resource);
		
		enemyUnit1health.setText("" + enemyUnitList.get(0).health);
		enemyUnit2health.setText("" + enemyUnitList.get(1).health);
		enemyUnit3health.setText("" + enemyUnitList.get(2).health);
		enemyUnit1resource.setText("" + enemyUnitList.get(0).resource);
		enemyUnit2resource.setText("" + enemyUnitList.get(1).resource);
		enemyUnit3resource.setText("" + enemyUnitList.get(2).resource);
	}
	
	private void updateBoard(BattleUnit activeUnit, Button oldTile, Button newTile) {
		oldTile.getStyleClass().remove(activeUnit.getTeam());
		oldTile.getStyleClass().remove(activeUnit.getType());
		newTile.getStyleClass().add(activeUnit.getType());
		newTile.getStyleClass().add(activeUnit.getTeam());
	}
	
	/** Visually highlight tiles valid for moves
	 * @param moveList 2D boolean array where true elements are valid moves */
	private void showValidMoves(boolean[][] moveList) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (moveList[row][col])
					tiles[row][col].getStyleClass().add("blueHighlight");
				else
					tiles[row][col].getStyleClass().remove("blueHighlight");
			}
		}
	}

	/** Visually highlight tiles occupied by valid targets */
	private void showValidTargets(boolean[][] targetArray) {
		// TODO highlight targetable units
		// enemy units highlight red
		// friendly units highlight green (if action = heal)
	}

	/** Remove all visual highlights from all tiles */
	private void removeHighlights() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				tiles[row][col].getStyleClass().remove("blueHighlight");
				tiles[row][col].getStyleClass().remove("redHighlight");
				tiles[row][col].getStyleClass().remove("greenHighlight");
			}
		}
	}
	
	/*===            === */
	/*=== Game  Flow === */
	/*===            === */
	
	/** Turns for both players begin here */
	private void turnStart() {
		switch (gameState) {
			case "Player: TurnStart":
				int unitIndex = unitRotation % 3; // cycles through indexes 0, 1, 2, 0, 1, 2, etc each new turn
				BattleUnit activeUnit = playerUnitList.get(unitIndex);
		
				// update instructions for player to move
				instructions.setText("Select a tile to move");
				// present move options
				showValidMoves(rules.isMoveValid(activeUnit.getxPos(), activeUnit.getyPos(), playerUnitList, enemyUnitList));
				gameState = "PlayerMovement";  // game waits for player input after this
				/* TODO remove state before turn-in */stateText.setText("Waiting for player to select a movement tile for unit " + unitIndex);
				break;
			case "AI: TurnStart":
				/* TODO remove state before turn-in */stateText.setText("AI turn starts");
				aiControl();
				updateStats();		// TODO remove once AI can perform actions
				victoryCheck();		// TODO remove once AI can perform actions
				gameState = "Player: TurnStart";
				turn++;
				unitRotation++;
				turnStart();
				break;
			default:
		}
	}
	
	
	/** Determine process to be performed based on current game state
	@param newX selected tile's x coordinate
	@param newY selected tile's y coordinate */
	private void processTilePress(int newX, int newY) {
		
		int unitIndex = unitRotation % 3;
		BattleUnit activeUnit = playerUnitList.get(unitIndex);
		
		// gameState = current turn phase
		switch (gameState) {
			case "PlayerMovement":
				handleMovement(activeUnit, newX, newY);
				break;
			case "PlayerAction: attack":
				handleAction(activeUnit, newX, newY, "attack");
				break;
			case "PlayerAction: ability":
				handleAction(activeUnit, newX, newY, "ability");
				break;
			case "PlayerAction: heal":
				handleAction(activeUnit, newX, newY, "heal");
				break;
			default:
		}
	}
	
	/** Prepare target list for selected action
	@param action action to be performed */
	private void selectAction(String action) {
		// skip targeting if player is passing
		if (action.equals("pass")) {
			gameState = "AI: TurnStart";
			turnStart();
			return;
		}
		
		// update instructions for player to take an action
		battleActions.setVisible(false);
		instructions.setVisible(true);
		instructions.setText("Select a target");
		// TODO present action targets
		/* uncomment when method exists */ // showValidTargets(rules.isTargetValid());
		gameState = "PlayerAction: " + action;
		/* TODO remove state before turn-in */ stateText.setText("Waiting for player to select a target");
	}
	
	/** Verify and execute unit movement
	 * @param activeUnit unit to be moved
	 * @param newX x-coordinate of destination tile
	 * @param newY y-coordinate of destination tile
	 * @return true if move is successful */
	private void handleMovement(BattleUnit activeUnit, int newX, int newY) {
		// ignore moves outside highlights
		boolean isValid = tiles[newX][newY].getStyleClass().contains("blueHighlight");
		if (!isValid)
			return;
		
		// execute move
		Button oldTile = tiles[activeUnit.getxPos()][activeUnit.getyPos()];
		Button newTile = tiles[newX][newY];
		updateBoard(activeUnit, oldTile, newTile);
		activeUnit.setxPos(newX);
		activeUnit.setyPos(newY);
		
		// clean up after successful move and prepare next phase
		instructions.setVisible(false);
		battleActions.setVisible(true);
		removeHighlights();
		gameState = "Player: SelectAction";
		/* TODO remove state before turn-in */ stateText.setText("Waiting for player to select an action");
	}
	
	/** Verify and execute unit action
	 * @param activeUnit unit performing the action
	 * @param targetX x-coordinate of target tile
	 * @param targetY y-coordinate of target tile
	 * @param action action to be performed */
	private void handleAction(BattleUnit activeUnit, int targetX, int targetY, String action) {
		BattleUnit targetUnit = null;
		// verify a target exists on selected tile
		if (activeUnit.getTeam().equals("enemy")) {
			for (BattleUnit unit : playerUnitList) {
				if (unit.getxPos() == targetX && unit.getyPos() == targetY) {
					targetUnit = unit;
					break;
				}
			}
		} else {
			for (BattleUnit unit : enemyUnitList) {
				if (unit.getxPos() == targetX && unit.getyPos() == targetY) {
					targetUnit = unit;
					break;
				}
			}
		}
		
		// failed action because there is no target on selected tile, does not advance gameState, so this step will be repeated
		if (targetUnit == null)
			return;
		
		// validate action and execute
		if (rules.isActionValid(activeUnit, targetUnit, action)) {
			// TODO execute action
			/* TODO remove state before turn-in */ stateText.setText("Action performed successfully.");
			// clean up after successful action and prepare next phase
			updateStats();
			victoryCheck();
			gameState = "AI: TurnStart";
			turn++; // advance to the turn counter (odd = AI, even  = Player)
		}
	}
	
	/** AI turn control */
	private void aiControl() {
		// TODO make AI work (g'luck)
	}
	
	/** Check the opponent team's condition */
	private void victoryCheck() {
		// determine who's turn it is
		String currentPlayer = "";
		if (turn % 2 == 0)
			currentPlayer = "Player";
		else
			currentPlayer = "AI";
		
		boolean victory;
		if (currentPlayer == "AI")
			victory = rules.isEnemyDefeated(playerUnitList);
		else
			victory = rules.isEnemyDefeated(enemyUnitList);
		
		if (victory)
			NavigationController.loadView(NavigationController.MAINMENU);
	}

	/*===            === */
	/*=== Game setup === */
	/*===            === */
	
	/** Generate a fresh list of enemy units for a battle */
	private void createUnitLists() {
		enemyUnitList.clear();
		enemyUnitList.add(new BattleUnit("earth", 1, 15, 0, "enemy", 1, 1));
		enemyUnitList.add(new BattleUnit("fire", 3, 10, 15, "enemy", 1, 3));
		enemyUnitList.add(new BattleUnit("water", 2, 10, 10, "enemy", 1, 5));
		
		// TODO find a better way, this seems crude..
		playerUnitList.clear();
		playerUnitList.add(new BattleUnit(Player.unitList.get(0).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), "player", 6, 2));
		playerUnitList.add(new BattleUnit(Player.unitList.get(1).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), "player", 6, 4));
		playerUnitList.add(new BattleUnit(Player.unitList.get(2).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), "player", 6, 6));
	}
	
	/** Draw the appropriate icon for each unit on the side panels */
	private void setUnitIcons() {
		playerUnit1.getStyleClass().add(playerUnitList.get(0).type);
		playerUnit2.getStyleClass().add(playerUnitList.get(1).type);
		playerUnit3.getStyleClass().add(playerUnitList.get(2).type);
		enemyUnit1.getStyleClass().add(enemyUnitList.get(0).type);
		enemyUnit2.getStyleClass().add(enemyUnitList.get(1).type);
		enemyUnit3.getStyleClass().add(enemyUnitList.get(2).type);
	}
	
	/** Initialize the 2D button array representing the game board */
	private void createBoard() {
		tiles = new Button[8][8];
		 
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Button tile = new Button();
				tile.setId(row + "" + col);
				tile.getStyleClass().add("tile");
				if ((row + col) % 2 == 0)
					tile.getStyleClass().add("whtTile");
				else
					tile.getStyleClass().add("blkTile");
				tile.setOnAction(this::tilePress);
				
				tilePane.getChildren().add(tile);
				tiles[row][col] = tile;
			}
		}
	}
	
	/** Place the Player's unit token on a tile
	 * @param index index of the unit in Player's unit list
	 * @param row location of the tile (first index in 2D array)
	 * @param col location of the tile (second index in 2D array) */
	private void initPlayerUnit(int index, int row, int col) {
		tiles[row][col].getStyleClass().add("playerTile");
		tiles[row][col].getStyleClass().add(playerUnitList.get(index).getType());
	}
	
	/** Place the Enemy's unit token on a tile
	 * @param index index of the unit in Enemy's unit list
	 * @param row vertical location of the tile (first index in 2D array)
	 * @param col horizontal location of the tile (second index in 2D array) */
	private void initEnemyUnit(int index, int row, int col) {
		tiles[row][col].getStyleClass().add("enemyTile");
		tiles[row][col].getStyleClass().add(enemyUnitList.get(index).getType());
	}
}