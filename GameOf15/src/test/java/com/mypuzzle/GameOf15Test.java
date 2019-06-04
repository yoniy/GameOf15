package com.mypuzzle;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mypuzzle.mng.ifc.GameManagerIFC;
import com.mypuzzle.mng.impl.GameManager;

//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GameOf15Test
{
	private static GameManagerIFC manager;
	
	@BeforeClass
	public static void initManager(){
		manager = new GameManager(4);
	}
	
	@Test
	public void testIsSolvable1(){
		int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15};
		manager.setTiles(tiles);
		assertEquals(true, manager.isSolvable());
	}
	
	@Test
	public void testIsSolvable2(){
		int[] tiles = {1,13,3,4,5,14,7,8,9,10,11,12,2,6,15,0};
		manager.setTiles(tiles);
		assertEquals(true, manager.isSolvable());
	}
	
	@Test
	public void testIsSolvable3(){
		int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,15,14,13,0};
		manager.setTiles(tiles);
		assertEquals(false, manager.isSolvable());
	}
	
	@Test
	public void testIsSolvable4(){
		int[] tiles = {15,2,3,4,12,6,9,8,7,10,11,5,13,14,0,1};
		manager.setTiles(tiles);
		assertEquals(false, manager.isSolvable());
	}
	
	@Test
	public void testIsSolved1(){
		int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		manager.setTiles(tiles);
		assertEquals(true, manager.isSolved());
	}
	
	@Test
	public void testIsSolved2(){
		int[] tiles = {0,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1};
		manager.setTiles(tiles);
		assertEquals(false, manager.isSolved());
	}
	
	@Test
	public void testReset(){
		int[] tiles = {5,2,3,4,1,7,6,8,9,14,11,12,13,0,15,10};
		manager.setTiles(tiles);
		manager.reset();
		assertEquals(true, manager.isSolved());
	}
	
	@Test
	public void testShuffle(){
		int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		manager.setTiles(tiles);
		manager.shuffle();
		
		int blankTile = tiles[tiles.length-1];
		assertEquals(0, blankTile);
	}
	
	// The below 3 tests testMoveTiles1() --> testMoveTiles2() --> testMoveTiles3() - must be followed !
	// do not change neither methods nor the order of their appearance
	@Test
	public void testMoveTiles1(){
		int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		manager.setTiles(tiles);
		manager.moveTiles(-1);
		
		int blankTile = tiles[tiles.length-2];
		assertEquals(0, blankTile);
	}
	
	@Test
	public void testMoveTiles2(){
		manager.moveTiles(-4);
		int blankTile = manager.getTiles()[10];
		assertEquals(0, blankTile);
	}
	
	@Test
	public void testMoveTiles3(){
		manager.moveTiles(1);
		int blankTile = manager.getTiles()[11];
		assertEquals(0, blankTile);
	}
	
	@Test
	public void testNewGame(){
		manager.reset();
		assertEquals(true, manager.isSolvable());
		assertEquals(false, manager.isGameOver());
	}
	
}
