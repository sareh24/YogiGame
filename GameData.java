/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

/**
 *
 * @author sarehsoltani
 */
public class GameData {
    
    private String playerName;
    private int score;
   

    public GameData(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
      
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }



    @Override
    public String toString() {
        return "GameData{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                
                '}';
    }
    
}
