/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.util.Random;

/**
 *
 * @author sarehsoltani
 */

/**
 * The Levels class handles the generation and management of the game levels, including obstacles, baskets, and the player's initial position.
 */
public class Levels {
    private Cell[][] grid;
    private int rows;
    private int cols;

    /**
     * Constructor to initialize the level with the specified grid size.
     * 
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */
    public Levels(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
    }
    
    /**
     * Generates the current level with obstacles, baskets, and the player.
     * 
     * @param levelNum The current level number.
     */
    public void generate(int levelNum){
        int baseObs = 5;    // start with 5 obstacles
        //int baseRanger = 1; // start with 1 ranger
        int baseBasket = 3; // start with 3 baskets
        
        int numObs = baseObs + levelNum*2;
        //int numRanger = baseRanger + levelNum / 2; // Increase rangers every two levels
        int numBasket = baseBasket + levelNum;
        
        // initialize the grid
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                 grid[i][j] = Cell.EMPTY;
            
            }
        }
        grid[0][0] = Cell.PLAYER;
        placeRandom(Cell.OBSTACLE, numObs);
        placeRandom(Cell.BASKET, numBasket);
       
    }
    
    private void placeRandom(Cell element, int count){
        Random random = new Random();
        while(count > 0){
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
             if (grid[x][y] == Cell.EMPTY) {
                grid[x][y] = element;
                count--;
            }
        }
    }

      public Cell[][] getGrid() {
        return grid;
    }
    
    
    
}
