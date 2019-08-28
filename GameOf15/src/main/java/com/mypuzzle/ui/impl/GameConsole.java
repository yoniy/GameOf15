package com.mypuzzle.ui.impl;

import com.mypuzzle.GameSettings;
import com.mypuzzle.GameUtils;
import com.mypuzzle.mng.ifc.GameManagerIFC;
import com.mypuzzle.ui.ifc.UIManagerIFC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
//import com.sun.xml.internal.ws.util.StringUtils;

public class GameConsole implements UIManagerIFC {
	
	// Size of our Game/Puzzle
	private int gameSize;
	
	// Start message
	private String startMessage; 
	
	// Game manager instance
	private GameManagerIFC gameMng;
	
	BufferedReader bufferReader;
	
	public GameConsole(GameSettings settings) {
		this.gameSize = settings.getSize();
		this.startMessage = settings.getStartMsg();
		this.bufferReader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public void startGame(GameManagerIFC mng) {
		this.gameMng = mng;
		gameMng.setGameOver(true);
		
		mng.newGame();
		printBoard();
		
		while (!gameMng.isGameOver()){
			UserInstructions userInstruction = getUserInstructions(bufferReader);
			handleUserInput(userInstruction);
		}

		System.out.println("Puzzle is solved ! Game over");
	}
	
	private UserInstructions getUserInstructions(BufferedReader in){
		UserInstructions instructions = new UserInstructions();
		
		try{
			System.out.print("Enter a Tile number to move: ");
			String tile = in.readLine();
			instructions.setChosenTile(tile);
		}
		
		catch (IOException e){
			e.printStackTrace();
		}
		
		return instructions;
	}
	
	@Override
	public void updateBoard() {
		printBoard();
	}

	@Override
	public void handleUserInput(Object userInstruction) {

		if(gameMng.isGameOver()){
			gameMng.newGame();
		}

		else{
			// validate user input
			ValidationResults results = validateUserInput((UserInstructions) userInstruction);
			if(!results.valid){
				System.err.println(results.errMsg + "\n");
				try{ Thread.sleep(100); } catch (InterruptedException e){};
				return;
			}

			int tile = Integer.parseInt(((UserInstructions) userInstruction).getChosenTile());
			int tilePos = getPosition(gameMng.getTiles(), tile);

			int r1 = tilePos / gameSize;
			int c1 = tilePos % gameSize;

			int r2 = gameMng.getBlankPos() / gameSize;
			int c2 = gameMng.getBlankPos() % gameSize;

			System.out.println("Selected tile: (" + r1 + "," + c1 + ")");
			System.out.println("Blank tile: (" + r2 + "," + c2 + ")");

			int steps = GameUtils.calcSteps(r1, c1, r2, c2, gameSize);
			gameMng.moveTiles(steps);
			GameUtils.printMovement(steps);
		}

		updateBoard();
	}
	
	private ValidationResults validateUserInput(UserInstructions userInstruction){
		ValidationResults results = new ValidationResults();

		// Check if Number format
		if(!userInstruction.getChosenTile().matches("-?\\d+(\\.\\d+)?")){
			results.valid = false;
			results.errMsg = "Chosen Tile is not a number. Please try again...";
		}

		// Chosen Tile dos'nt exist
		else if(!IntStream.of(gameMng.getTiles()).anyMatch(x -> x == Integer.parseInt(userInstruction.getChosenTile()))){
			results.valid = false;
			results.errMsg = "Chosen Tile doesn't exist. Please try again...";
		}

		return results;
	}
	
	private void printBoard() {
		
		int [] tiles = gameMng.getTiles();

		System.out.println();
		
		for(int i=0; i < tiles.length; i++){
			
			// check special case for a blank tile
			String val = tiles[i] == 0 ? " " : String.valueOf(tiles[i]);
			System.out.print(val + calcBlankSize(i, tiles));

			// New line
			if( (i + 1) % gameSize == 0){
				System.out.println();
			}
		}
		System.out.println();
	}
	
	private boolean isTwoDigits(int tile){
		return Integer.toString(Math.abs(tile)).trim().length() == 2;
	}
	
	private String calcBlankSize(int i, int[] tiles){
		
		// 4 blanks for 2 digits, 5 blanks for 1 digit
		return isTwoDigits(tiles[i]) ? "    " : "     ";
	}

	private int getPosition(int [] arr, int find){

		for(int i=0; i < arr.length ; i++){
			if(arr[i] == find){
				return i;
			}
		}
		return 0;
	}
	
}

class UserInstructions {
	
	private String chosenTile;

	public String getChosenTile() {
		return chosenTile;
	}
	
	public void setChosenTile(String chosenTile) {
		this.chosenTile = chosenTile;
	}
}


class ValidationResults {

	public boolean valid = true;
	public String errMsg;
}