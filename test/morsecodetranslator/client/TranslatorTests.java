/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator.client;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jason
 */
public class TranslatorTests {
    
    @Test
    public void testTranslateToMorse() {
        String string = "hello jason";
        Translator translator = new Translator();
        String result = translator.translateToMorse(string);
        assertEquals(".... . .-.. .-.. ---     .--- .- ... --- -. ", result);
    }
    
    @Test
    public void testTranslateFromMorse() {
        String string = ".... . .-.. .-.. ---     .--- .- ... --- -. ";
        Translator translator = new Translator();
        String result = translator.translateFromMorse(string);
        assertEquals("hello jason", result);
    }
}
