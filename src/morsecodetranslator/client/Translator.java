/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator.client;

import java.util.Scanner;

/**
 *
 * @author Jason
 */
public class Translator {
    char[] english = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
                  'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                  ',', '.', '?' };

    String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
                ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
                "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
                "-----", "--..--", ".-.-.-", "..--.." };

    //Scanner keyboard = new Scanner(System.in);
    public void test(){
        
    System.out.println(" This is an English to Morse Code Translator.  ");
    System.out.println(" Please enter what you would like translate ");
    System.out.println("             into Morse Code. ");
    System.out.println(" ============================================ ");

    //String userInput = keyboard.nextLine().toLowerCase();
    
    String userInput = "my name is jason".toLowerCase();

    char[] chars = userInput.toCharArray();

    String str = "";
    for (int i = 0; i < chars.length; i++){
        for (int j = 0; j < english.length; j++){

            if (english[j] == chars[i]){
                str = str + morse[j] + " ";  
            }
        }
    }
    System.out.println(str);
    }
    
    public String translateToMorse(String string){
        //move code here
        char[] chars = string.toCharArray();

    String str = "";
    for (int i = 0; i < chars.length; i++){
        for (int j = 0; j < english.length; j++){

            if (english[j] == chars[i]){
                str += morse[j] + " ";  
            }
        }
    }
        return str;
    }
    
    public String translateFromMorse(String string){
        
        return null;
    }
    
}
