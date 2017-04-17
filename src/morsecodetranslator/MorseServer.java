/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecodetranslator;

/**
 *
 * @author Jason
 */

import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
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

public class MorseServer extends JFrame{
       private JTextArea outputArea;
       private ServerSocket server; // server socket to connect with clients
       private ExecutorService runTranslator;
       private Condition otherUserConnected; 
       private Lock transLock;
       private User[] users;
       private final static int user1 = 0;
       private final static int user2 = 1;
       
    public morseServer(){
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
        outputArea.setText("Waiting for user");
        
        setSize(300,300);
        setVisible(true);
            
        
    }//end constructor
    
    //wait for two users to begin.
    public void execute(){
        //wait for each client to connect
        for (int i = 0; i < users.length; i++){
            try { //wait for connection, create user, start runnable, 
                users[i] = new User(server.accept(), i);
                runTranslator.execute(users[i]);
            }//end try
            catch (IOException ioException){
                ioException.printStackTrace();
                System.exit(1);
            }//end catch
        }//end for
        
        transLock.lock();//lock chat to signal users thread
        
        try {
            users[user1].setSuspended(false);//resume user1
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
    //inner class user manages users as runnables
    private class User implements Runnable {
        private Socket connection;//connection to client
        private Scanner input;//input from client
        private Formatter output; // output to client
        private int userNumber;//keeps track of user
        private boolean suspended = true; //finds out if thread is suspended
        
        //set up user thread
        public User(Socket socket, int number){
            userNumber = number;//store users number
            connection = socket;//store socket for client
            
            //
            try { //obtain streams from socket
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
                
            }//end try
            
            catch(IOException ioException){
                ioException.printStackTrace();
                System.exit(1);
            }//end catch
        }//end user constructor
        
        //control threads execution
        public void run(){
            //process messages from client
            
        }
    }
       
       
}
