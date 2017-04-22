/*
Jason Hardwick
CIS 314
4/12/17
 */
package morsecodetranslator.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Jason
 */
public class ConnectedClient implements Runnable {
    private Server server;
    private Socket connection;//connection to client
    private Scanner input;//input from client
    private PrintWriter output; // output to client
    private int clientNumber;//keeps track of user
    private boolean suspended = true; //finds out if thread is suspended

    //set up user thread
    public ConnectedClient(Server server, Socket socket, int number){
        this.server = server;
        clientNumber = number;//store users number
        connection = socket;//store socket for client

        //
        try { //obtain streams from socket
            input = new Scanner(connection.getInputStream());
            output = new PrintWriter(connection.getOutputStream());

        }//end try

        catch(IOException ioException){
            ioException.printStackTrace();
            System.exit(1);
        }//end catch
    }//end user constructor

    //control threads execution
    public void run(){
        //process messages from client
        while(true) {
            if (input.hasNextLine()) {
                String message = input.nextLine();
                System.out.println("Received message from client" + clientNumber + ": " + message);

                if (clientNumber == Server.client1){
                    //send to client2
                    server.connectedClients[Server.client2].sendMessage(message);
                }
                else {
                    //send to client1
                    server.connectedClients[Server.client1].sendMessage(message);
                }
            }
        }
    }

    public void sendMessage(String message){
        System.out.println("Sending message to client" + clientNumber + ": " + message);
        output.println(message);
        output.flush();


    }

    public void setSuspended(boolean status) {
        suspended = status;
    }

}
    
