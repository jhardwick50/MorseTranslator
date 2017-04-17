/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator;

import javax.swing.JFrame;

/**
 *
 * @author Jason
 */
public class MorseCodeTranslator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MorseServer application = new MorseServer();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.execute();
       
    }
    
}
