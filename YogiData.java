/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * The {@code YogiData} class manages the saving, retrieval, and display of game data,
 * including player scores, completed games, and high scores. It interacts with a MySQL 
 * database to store and retrieve data and provides methods to manage game data in memory.
 * <p>
 * It provides functionality to check if a player exists in the database, save completed
 * games to memory and database, retrieve all completed games, display game data, and 
 * fetch the top 10 high scores from the database.
 * </p>
 * <p>
 * This class works in conjunction with a SQL database to persist game data, utilizing 
 * {@link GameData} objects to store the player's name and score.
 * </p>
 * 
 * @author sarehsoltani
 */
public class YogiData {
     private ArrayList<GameData> completedGames;
    private HashMap<String, GameData> gameDataMap;

    private static final String URL = "jdbc:mysql://localhost:3306/yogi";
    private static final String USER = "root";
    private static final String PASS = "Abc12345678";
    
    /**
     * Constructs a new {@code YogiData} object, initializing in-memory storage 
     * for completed games and game data mapping.
     */
    public YogiData() {
        completedGames = new ArrayList<>();
        gameDataMap = new HashMap<>();
    }

     /**
     * Saves game data for the given player name and score. If the player does not exist 
     * in the database, it inserts a new record. It also updates the in-memory storage of 
     * completed games.
     * 
     * @param playerName the name of the player
     * @param score the score achieved by the player
     */
    public void saveGameData(String playerName, int score) {
    // Define SQL query to check if a player already exists in the players table
    String checkPlayerSql = "SELECT COUNT(*) FROM players WHERE player_name = ?";
    // Establish a connection to the database using JDBC and the provided credentials
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         // Prepare the query to check if the player already exists in the 'players' table
         PreparedStatement pstmtCheck = conn.prepareStatement(checkPlayerSql)) {
        
        pstmtCheck.setString(1, playerName);
        try (ResultSet rs = pstmtCheck.executeQuery()) {
            rs.next();
             // Check if the count of matching rows is 0 (meaning player doesn't exist in the database)
            if (rs.getInt(1) == 0) {
                // If the player does not exist, insert into the players table
                String insertPlayerSql = "INSERT INTO players (player_name) VALUES (?)";
                try (PreparedStatement pstmtInsert = conn.prepareStatement(insertPlayerSql)) {
                    pstmtInsert.setString(1, playerName);
                    // Execute the insertion to add the new player to the 'players' table
                    pstmtInsert.executeUpdate();
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error checking or inserting player: " + e.getMessage());
    }  
         // Create a new GameData object with the provided playerName and score
        GameData gameData = new GameData(playerName, score);
        // Add the newly created GameData object to the completedGames list (in memory)
        completedGames.add(gameData);
        gameDataMap.put(playerName, gameData); // Updates if the playerName already exists
        System.out.println("Game data saved for player: " + playerName);
         // Call saveAllToDatabase() to persist all in-memory game data to the database
        saveAllToDatabase();
  
    }

    /**
     * Saves all in-memory game data (player scores) to the database. 
     * Updates the player's score in the database if it is higher than 
     * the existing score.
     */
    
    public void saveAllToDatabase() {
        // Define the SQL query to insert or update player's score in the highscores table.
       // If a player already exists, their score will be updated to the higher value between the current and new score.
         String sql = "INSERT INTO highscores (player_name, score) VALUES (?, ?) " +
                 "ON DUPLICATE KEY UPDATE score = GREATEST(score, VALUES(score))";
       // Establish a connection to the database using JDBC and provided credentials
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             // Prepare the SQL query for execution (insert or update depending on the situation)
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Iterate through each player in the gameDataMap (which stores player names and their scores)
            for (String playerName : gameDataMap.keySet()) {
                
                GameData gameData = gameDataMap.get(playerName);
                // Set the playerName parameter for the SQL query (1st parameter in the INSERT statement)
                pstmt.setString(1, gameData.getPlayerName());
                pstmt.setInt(2, gameData.getScore());
              //  pstmt.setInt(3, gameData.getLives());
                pstmt.executeUpdate();
            }
            System.out.println("All data saved to the database.");
        } catch (SQLException e) {
            System.err.println("Error saving data to database: " + e.getMessage());
        }
    }

   
    // Retrieve all completed games in memory
    public ArrayList<GameData> getAllCompletedGames() {
        return completedGames;
    }

    // Display all in-memory game data
    public void displayAllMemoryData() {
        if (completedGames.isEmpty()) {
            System.out.println("No games completed yet.");
        } else {
            for (GameData gameData : completedGames) {
                System.out.println("Player: " + gameData.getPlayerName() + 
                                   ", Score: " + gameData.getScore() 
                                   );
            }
        }
    }
     /**
     * Fetches the top 10 highest scores from the database.
     * 
     * @return a list of strings containing the player name and their score for the top 10 high scores
     */
       public List<String> fetchTop10Scores() {
        List<String> highScores = new ArrayList<>();
        String sql = "SELECT player_name, score FROM highscores ORDER BY score DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");
                highScores.add(playerName + " - " + score);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching high scores: " + e.getMessage());
        }

        return highScores;
    }

}
