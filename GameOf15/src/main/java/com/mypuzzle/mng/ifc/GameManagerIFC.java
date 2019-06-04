package com.mypuzzle.mng.ifc;

public interface GameManagerIFC {
	
	public void newGame();
	
	public void reset();
	
	public void shuffle();
	
	public boolean isSolvable();
	
	public boolean isSolved();
	
	public void moveTiles(int steps);
	
	// Exposes an external setter for the 'gameOver' property
	public void setGameOver(boolean gover);
	
	// Exposes an external getter for the 'gameOver' property
	public boolean isGameOver();

	// Exposes an external getter for the 'blankPosition' property
	public int getBlankPos();
	
	// Exposes an external setter for the 'tiles' property
	public void setTiles(int[] tiles);
	
	// Exposes an external getter for our data model
	public int[] getTiles();
}
