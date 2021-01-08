/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT_NUMBER = 1234;
    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;
    private final int PLAYER1_WON = 1;
    private final int PLAYER2_WON = 2;
    private final int DRAW = 3;
    private final int CONTINUE = 4;
    
    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            while(true) {
                Socket s1 = serverSocket.accept();
                
                GameHandler gameHandler = new GameHandler(s1);
            }
        } catch (IOException e) {
            System.out.println("Server() constructor exception");
        }
    }
    
    public static void main(String args[]) {
        System.out.println("Listening to port 1234");
        Server server = new Server();
    }
}
