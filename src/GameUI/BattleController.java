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
		int tileX = tileID / 10;
		int tileY = tileID % 10;
		
		switch (gameState) {
			case "PlayerMovement":
				handleMovement(tileX, tileY);
				break;
			case "PlayerAction: attack":
				handleAction(tileX, tileY, "attack");
				break;
			case "PlayerAction: ability":
				handleAction(tileX, tileY, "ability");
				break;
			case "PlayerAction: heal":
				handleAction(tileX, tileY, "heal");
				break;
			default:
		}
	}
	
	/** Identify action button selected */
	@FXML private void selectAction(ActionEvent event) {
		Button action = (Button) event.getSource();
		String actionID = action.getId();
		
		// hide action buttons after selection
		battleActions.setVisible(false);
		instructions.setVisible(true);
		
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
	private void updateBoard(BattleUnit unit, Button oldTile, Button newTile) {
		oldTile.getStyleClass().remove(unit.getTeam());
		oldTile.getStyleClass().remove(unit.getType());
		newTile.getStyleClass().add(unit.getType());
		newTile.getStyleClass().add(unit.getTeam());
	}
	
	/** Enable or disable action buttons as appropriate */
	private void showActionOptions() {
		// start with all buttons disabled
		attack.setDisable(true);
		ability.setDisable(true);
		heal.setDisable(true);
		
		// if there are targets in range to attack: enable attack button
		if (rules.validTargetsExist(activeUnit(), opponentUnitList(), "attack"))
			attack.setDisable(false);
		
		// if the unit is not a healer..
		if (!(activeUnit().getType().equals("water"))) {
			// if the unit has enough resource for its ability..
			if ((activeUnit().getType().equals("earth") && activeUnit().getResource() > 0) ||
				(activeUnit().getType().equals("fire") && activeUnit().getResource() >= 2) ) {
				// as long as a target is in range for an ability: enable ability button
				if (rules.validTargetsExist(activeUnit(), opponentUnitList(), "ability")) 
					ability.setDisable(false);
			}
		// if the unit is a healer..
		} else {
			// if the unit has enough resource to heal..
			if (activeUnit().getType().equals("water") && activeUnit().getResource() >= 1) {
				// if there are targets in range to heal: enable heal button
				if (rules.validTargetsExist(activeUnit(), friendlyUnitList(), "heal"))
					heal.setDisable(false);
			}
			// if the unit has enough resource for its ability: enable ability button
			if (activeUnit().getType().equals("water") && activeUnit().getResource() >= 2)
				ability.setDisable(false);
		}
	}
	
	/** Visually highlight tiles valid for moves
	 * @param moveList 2D boolean array where true elements are valid moves */
	private void showValidMoves(boolean[][] moveList) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (moveList[row][col])
					tiles[row][col].getStyleClass().add("blueHighlight");
			}
		}
	}

	/** Visually highlight tiles occupied by valid targets
	 * @param targetArray 2D boolean array where true elements are valid target locations
	 * @param team unit team targetable by action*/
	private void showValidTargets(boolean[][] targetArray, String team) {
		// select highlight color
		String highlight;
		if (team.equals(opponent()))
			highlight = "redHighlight";		// enemy units highlight red
		else
			highlight = "greenHighlight";	// friendly units highlight green
		
		// apply highlight
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (targetArray[row][col])
					tiles[row][col].getStyleClass().add(highlight);
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
		
				// update instructions for player to move
				instructions.setText("Select a tile to move");
				// present move options
				showValidMoves(rules.isMoveValid(activeUnit().getxPos(), activeUnit().getyPos(), friendlyUnitList(), opponentUnitList()));
				gameState = "PlayerMovement";  // game waits for player input after this
				break;
			case "AI: TurnStart":
				turn++;
				aiControl();
				break;
			default:
		}
	}
	
	/** Verify and execute unit movement
	 * @param newX x-coordinate of destination tile
	 * @param newY y-coordinate of destination tile
	 * @return true if move is successful */
	private void handleMovement(int newX, int newY) {
		
		// ignore moves outside highlights
		boolean validMove = tiles[newX][newY].getStyleClass().contains("blueHighlight");
		if (!validMove)
			return;
		
		// execute move
		Button oldTile = tiles[activeUnit().getxPos()][activeUnit().getyPos()];
		Button newTile = tiles[newX][newY];
		updateBoard(activeUnit(), oldTile, newTile);
		activeUnit().setxPos(newX);
		activeUnit().setyPos(newY);
		
		// clean up after successful move and prepare next phase
		removeHighlights();
		instructions.setVisible(false);
		battleActions.setVisible(true);
		gameState = "Player: SelectAction";
		showActionOptions();
	}
	
	/** Prepare target list for selected action
	@param action action to be performed */
	private void selectAction(String action) {
		
		if (action.equals("pass")) {
			gameState = "AI: TurnStart";
			turnStart();
			return; // skip the rest of targeting process
		}
		
		// update instructions for player to select a target
		instructions.setText("Select a target");
		
		if(action.equals("attack") || !(activeUnit().getType().equals("water")))
			showValidTargets(rules.isActionValid(activeUnit(), opponentUnitList(), action), opponent());
		else
			showValidTargets(rules.isActionValid(activeUnit(), friendlyUnitList(), action), activeUnit().getTeam());
		
		gameState = "PlayerAction: " + action;
	}
	
	/** Verify and execute unit action
	 * @param activeUnit unit performing the action
	 * @param targetX x-coordinate of target tile
	 * @param targetY y-coordinate of target tile
	 * @param action action to be performed */
	private void handleAction(int targetX, int targetY, String action) {
		
		ArrayList<BattleUnit> targetList;
		if (tiles[targetX][targetY].getStyleClass().contains("redHighlight"))
			targetList = opponentUnitList();
		else if (tiles[targetX][targetY].getStyleClass().contains("blueHighlight"))
			targetList = friendlyUnitList();
		else // only highlighted tiles contain valid targets, ignore the rest
			return;
		
		BattleUnit targetUnit = null;
		// get the unit on selected tile
		for (BattleUnit unit : targetList) {
			if (unit.getxPos() == targetX && unit.getyPos() == targetY) {
				targetUnit = unit;
				break;
			}
		}
		
		// validate action and execute
		switch (action) {
			case "attack":
				// apply damage changes
				targetUnit.modHealth(-activeUnit().getDamage());
				if (targetUnit.getHealth() < 0)
					targetUnit.setHealth(0);
				// apply resource changes
				if (activeUnit().getType().equals("earth"))
					activeUnit().setResource(activeUnit().getResource() + 1);
				break;
			case "ability":
				switch (activeUnit().getType()) {
					case "fire":
						targetUnit.modHealth(-activeUnit().getDamage() * 2);
						if (targetUnit.getHealth() < 0)
							targetUnit.setHealth(0);
						activeUnit().modResource(-2);
						break;
					case "earth":
						// apply damage equal to amount of rage
						targetUnit.modHealth(-activeUnit().getResource());
						if (targetUnit.getHealth() < 0)
							targetUnit.setHealth(0);
						// remove all rage
						activeUnit().setResource(0);
						break;
					case "water":
						// apply heal to all friendly team units
						for (BattleUnit unit : friendlyUnitList()) {
							unit.modHealth(2);
							// prevent over-healing
							if (unit.getHealth() > (unit.getType().equals("earth") ? 15 : 10))
								unit.setHealth(unit.getType().equals("earth") ? 15 : 10);
						}
						// apply resource cost
						activeUnit().modResource(-2);
						break;
					default:
				}
				break;
			case "heal":
				// apply heal to target
				targetUnit.modHealth(3);
				// prevent over-healing
				if (targetUnit.getHealth() > (targetUnit.getType().equals("earth") ? 15 : 10))
					targetUnit.setHealth(targetUnit.getType().equals("earth") ? 15 : 10);
				// apply resource cost
				activeUnit().modResource(-1);
				break;
			default:
		}
		
		// clean up after successful action and prepare next phase
		updateStats();
		removeHighlights();
		victoryCheck();
		gameState = "AI: TurnStart";
		turn++; // advance to the turn counter (odd = AI, even  = Player)
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
					// TODO remove some of these once AI can perform actions
					updateStats();
					victoryCheck();
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
		boolean victory = rules.isEnemyDefeated(opponentUnitList());
		
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

	/*===             === */
	/*=== Information === */
	/*===             === */
	
	private BattleUnit activeUnit() {
		BattleUnit activeUnit = null;
		int unitIndex = unitRotation % 3;
		if (turn % 2 == 0)
			activeUnit = playerUnitList.get(unitIndex);
		else
			activeUnit = enemyUnitList.get(unitIndex);
		
		return activeUnit;
	}
	
	private ArrayList<BattleUnit> opponentUnitList() {
		if (activeUnit().getTeam().equals("enemy"))
			return playerUnitList;
		else
			return enemyUnitList;
	}
	
	private ArrayList<BattleUnit> friendlyUnitList() {
		if (activeUnit().getTeam().equals("player"))
			return playerUnitList;
		else
			return enemyUnitList;
	}
	
	private String opponent() {
		return "";
	}
}