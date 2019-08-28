package com.mypuzzle.ui.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mypuzzle.GameSettings;
import com.mypuzzle.GameUtils;
import com.mypuzzle.mng.ifc.GameManagerIFC;
import com.mypuzzle.ui.ifc.UIManagerIFC;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements UIManagerIFC{

	// Game title
	private String gameTilte;
	
	// Size of our Game/Puzzle
	private int gameSize;
	
	// Grid UI Dimension
	private int dimension;
	
	// Start message
	private String startMessage; 
	
	// Check Mark Sign (Unicode)
	private String CHECK_MARK = "\u2713";
	
	// Foreground Color (Tiles)
	private Color FOREGROUND_COLOR;
	
	// Foreground Color (Board)
	private Color BACKGROUND_COLOR;
	
	// Text Color + Tile outline Color
	private Color TEXT_COLOR;
	
	// Foreground Font (Tiles)
	private String FONT = "SansSerif";
	
	// Size of tile on UI
	private int tileSize;
	
	// Margin for the grid on the frame
	private int margin;
	
	// Grid UI Size
	private int gridSize;
	
	// Game manager instance
	private GameManagerIFC gameMng;
	
	public GameBoard(GameSettings settings) {
		this.gameTilte = settings.getTitle();
		this.gameSize = settings.getSize();
		this.dimension = settings.getGridDim();
		this.margin = settings.getGridMargin();
		this.startMessage = settings.getStartMsg();
		this.FOREGROUND_COLOR = new Color(Integer.parseInt(settings.getTilesColorHex(), 16));
		this.BACKGROUND_COLOR = new Color(Integer.parseInt(settings.getBoardColorHex(), 16));
		this.TEXT_COLOR = new Color(Integer.parseInt(settings.getTextColorHex(), 16));
		
		// calculate grid size and tile size  
	    gridSize = (dimension - (2 * margin));
	    tileSize = gridSize / gameSize;
	    
	    // set Panel properties
	    setPreferredSize(new Dimension(dimension, dimension + margin));
	    setBackground(BACKGROUND_COLOR);
	    setForeground(FOREGROUND_COLOR);
	    setFont(new Font(FONT, Font.BOLD, 60));
	}
	
	@Override
	public void startGame(GameManagerIFC mng) {
		
		this.gameMng = mng;
		gameMng.setGameOver(true);
		
		// execute asynchronously (on the AWT event dispatching thread)
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle(gameTilte);
			frame.setResizable(false);
			frame.add(this);
			frame.pack();
			// center on the screen
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
		
		gameMng.newGame();
		
		// add mouse listener for user interactions
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				handleUserInput(e);
			}
		});
	}
	
	/**
	 * Allow users to interact on the Grid by clicking on the Tiles.
	 * Move tiles to solve the game
	 */
	@Override
	public void handleUserInput(Object obj) {
		MouseEvent e = (MouseEvent) obj;
		if(gameMng.isGameOver()){
			gameMng.newGame();
		}
		
		else{
			// get click position
			int ex = e.getX() - margin;
			int ey = e.getY() - margin;
			
			// click on the Grid ?
			if((ex < 0 || ex > gridSize) || (ey < 0 || ey > gridSize)){
				System.out.println("Click is out of range");
				return;
			}
			
			// get tile position on the grid
	        int c1 = ex / tileSize;
	        int r1 = ey / tileSize;
	        GameUtils.printCoordinates("User Click coordinates:", c1, r1);
	        
	        // get blank tile position on the grid (by converting a 1D into a 2D coordinates)
	        int c2 = gameMng.getBlankPos() % gameSize;
	        int r2 = gameMng.getBlankPos() / gameSize;
	        
	        // move tiles if needed, then check if solved
	        int steps = GameUtils.calcSteps(r1, c1, r2, c2, gameSize);
	        gameMng.moveTiles(steps);
			GameUtils.printMovement(steps);
		}
		
		updateBoard();
	}

	@Override
	public void updateBoard() {
		// Repaint this component
		// By invoking its internal update methods (i.e: paintComponent())
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawGrid(g2D);
		drawStartMessage(g2D);
	}
	
	private void drawGrid(Graphics2D g){
		
		int [] tiles = gameMng.getTiles();
		boolean gameOver = gameMng.isGameOver();
		
		for(int i=0; i < tiles.length; i++) {
			// Convert 1D array integers into a 2D coordinates
			int row = i / gameSize;
			int col = i % gameSize;
			// Convert to UI coordinates
			int x = margin + (col * tileSize);
			int y = margin + (row * tileSize);
			
			// check special case for a blank tile:
			if(tiles[i] == 0){
				if(gameOver){
					g.setColor(TEXT_COLOR);
					drawCenteredString(g, CHECK_MARK, x, y);
				}
				continue;
			}
			
			// for other tiles
			g.setColor(getForeground());
			g.fillRoundRect(x, y, tileSize, tileSize, 25, 25); // fill a solid tile (without outline)
			
			g.setColor(TEXT_COLOR);
			g.drawRoundRect(x, y, tileSize, tileSize, 25, 25); // draw outline
			
			g.setColor(TEXT_COLOR); // back to white color, to display the tile numbers
			drawCenteredString(g, String.valueOf(tiles[i]), x, y);
		}
	}
	
	private void drawStartMessage(Graphics2D g){
		if(gameMng.isGameOver()){
			g.setFont(getFont().deriveFont(Font.BOLD, 18)); //use the same game font, however bolder & bigger
			g.setColor(TEXT_COLOR);
			g.drawString(startMessage, (getWidth() - g.getFontMetrics().stringWidth(startMessage)) / 2, getHeight() - margin);
			System.out.println("Puzzle is solved ! Game over");
		}
	}
	
	private void drawCenteredString(Graphics2D g, String s, int x, int y){
		// center string s for the given tile (x,y)
	    FontMetrics fm = g.getFontMetrics();
	    int asc = fm.getAscent();
	    int desc = fm.getDescent();
	    g.drawString(s,  x + (tileSize - fm.stringWidth(s)) / 2, y + (asc + (tileSize - (asc + desc)) / 2));
	}
}
