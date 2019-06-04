package com.mypuzzle.mng.impl;

import java.util.Random;

import com.mypuzzle.GameSettings;
import com.mypuzzle.mng.ifc.GameManagerIFC;

public class GameManager implements GameManagerIFC {
	
	// Storing our tiles in a 1D array of integers
	private int [] tiles;

	// Number of tiles
	private int numOfTiles;

	// Position of the blank tile
	private int blankPos;

	// Random Object to shuffle tiles
	private Random RANDOM;
	
	// Indicating whether the game is over or not
	private boolean gameOver;
	
	public GameManager(GameSettings settings) {
		int size = settings.getSize();
		numOfTiles = (size * size) - 1; // -1 because we don't count the blank tile
		tiles = new int[size * size];
		RANDOM = new Random();
	}
	
	@Override
	public boolean isGameOver(){
		return gameOver;
	}
	
	@Override
	public void setGameOver(boolean gover){
		this.gameOver = gover;
	}
	
	@Override
	public int[] getTiles() {
		return this.tiles;
	}
	
	@Override
	public int getBlankPos() {
		return blankPos;
	}
	
	/**
	 * Initiates a new Game:
	 * Keep shuffling all tiles until grid is solvable 
	 */
	@Override
	public void newGame() {

		int attempts = 1;
		do{
			reset();   // reset tiles to initial state
			System.out.println("Shuffeling puzzle tiles, attempts = " + attempts++);
			shuffle(); // shuffle
		}
		while(!isSolvable()); // keep shuffling until grid is solvable
		
		gameOver = false;
	}

	
	/**
	 * Sort all tiles in ascending order: 1 to 15
	 */
	@Override
	public void reset() {
		for(int i = 0; i < tiles.length; i++){
			tiles[i] = (i+1) % tiles.length;
		}
		
		// blank position is last
		blankPos = tiles.length - 1;
	}

	
	/**
	 * Shuffle all tiles by switching between a random tile to another tile taken from the end
	 * Exclude the blank tile, leave it in the solved position
	 */
	@Override
	public void shuffle() {

		int num = numOfTiles;
		
		while (num > 1){
			// get a random tile
			int rand = RANDOM.nextInt(num--);
			int tmp = tiles[rand];
			// switch between tiles
			tiles[rand] = tiles[num];
			tiles[num] = tmp;
		}
	}

	/**
	 * According to definition:
	 * only half permutations of the puzzle are solvable.
	 * Whenever a tile is preceded by another tile with higher value - it counts an an Inversion.
	 * A puzzle is solvable only if the number of Inversion is even
	 * 
	 * Based on that:
	 * The method counts all Inversions and return true if this number is Even, false otherwise.
	 */
	@Override
	public boolean isSolvable() {
		int invCounter = 0;
		
		for(int i = 0; i < numOfTiles; i++){
			for(int j=0; j<i; j++){
				if(tiles[j] > tiles[i]){
					invCounter++;
				}
			}
		}
		
		return (invCounter % 2) == 0;
	}
	

	/**
	 * The puzzle is solved if all tiles are sorted from 1 to 15 (ascending) and the last tile is blank.
	 * The method return true if the puzzle is solved, false otherwise
	 */
	@Override
	public boolean isSolved() {
		
		// if the last position isn't blank - return false
		if(tiles[tiles.length - 1] != 0){
			return false;
		}
		
		for(int i = numOfTiles - 1; i >= 0; i--){
			if (tiles[i] != (i + 1)){
				return false;
			}
		}
		
		// all tiles are sorted from 1 to 15. Last tile is blank.
		return true;
		
	}
	
	@Override
	public void moveTiles(int steps) {
		
		if(steps != 0){
        	// move tiles backward (-steps) or forward (+steps)
            int newBlankPos = blankPos + steps;
            tiles[blankPos] = tiles[newBlankPos];
            blankPos = newBlankPos;
            tiles[blankPos] = 0;
        }
        
        // check if game is solved
		gameOver = isSolved();
	}
}
