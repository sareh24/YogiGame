/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 *
 * @author sarehsoltani
 */

/**
 * The YogiBearGUI class represents the graphical user interface (GUI) of the Yogi Bear game.
 * It extends the JFrame class to provide the main window, and uses a JPanel to render the game grid.
 */
public class YogiBearGUI extends JFrame{
    private Game game;
      private final YogiData yogiData;
   // private YogiDatabase yogiDatabase;
    private JPanel gamePanel;
   
    private JMenuBar menuBar;
    private final RestartListener resLi;
    


    
    public YogiBearGUI(Game game, YogiData yogiData) {
        this.game = game;
        this.yogiData = yogiData;
        resLi = new RestartListener();
        setTitle("Yogi Bear Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();
        initializeMenu();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
      
    }
    


       
        /**
        * Initializes the game panel and adds it to the frame.
        * Sets the preferred size and attaches the KeyListener.
        */
        private void initializeComponents() {
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(500, 500));
        add(gamePanel, BorderLayout.CENTER);

        addKeyListener(new GameKeyListener());
    }
   
        
     /**
     * Initializes the menu bar with options to restart the game and view high scores.
     */
      private void initializeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem restartItem = new JMenuItem("Restart Game");
        restartItem.addActionListener(new RestartListener());

        JMenuItem highscoreItem = new JMenuItem("High Scores");
        highscoreItem.addActionListener(new HighscoreListener());

        menu.add(restartItem);
        menu.add(highscoreItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);
    }
      
 
    
     /**
     * Draws the game grid on the JPanel.
     * Each cell is colored based on its content (empty, obstacle, basket, ranger, player).
     * 
     * @param g The Graphics object used to render the grid.
     */
     private void drawGrid(Graphics g) {
      
        Cell[][] grid = game.getLevels().getGrid();
        int cellSize = 50;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case EMPTY -> g.setColor(Color.WHITE);
                    case OBSTACLE -> g.setColor(Color.GREEN); 
                    case BASKET -> g.setColor(Color.YELLOW);
                    
                    case RANGER -> g.setColor(Color.RED);
                   case PLAYER ->  g.setColor(Color.BLUE);
                }
                 g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                 g.setColor(Color.BLACK);
               g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
    
      /**
      * Key listener to handle player movement based on key presses.
      */
      private class GameKeyListener extends KeyAdapter {
          /**
         * Handles key presses for movement.
         * 
         * @param e The KeyEvent triggered by the key press.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> game.processMove(-1, 0); // Move up
                case KeyEvent.VK_DOWN -> game.processMove(1, 0);  // Move down
                case KeyEvent.VK_LEFT -> game.processMove(0, -1); // Move left
                case KeyEvent.VK_RIGHT -> game.processMove(0, 1);  // Move right
            }
            gamePanel.repaint();
        }
    }
         /**
     * ActionListener for restarting the game.
     */
       private class RestartListener implements ActionListener {
         /**
         * Restarts the game when triggered.
         * 
         * @param e The ActionEvent triggered by clicking the restart menu item.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            game.restartGame();
            gamePanel.repaint();
        }
    }
       /**
     * ActionListener for showing the high scores.
     */
    private class HighscoreListener implements ActionListener {
         /**
         * Displays the high scores when triggered.
         * 
         * @param e The ActionEvent triggered by clicking the high scores menu item.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
        
             StringBuilder highScores = new StringBuilder("High Scores:\n");
             
            List<String> scores = yogiData.fetchTop10Scores();

              for (String score : scores) {
                highScores.append(score).append("\n");
            }

            JOptionPane.showMessageDialog(YogiBearGUI.this, highScores.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        }
            

    }
    
    public String askForPlayerName() {
    String playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.QUESTION_MESSAGE);

    if (playerName == null || playerName.trim().isEmpty()) {
        playerName = "Guest"; // Default name if no input is provided
    }

    return playerName.trim();
}
     public void askToRestart() {
       int choice = JOptionPane.showConfirmDialog(
        this, 
        "Would you like to restart the game?", 
        "Restart Game", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE
    );

    if (choice == JOptionPane.YES_OPTION) {
        resLi.actionPerformed(null); 
    } else {
        JOptionPane.showMessageDialog(this, "Thank you for playing!", "Exit", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

   
}
      public void showGameOverMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

}

