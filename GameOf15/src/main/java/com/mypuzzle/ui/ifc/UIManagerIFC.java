package com.mypuzzle.ui.ifc;

import com.mypuzzle.mng.ifc.GameManagerIFC;

public interface UIManagerIFC {
	
	/**
	 * Initiates a new Game Board size X size
	 * Could be either by a simple terminal I/O or by a dedicated GUI
	 * @param mng - a GameManager interface, containing the data Model
	 */
	public void initGameBoard(GameManagerIFC mng);
	
	/**
	 * Handles User's interactions with the Board
	 * @param obj - a general object containing the User Input parameters.
	 * Could be a either a Java Scanner object for a simple terminal I/O, or any other MouseEvent initiated by a dedicated GUI
	 */
	public void handleUserInput(Object obj);
	
	/**
	 * Updates the board with a new data
	 * Could be either by a simple I/O operation, or by repainting a dedicated GUI components
	 */
	public void updateBoard();
	
}
