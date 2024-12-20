/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package yogibear;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author sarehsoltani
 */
/**
 * The Game class represents the main game logic.
 * It handles the game's state, player movements, level generation, and game progression.
 */
public class Game {

    private final Player player;
    private Levels levels;
    private ArrayList<Ranger> rangers;
    private int currentLevel;
    private boolean isGameOver;
    //private int playerId;
    private YogiData yogiData;
    private YogiBearGUI yogiGUI;
    private long levelStartTime; // Start time of the level
    private Timer gameTimer; // Timer object for counting elapsed time
     
    //private int scores;

    
    public Game(YogiData yogiData) {
        this.yogiData = yogiData;
        //this.yogiDatabase = yogiDatabase;
        this.currentLevel = 1;
        //this.currentScore = 0;
        this.isGameOver = false;
        this.player = new Player(0, 0);
        yogiGUI = new YogiBearGUI(this, yogiData);
        initializeLevel();
    }

 
    
 
    private void initializeLevel(){
        this.levels = new Levels(10, 10);
        this.levels.generate(currentLevel);
        this.rangers = new ArrayList<>();
        this.rangers.add(new Ranger(5, 5));
        this.rangers.add(new Ranger(7, 2));
        player.resetLives();
        player.move(0, 0);
        startLevelTimer();

    // Reset the grid for player position
          Cell[][] grid = levels.getGrid();
          grid[player.getPosition().getX()][player.getPosition().getY()] = Cell.PLAYER;
    }
   
    public Player getPlayer() {
        return player;
    }
    
    public int getScores(){
       return player.getCurrentScore();
    }
   /**
     * Starts a new game level with a timer.
     */
    private void startLevelTimer() {
        // Record the time at the start of the level
        levelStartTime = System.currentTimeMillis();
        
        // Initialize the timer task
        gameTimer = new Timer();
        
        // Schedule the task to update the elapsed time every 1000ms (1 second)
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = (System.currentTimeMillis() - levelStartTime) / 1000;
                System.out.println("Time elapsed: " + elapsedTime + " seconds");
            }
        }, 0, 1000); // Start immediately, update every second
    }

    
    /**
     * Starts the game and prints the initial grid.
     */
    public void startGame(){
         System.out.println("Starting Game at Level " + currentLevel);
         printGrid();
    }
    
    /**
     * Processes the player's movement and handles interactions with the grid, such as obstacles and baskets.
     * 
     * @param dx The change in the x-axis.
     * @param dy The change in the y-axis.
     */
    public void processMove(int dx, int dy) {
    if (isGameOver) {
         System.out.println("Game Over! Please restart.");
         player.setPlayerName(yogiGUI.askForPlayerName());
         System.out.println("Saving score: " + player.getCurrentScore());
         yogiData.saveGameData(player.getPlayerName(), player.getCurrentScore());
        yogiGUI.askToRestart();
         
         return;
        
    }

    // Calculate new player position
    int newX = player.getPosition().getX() + dx;
    int newY = player.getPosition().getY() + dy;

    // Boundary check: Prevent movement outside the grid
    if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
        System.out.println("Cannot move outside the grid!");
        return;
    }

    Cell[][] grid = levels.getGrid();

    // Check for obstacles
    if (grid[newX][newY] == Cell.OBSTACLE) {
        System.out.println("You hit an obstacle! Try another direction.");
        return;
    }
  
    // Handle interactions at the new position
    if (grid[newX][newY] == Cell.BASKET) {
    
        // Basket collection
        player.collectBasket();
        player.incrementScoere(10);
        
        grid[newX][newY] = Cell.EMPTY; // Mark basket as collected
        System.out.println("Collected a basket! Total: " + player.getNumCollectBask());
    }
     grid[player.getPosition().getX()][player.getPosition().getY()] = Cell.EMPTY;
     player.move(newX, newY);
     grid[newX][newY] = Cell.PLAYER;
    
    // Check for ranger interaction
    for (Ranger ranger : rangers) {
        if (ranger.getPosition().getX() == newX && ranger.getPosition().getY() == newY) {
            player.loseLife();
            System.out.println("Encountered a ranger! Lives left: " + player.getLives());

            if (player.getLives() == 0) {
                // Game over if no lives remain
                isGameOver = true;
 
                System.out.println("Game Over! You lost all your lives.");
                return;
            } else {
                // Respawn the player if they still have lives
                player.move(0, 0); // Starting position
                System.out.println("Respawned at the starting point!");
                return; // End turn after respawning
            }
        }
    }
    // Move rangers after the player's turn
     moveRangers();

    
    // Check if all baskets are collected
    if (areAllBasketsCollected(grid)) {
        System.out.println("Level completed! Proceeding to next level...");
        nextLevel();
        return; // Level is complete, no need to continue further in this method
    }

    // Print updated grid after the move
    printGrid();
}
      private void moveRangers() {
       for (Ranger ranger : rangers) {
        // Move each ranger based on their movement logic
          ranger.move(levels.getGrid()); // Passing the grid to the ranger's move method
    }
}
 
    private boolean areAllBasketsCollected(Cell[][] grid) {
    for (Cell[] row : grid) {
        for (Cell cell : row) {
            if (cell == Cell.BASKET) {
                return false;
            }
        }
    }
    return true;
}

    public Levels getLevels() {
        return levels;
    }
 
    private void nextLevel() {
    currentLevel++;
    if (currentLevel > 10) {
       // System.out.println("Congratulations! You completed all levels!");
        isGameOver = true;
        yogiData.saveGameData(player.getPlayerName(), player.getCurrentScore());
        yogiGUI.showGameOverMessage("Congratulations, you won!");
        
    } else {
        initializeLevel();
        
    }
}

    public void restartGame() {
    System.out.println("Restarting Game...");
   yogiData.saveAllToDatabase();
    this.currentLevel = 1;
    this.isGameOver = false;
    initializeLevel();
    startGame();
    
}
    
  
    private void printGrid() {
    Cell[][] grid = levels.getGrid();
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
            if (i == player.getPosition().getX() && j == player.getPosition().getY()) {
                System.out.print("P "); // Display the player
            } else {
                System.out.print(grid[i][j] + " ");
            }
        }
        System.out.println();
    }
}

    public boolean isGameOver() {
    return isGameOver;
}
  public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel(); // Stop the timer task
        }
    }
    
}