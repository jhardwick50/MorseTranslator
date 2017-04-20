/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Jason
 */
public class MessageSendListener implements KeyListener {
    
    private MessageService messageService;
    
    public MessageSendListener(MessageService messageService){
        this.messageService = messageService;
        
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //when key presses enter clear chat area and send message.  google key event code
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            messageService.sendMessage();
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
    
}
