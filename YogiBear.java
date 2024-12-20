/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package yogibear;

import javax.swing.SwingUtilities;

/**
 *
 * @author sarehsoltani
 */
public class YogiBear {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
  
       
       
        YogiData yogiData = new YogiData(); 
      Game game = new Game(yogiData);

    SwingUtilities.invokeLater(() -> {
        YogiBearGUI i = new YogiBearGUI(game, yogiData);
    });

    }
    
}
