/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
            // Initializing the server socket
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            while(true) {
                // Connecting to player 1
                Socket firstPlayer = serverSocket.accept();
                // Sending a notification to first client that he's the first player
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                // Connecting to player 2
                Socket secondPlayer = serverSocket.accept();
                // Sending a notification that he's the second player
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);
                
                        
                // Sending both players to the game handler
                GameHandler gameHandler = new GameHandler(firstPlayer, secondPlayer);
//                Thread th = new Thread(gameHandler);
//                th.start();
            }
        } catch (IOException e) {
            System.out.println("Couldn't connect to clients");
        }
    }
    
    public static void main(String args[]) {
        System.out.println("Server is online");
        Server server = new Server();
    }
}
