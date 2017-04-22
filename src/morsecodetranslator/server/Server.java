/*
Jason Hardwick
CIS 314
4/12/17
 */
package morsecodetranslator.server;

/**
 *
 * @author Jason
 */

import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Server extends JFrame{
       private JTextArea outputArea;
       private ServerSocket server; // server socket to connect with clients
       private ExecutorService runTranslator;
       private Condition otherUserConnected; 
       private Lock transLock;
       public ConnectedClient[] connectedClients = new ConnectedClient[2];
       public final static int client1 = 0;
       public final static int client2 = 1;
       
    public Server(){
        //create an executor service with a thread for each player
        runTranslator = Executors.newFixedThreadPool( 2 );
        transLock = new ReentrantLock(); // create lock
        
        //condition for both users to be connected
        otherUserConnected = transLock.newCondition();
        
        try {
            server = new ServerSocket(12345,2);
            
        } catch (IOException ioException){
            ioException.printStackTrace();
            System.exit(1);
        }
        
        outputArea = new JTextArea();//text area for output
        add(outputArea, BorderLayout.CENTER);
        outputArea.setText("Waiting for user\n \nJason Hardwick \nCIS314 \n4/12/17");
        
        setSize(300,300);
        setVisible(true);
            
        
    }//end constructor
    
    //wait for two users to begin.
    public void execute(){
        //wait for each client to connect
        for (int i = 0; i < connectedClients.length; i++){
            try { //wait for connection, create user, start runnable, 
                connectedClients[i] = new ConnectedClient(this, server.accept(), i);
                runTranslator.execute(connectedClients[i]);
            }//end try
            catch (IOException ioException){
                ioException.printStackTrace();
                System.exit(1);
            }//end catch
        }//end for
        
        transLock.lock();//lock chat to signal users thread
        
        try {
            connectedClients[client1].setSuspended(false);//resume user1
            otherUserConnected.signal();//wake up user1's thread
        }//end try
        finally {
            transLock.unlock();//unlock after signaling user 1
        }//end finally
    }//end method execute
    
    //display message in output area
    private void displayMessage(final String messageToDisplay){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        outputArea.append(messageToDisplay);//end messagetodisplay
                    }//end method run
                }//end inner class
        );//end call to swingutilities
        
    }//end method displayMessage

    public static void main(String[] args) {
        Server application = new Server();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.execute();
       
    }
       
       
}
