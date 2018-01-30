/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego_ratongatos;

import java.awt.Color;

/**
 *
 * @author F20LAB303-XXE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PresentationGame pg = new PresentationGame();
        pg.setVisible(true);
        pg.setLocationRelativeTo(null);
        pg.getContentPane().setBackground(Color.cyan.brighter());
    }
    
}
