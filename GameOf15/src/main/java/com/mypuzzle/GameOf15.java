package com.mypuzzle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mypuzzle.mng.ifc.GameManagerIFC;
import com.mypuzzle.mng.impl.GameManager;
import com.mypuzzle.ui.ifc.UIManagerIFC;
import com.mypuzzle.ui.impl.GameBoard;
import com.mypuzzle.ui.impl.GameConsole;

public class GameOf15 {
	
    public static void main( String[] args ){
    	new GameOf15();
    }
    
    public GameOf15() {
    	
    	// Initiate game settings from config.properties
    	System.out.println("Loading game settings");
    	GameSettings settings = loadSettings("config.properties");
    	
    	// Instantiate our Game Manager
    	System.out.println("Initiating game manager");
    	GameManagerIFC gameMng = new GameManager(settings);
    	
    	// Instantiate our Game Board
    	System.out.println("Initiating UI Board");
//    	UIManagerIFC board = new GameBoard(settings);
    	UIManagerIFC console = new GameConsole(settings);
    	
    	// Start the game
    	System.out.println("Starting the game...");
//    	board.startGame(gameMng);
    	console.startGame(gameMng);
	}
    
    public GameSettings loadSettings(String fileName){
    	
    	GameSettings settings = new GameSettings();
    	
    	try(InputStream input = GameOf15.class.getClassLoader().getResourceAsStream(fileName)){
    		
    		Properties prop = new Properties();
    		
    		if(input == null){
    			throw new IOException("unable to find config.properties. Make sure it's in the classpath");
    		}
    		
    		// load a properties file from class path
    		prop.load(input);
    		
    		// initiate all game settings
    		settings.setSize(Integer.parseInt(prop.getProperty("game.size")));
    		settings.setTitle(prop.getProperty("ui.game.title"));
    		settings.setStartMsg(prop.getProperty("ui.start.msg"));
    		settings.setGridDim(Integer.parseInt(prop.getProperty("ui.grid.dimension")));
    		settings.setGridMargin(Integer.parseInt(prop.getProperty("ui.grid.margin")));
    		settings.setTilesColorHex(prop.getProperty("ui.tiles.color.hex"));
    		settings.setBoardColorHex(prop.getProperty("ui.board.color.hex"));
    		settings.setTextColorHex(prop.getProperty("ui.text.color.hex"));
    	}
    	
		catch (IOException e) {
			System.out.println("Failed to load game settings/properties");
			e.printStackTrace();
			System.exit(1);
		}
    	
    	return settings;
    }
}
    
