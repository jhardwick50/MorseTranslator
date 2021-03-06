/*
Jason Hardwick
CIS 314
4/12/17
 */
package morsecodetranslator.client;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jason
 */



public class Client extends JFrame implements Runnable{
    public JTextArea displayArea;//area to display output
    private Socket connection;//connection to server
    private Scanner input;//input from server
    private PrintWriter output;//output to server
    private String morseHost;//host name
    public JTextArea chatArea;
    public MessageService messageService;
    
    //set up user interface
    public Client(String host){
        morseHost = host;//set name of server
        displayArea = new JTextArea(20,30);//set up JTextArea
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea),BorderLayout.NORTH);
        chatArea = new JTextArea(4,30);//area for input
        add(new JScrollPane(chatArea),BorderLayout.SOUTH);
        this.messageService = new MessageService(this);
        chatArea.addKeyListener(new MessageSendListener(messageService));
        
        
        setSize(500,500);
        setVisible(true);
        
    }//end constructor
    
    //start client thread
    public void startClient(){
        //connect to server and get streams
        try{
            //make connection to server
            connection = new Socket(InetAddress.getByName(morseHost),12345);
            
            System.out.println("connected");
            
            //get streams for input output
            input = new Scanner(connection.getInputStream());
            output = new PrintWriter(connection.getOutputStream());
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        //create and start worker thread for this client
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this);//execute client
    }//end method startClient
    
    public void sendMesage(String message) {
        String trimmedMessage = message.trim();
        System.out.println("Sending message to server: " + trimmedMessage);
        output.println(trimmedMessage);
        output.flush();
    }
    
    
    
    public static void main(String[] args) {
        Client application = new Client("127.0.0.1");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.startClient();
       
    }

    @Override
    public void run() {
       
        while (true){
            String message = input.nextLine();
            System.out.println("received message from server: " + message);

            messageService.processMessage(message);
        }    
        
        
    }
   
} // End class Client
    

