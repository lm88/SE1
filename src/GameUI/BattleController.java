package GameUI;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import DataModels.*;
import GameRules.BattleRules;
import UIFramework.NavigationController;

public class BattleController {
	
	private BattleRules rules;
	private int turn;
	private int unitRotation;
	private String gameState = "Player: TurnStart";
	private Button[][] tiles;
	
	private ArrayList<BattleUnit> playerUnitList = new ArrayList<>();
	private ArrayList<BattleUnit> enemyUnitList = new ArrayList<>();
	
	@FXML Button playerUnit1;
	@FXML Button playerUnit2;
	@FXML Button playerUnit3;
	@FXML Label playerUnit1health;
	@FXML Label playerUnit2health;
	@FXML Label playerUnit3health;
	@FXML Label playerUnit1resource;
	@FXML Label playerUnit2resource;
	@FXML Label playerUnit3resource;
	
	@FXML Button enemyUnit1;
	@FXML Button enemyUnit2;
	@FXML Button enemyUnit3;
	@FXML Label enemyUnit1health;
	@FXML Label enemyUnit2health;
	@FXML Label enemyUnit3health;
	@FXML Label enemyUnit1resource;
	@FXML Label enemyUnit2resource;
	@FXML Label enemyUnit3resource;
	
	@FXML BorderPane battle;
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
		unitRotation = 0;
		
		createUnitLists();
		setUnitIcons();
		updateStats();
		setResourceColors();
		createBoard();
		
		// draw units in initial positions
		for (BattleUnit unit : enemyUnitList) {
			updateBoard(unit, tiles[0][0], tiles[unit.getxPos()][unit.getyPos()]);
		}
		for (BattleUnit unit : playerUnitList) {
			updateBoard(unit, tiles[0][0], tiles[unit.getxPos()][unit.getyPos()]);
		}
		
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
	
	/** Update unit position on game board */
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
	private void showValidTargets(boolean[][] targetArray, String team) {
		// select highlight color
		String highlight;
		if (team.equals("enemy"))
			highlight = "redHighlight";		// enemy units highlight red
		else
			highlight = "greenHighlight";	// friendly units highlight green
		
		// apply highlight, ensuring non-targets do not highlight
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (targetArray[row][col])
					tiles[row][col].getStyleClass().add(highlight);
				else
					tiles[row][col].getStyleClass().remove(highlight);
			}
		}
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
				break;
			case "AI: TurnStart":
				aiControl();
				break;
			default:
		}
	}
	
	/** Determine tile click function based on current game state (movement or targeting)
	@param newX selected tile's x coordinate
	@param newY selected tile's y coordinate */
	private void processTilePress(int newX, int newY) {
		
		// gameState = current turn phase
		switch (gameState) {
			case "PlayerMovement":
				handleMovement(newX, newY);
				break;
			case "PlayerAction: attack":
				handleAction(newX, newY, "attack");
				break;
			case "PlayerAction: ability":
				handleAction(newX, newY, "ability");
				break;
			case "PlayerAction: heal":
				handleAction(newX, newY, "heal");
				break;
			default:
		}
	}
	
	/** Prepare target list for selected action
	@param action action to be performed */
	private void selectAction(String action) {
		// hide action buttons after selection
		battleActions.setVisible(false);
		instructions.setVisible(true);

		int unitIndex = unitRotation % 3;
		BattleUnit activeUnit = playerUnitList.get(unitIndex);
				
		ArrayList<BattleUnit> targetList = null;
		String targetTeam = "";
		switch (action) {
			case "attack":
			case "ability":
				targetList = enemyUnitList;
				targetTeam = "enemy";
				break;
			case "heal":
				targetList = playerUnitList;
				targetTeam = "player";
				break;
			case "pass":
			default: // treat unknown actions as a pass
				gameState = "AI: TurnStart";
				turnStart();
				return; // skip the rest of targeting process
		}
		
		// update instructions for player to select a target
		instructions.setText("Select a target");
		// TODO present action targets
		showValidTargets(rules.isTargetValid(activeUnit, targetList), targetTeam);
		gameState = "PlayerAction: " + action;
	}
	
	/** Verify and execute unit movement
	 * @param activeUnit unit to be moved
	 * @param newX x-coordinate of destination tile
	 * @param newY y-coordinate of destination tile
	 * @return true if move is successful */
	private void handleMovement(int newX, int newY) {

		// determine the active unit to move
		int unitIndex = unitRotation % 3;
		BattleUnit activeUnit = playerUnitList.get(unitIndex);
		
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
	}
	
	/** Verify and execute unit action
	 * @param activeUnit unit performing the action
	 * @param targetX x-coordinate of target tile
	 * @param targetY y-coordinate of target tile
	 * @param action action to be performed */
	private void handleAction(int targetX, int targetY, String action) {

		// determine active unit performing an action
		int unitIndex = unitRotation % 3;
		BattleUnit activeUnit = playerUnitList.get(unitIndex);
		
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
			switch (action) {
				case "attack":
					// apply damage changes
					targetUnit.modHealth(-activeUnit.getDamage());
					if (targetUnit.getHealth() < 0)
						targetUnit.setHealth(0);
					// apply resource changes
					if (activeUnit.getType().equals("earth"))
						activeUnit.setResource(activeUnit.getResource() + 1);
					break;
				case "ability":
					switch (activeUnit.getType()) {
						case "fire":
							targetUnit.modHealth(-activeUnit.getDamage() * 2);
							if (targetUnit.getHealth() < 0)
								targetUnit.setHealth(0);
							activeUnit.modResource(-2);
							break;
						case "earth":
							// apply damage equal to amount of rage
							targetUnit.modHealth(activeUnit.getResource());
							if (targetUnit.getHealth() < 0)
								targetUnit.setHealth(0);
							// remove all rage
							activeUnit.setResource(0);
							break;
						case "water":
							// apply heal
							targetUnit.modHealth(3);
							// prevent over-healing
							if (targetUnit.getHealth() > (targetUnit.getType().equals("earth") ? 15 : 10))
								targetUnit.setHealth(targetUnit.getType().equals("earth") ? 15 : 10);
							// apply resource cost
							activeUnit.modResource(-2);
							break;
						default:
					}
					break;
				case "heal":
					break;
				default:
			}
			
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
		
		// inform player to wait for AI
		instructions.setText("It is the enemy's turn..");
		

		battle.setMouseTransparent(true);
		PauseTransition wait = new PauseTransition(Duration.millis(1000));
		wait.onFinishedProperty().set(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateStats();		// TODO remove once AI can perform actions
					victoryCheck();		// TODO remove once AI can perform actions
					gameState = "Player: TurnStart";
					turn++;
					unitRotation++;
					battle.setMouseTransparent(false);
					turnStart();
				}
		});
		wait.play();
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
		if (currentPlayer.equals("AI"))
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
		playerUnitList.add(new BattleUnit(Player.unitList.get(1).getType(), Player.unitList.get(1).getDamage(),
				Player.unitList.get(1).getHealth(), Player.unitList.get(1).getResource(), "player", 6, 4));
		playerUnitList.add(new BattleUnit(Player.unitList.get(2).getType(), Player.unitList.get(2).getDamage(),
				Player.unitList.get(2).getHealth(), Player.unitList.get(2).getResource(), "player", 6, 6));
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
	
	/** Color the resource label according to unit type */
	private void setResourceColors() {
		enemyUnit1resource.getStyleClass().add((enemyUnitList.get(0).getType().equals("earth") ? "rage" : "mp"));
		enemyUnit2resource.getStyleClass().add((enemyUnitList.get(1).getType().equals("earth") ? "rage" : "mp"));
		enemyUnit3resource.getStyleClass().add((enemyUnitList.get(2).getType().equals("earth") ? "rage" : "mp"));
		playerUnit1resource.getStyleClass().add((playerUnitList.get(0).getType().equals("earth") ? "rage" : "mp"));
		playerUnit2resource.getStyleClass().add((playerUnitList.get(1).getType().equals("earth") ? "rage" : "mp"));
		playerUnit3resource.getStyleClass().add((playerUnitList.get(2).getType().equals("earth") ? "rage" : "mp"));
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
}