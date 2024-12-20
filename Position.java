package yogibear;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sarehsoltani
 */
/**
 * The Position class represents the coordinates of an object (player, ranger, etc.) on the game grid.
 */
public class Position {
    private int x;
    private int y;
    
    /**
     * Constructor to initialize a position with specific x and y coordinates.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}
