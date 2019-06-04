package com.mypuzzle;

public class GameSettings {
	
	private int size;
	
	private String title;
	
	private String startMsg;
	
	private int gridDim;
	
	private int gridMargin;
	
	private String tilesColorHex;
	
	private String boardColorHex;
	
	private String textColorHex;
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStartMsg() {
		return startMsg;
	}
	
	public void setStartMsg(String startMsg) {
		this.startMsg = startMsg;
	}

	public int getGridDim() {
		return gridDim;
	}

	public void setGridDim(int gridDim) {
		this.gridDim = gridDim;
	}

	public int getGridMargin() {
		return gridMargin;
	}

	public void setGridMargin(int gridMargin) {
		this.gridMargin = gridMargin;
	}

	public String getTilesColorHex() {
		return tilesColorHex;
	}

	public void setTilesColorHex(String tilesColorHex) {
		this.tilesColorHex = tilesColorHex;
	}

	public String getBoardColorHex() {
		return boardColorHex;
	}

	public void setBoardColorHex(String boardColorHex) {
		this.boardColorHex = boardColorHex;
	}

	public String getTextColorHex() {
		return textColorHex;
	}

	public void setTextColorHex(String textColorHex) {
		this.textColorHex = textColorHex;
	}
}
