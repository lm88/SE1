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
	
	private BattleRules rules;
	private ArrayList<BattleUnit> enemyUnitList = new ArrayList<>();
	private ArrayList<BattleUnit> playerUnitList = new ArrayList<>();
	private Button[][] tiles;
	private int turn;
	private int unitRotation;
	private String gameState = "Player:TurnStart";
	
	/* tiles are now created by the controller, but leaving this here for now in case it is needed again
	@FXML Button tile00, tile01, tile02, tile03, tile04, tile05, tile06, tile07;
	@FXML Button tile10, tile11, tile12, tile13, tile14, tile15, tile16, tile17;
	@FXML Button tile20, tile21, tile22, tile23, tile24, tile25, tile26, tile27;
	@FXML Button tile30, tile31, tile32, tile33, tile34, tile35, tile36, tile37;
	@FXML Button tile40, tile41, tile42, tile43, tile44, tile45, tile46, tile47;
	@FXML Button tile50, tile51, tile52, tile53, tile54, tile55, tile56, tile57;
	@FXML Button tile60, tile61, tile62, tile63, tile64, tile65, tile66, tile67;
	@FXML Button tile70, tile71, tile72, tile73, tile74, tile75, tile76, tile77; */
	
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
	
	@FXML TilePane tilePane;
	@FXML Label overlord;
	@FXML Label instructions;
	
	/** <h1>Executed on first load of the view</h1>
	 * The method creates the game board 2D-button-array and enemy unit list,
	 * draws unit information on view, and prepares game board for play */
	@FXML private void initialize() {
		rules = new BattleRules();
		turn = 0;
		
		createUnitLists();
		setUnitIcons();
		createBoard();
		updateStats();
		
		overlord.setText("Overlord Level " + Player.level);
		
		placeEnemyUnit(0, 1, 1);
		placeEnemyUnit(1, 1, 3);
		placeEnemyUnit(2, 1, 5);
		placePlayerUnit(0, 6, 2);
		placePlayerUnit(1, 6, 4);
		placePlayerUnit(2, 6, 6);
		
		turnCycle();
	}
	
	/** Send tile coordinates */
	@FXML private void tilePress(ActionEvent event) {
		Button tile = (Button) event.getSource();
		int tileID = Integer.valueOf(tile.getId());
		int x = tileID / 10;
		int y = tileID % 10;
		tile.setText(x + "" + y);
		showValidMoves(rules.isMoveValid(x, y));
		turnCycle();
	}

	/** Send view change request */
	@FXML private void transitionView() {
		NavigationController.loadView(NavigationController.MAINMENU);
	}
	
	/** Generate a fresh list of enemy units for a battle */
	private void createUnitLists() {
		enemyUnitList.clear();
		enemyUnitList.add(new BattleUnit("earth", 1, 15, 0, 1, 1));
		enemyUnitList.add(new BattleUnit("fire", 3, 10, 15, 1, 3));
		enemyUnitList.add(new BattleUnit("water", 2, 10, 10, 1, 5));
		
		// TODO find a better way, this seems crude..
		playerUnitList.clear();
		playerUnitList.add(new BattleUnit(Player.unitList.get(0).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), 6, 2));
		playerUnitList.add(new BattleUnit(Player.unitList.get(1).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), 6, 2));
		playerUnitList.add(new BattleUnit(Player.unitList.get(2).getType(), Player.unitList.get(0).getDamage(),
				Player.unitList.get(0).getHealth(), Player.unitList.get(0).getResource(), 6, 2));
	}
	
	/** Draw the appropriate icon for each unit on the side panels */
	private void setUnitIcons() {
		playerUnit1.getStyleClass().add(Player.unitList.get(0).type);
		playerUnit2.getStyleClass().add(Player.unitList.get(1).type);
		playerUnit3.getStyleClass().add(Player.unitList.get(2).type);
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
	private void placePlayerUnit(int index, int row, int col) {
		tiles[row][col].getStyleClass().add("playerTile");
		tiles[row][col].getStyleClass().add(Player.unitList.get(index).type);
	}
	
	/** Place the Enemy's unit token on a tile
	 * @param index index of the unit in Enemy's unit list
	 * @param row vertical location of the tile (first index in 2D array)
	 * @param col horizontal location of the tile (second index in 2D array) */
	private void placeEnemyUnit(int index, int row, int col) {
		tiles[row][col].getStyleClass().add("enemyTile");
		tiles[row][col].getStyleClass().add(enemyUnitList.get(index).type);
	}
	
	
	
	
	
	
	private void turnCycle() {
		// check for victory
		
		// unit rotation number (turn 0 = index 0, turn 1 = index 1, turn 2 = index 2, turn 3 = index 
		int unitIndex = unitRotation % 3;
		// gameState = current turn phase
		switch (gameState) {
			case "Player:TurnStart":
				// update instructions for player to move
				instructions.setText("Select a tile to move");
				// TODO present move options for unit index turn % 3
				showValidMoves(rules.isMoveValid(playerUnitList.get(unitIndex).getxPos(), playerUnitList.get(unitIndex).getyPos()));
				gameState = "Player:Move"; // await player input
				break;
			case "Player:Move":
				handleMovement();
				gameState = "Player:Action"; // await player input
				break;
			case "Player:SelectAction":
				// update instructions for player to take an action
				instructions.setText("Select a target");
				// TODO present action targets
				gameState = "Player:TurnFinish";
				break;
			case "Player:Action":
				// TODO accept action and execute
				updateStats();
				victoryCheck();
				gameState = "AI:Turn";
				turn++; // advance to the turn counter (odd = AI, even  = Player)
				break;
			case "AI:Turn":
				// TODO add AI methods
				/* << temporary >> */System.out.println("AI move done");
				updateStats();
				victoryCheck();
				gameState = "Player:TurnStart";
				turn++;
				break;
			default:
		}
	}
	
	private void handleMovement() {
		// TODO accept move option and execute
		
	}
	
	private void selectAction() {
		
	}
	
	private void handleAction() {
		
	}
	
	private void aiControl() {
		
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
	
	
	
	
	
	
	
	/** Update health and resource labels next to unit icons */
	private void updateStats() {
		playerUnit1health.setText("" + Player.unitList.get(0).health);
		playerUnit2health.setText("" + Player.unitList.get(1).health);
		playerUnit3health.setText("" + Player.unitList.get(2).health);
		playerUnit1resource.setText("" + Player.unitList.get(0).resource);
		playerUnit2resource.setText("" + Player.unitList.get(1).resource);
		playerUnit3resource.setText("" + Player.unitList.get(2).resource);
		
		enemyUnit1health.setText("" + enemyUnitList.get(0).health);
		enemyUnit2health.setText("" + enemyUnitList.get(1).health);
		enemyUnit3health.setText("" + enemyUnitList.get(2).health);
		enemyUnit1resource.setText("" + enemyUnitList.get(0).resource);
		enemyUnit2resource.setText("" + enemyUnitList.get(1).resource);
		enemyUnit3resource.setText("" + enemyUnitList.get(2).resource);
	}
}