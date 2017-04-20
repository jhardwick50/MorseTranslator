/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator.client;

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
        client.displayArea.append(text);
        client.chatArea.setText("");
        //translate message
        String morse = translator.translateToMorse(text);
        System.out.println(morse);
        //send translated message to server through client
    }
    
    public void recieveMessage(){
        //recieve encoded message
        //translate message
        //update chat window
    }
}
