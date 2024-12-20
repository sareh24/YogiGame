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
 * The Ranger class represents an enemy that moves in the game and interacts with the player.
 */
public class Ranger {
     private Position position; // Ranger's position
     private int dx, dy; // Movement direction

     /**
     * Constructor to initialize the ranger with a starting position.
     * 
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Ranger(int x, int y) {
        this.position = new Position(x,y);
        this.dx = 1; // default direction
        this.dy = 0;
    }

    public Position getPosition() {
        return position;
    }
    
    /**
     * Moves the ranger based on the current direction.
     * 
     * @param grid The current game grid.
     */
    public void move(Cell[][] grid){
        int newX =  position.getX() + dx;
        int newY =  position.getY() + dy;
        
        if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length &&  grid[newX][newY] != Cell.OBSTACLE){
            
            grid[position.getX()][position.getY()] = Cell.EMPTY;
            position.setPosition(newX, newY);
            grid[newX][newY] = Cell.RANGER;  
        }else{
              
           dx = -dx;
           dy = -dy;
        }
    }
    
    
    
    
}
