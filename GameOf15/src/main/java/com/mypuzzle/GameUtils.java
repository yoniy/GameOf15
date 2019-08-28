package com.mypuzzle;

public class GameUtils {

    public static int calcSteps(int clickedRow, int clickedCol, int blankRow, int blankCol, int gameSize){

        int steps = 0;

        // blank tile and Clicked tile are on the same row + are close neighbors (left/right)
        if(clickedRow == blankRow && Math.abs(clickedCol - blankCol) == 1){
            // move 1 step backward/forward
            steps = clickedCol > blankCol ? 1: -1;
        }
        // blank tile and Clicked tile are on the same column + are close neighbors (up/down)
        else if(clickedCol == blankCol && Math.abs(clickedRow - blankRow) == 1){
            steps = clickedRow > blankRow ? gameSize: -gameSize;
        }

        return steps;

    }

    public static void printCoordinates(String prefix, int x, int y){
        System.out.println(prefix + " " + "(" + x + "," + y + ")");
    }

    public static void printMovement(int steps){
        if(steps != 0){
            String direction = steps < 0 ? "back": "forward";
            System.out.println("Moving the blank tile " + Math.abs(steps) + " steps " + direction + "\n");
        }
        else{
            System.out.println("Nothing to move\n");
        }
    }
}
