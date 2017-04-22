/*
Jason Hardwick
CIS 314
4/12/17
 */
package morsecodetranslator.client;

import javax.swing.SwingUtilities;

/**
 *
 * @author Jason
 */
public class MessageService {
    public Client client;
    public Translator translator;
    
    public MessageService(Client client){
        this.client = client;
        this.translator = new Translator();
        
        
    }
    
    public void sendMessage(){
        String text = client.chatArea.getText();
        
        //update chat window
        client.chatArea.setText("");
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                client.displayArea.append("\nMe: " + text);
            }
        });
        //translate message
        String morse = translator.translateToMorse(text);
        
        //send translated message to server through client
        client.sendMesage(morse);
    }
    
    public void processMessage(String message){
        //translate message
        String english = translator.translateFromMorse(message);
        //update chat window
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                client.displayArea.append("Them: " + message + "\n              " + english);
            }
        });
        
    }
}
