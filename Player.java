/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

/**
 *
 * @author sarehsoltani
 */

/**
 * The Player class represents the player character in the game.
 * It holds information about the player's position, lives, and collected baskets.
 */
public class Player {
    private  String playerName;
    private Position position;
    private int lives;
    private int numCollectBask;
    private int currentScore;
    private int defaultLives = 3;

    
     /**
     * Constructor to create a new player at a given position.
     * 
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Player(int x, int y) {
        this.position = new Position(x,y);
        this.lives = 3; // inisual lives
        this.numCollectBask = 0;
        this.currentScore = 0;
        
    }

    public String getPlayerName() {
        return playerName;
    }
    

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

   
    

    public Position getPosition() {
        return position;
    }

    public int getNumCollectBask() {
        return numCollectBask;
    }

  
    
    public int getLives() {
        return lives;
    }
    
    public void resetLives() {
        this.lives = defaultLives;
    }


    /**
     * Moves the player to a new position.
     * 
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    public void move(int newX, int newY) {
         position.setPosition(newX, newY);
    }
    
    /**
     * Increases the number of baskets collected by the player.
     */
    public void collectBasket(){
       numCollectBask++;
    }
    
    public void incrementScoere(int points){
       this.currentScore += points;
       System.out.println("Player's current score: " + this.currentScore);

    }
    
     /**
     * Decreases the player's life by one.
     */
    public void loseLife(){
        this.lives--;
    
    }
    
}
