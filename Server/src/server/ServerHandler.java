/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler implements Runnable {
    private final int PORT_NUMBER = 1234;
    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;
    private final int PLAYER1_WON = 1;
    private final int PLAYER2_WON = 2;
    private final int DRAW = 3;
    private final int CONTINUE = 4;
    
    public ServerHandler() {
//        try {
//            // Initializing the server socket
//            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
//            while(true) {
//                // Connecting to player 1
//                Socket firstPlayer = serverSocket.accept();
//                // Sending a notification to first client that he's the first player
//                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);
//                String name1 = new DataInputStream(firstPlayer.getInputStream()).readUTF();
//                System.out.println(name1);
//
//                // Connecting to player 2
//                Socket secondPlayer = serverSocket.accept();
//                // Sending a notification that he's the second player
//                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);
//                String name2 = new DataInputStream(secondPlayer.getInputStream()).readUTF();
//                System.out.println(name2);
//                
//                new DataOutputStream(firstPlayer.getOutputStream()).writeUTF(name2);
//                firstPlayer.getOutputStream().flush();
//                new DataOutputStream(secondPlayer.getOutputStream()).writeUTF(name1);
//                secondPlayer.getOutputStream().flush();
//                        
//                // Sending both players to the game handler
//                new GameHandler(firstPlayer, secondPlayer, name1, name2);
//            }
//        } catch (IOException e) {
//            System.out.println("One of the clients disconnected");
//        } 
    }
    
    @Override
    public void run() {
        while(true){
            try {
                // Initializing the server socket
                ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
                while(true) {
                    // Connecting to player 1
                    Socket firstPlayer = serverSocket.accept();
                    // Sending a notification to first client that he's the first player
                    new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);
                    String name1 = new DataInputStream(firstPlayer.getInputStream()).readUTF();

                    // Connecting to player 2
                    Socket secondPlayer = serverSocket.accept();
                    // Sending a notification that he's the second player
                    new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);
                    String name2 = new DataInputStream(secondPlayer.getInputStream()).readUTF();


                    new DataOutputStream(firstPlayer.getOutputStream()).writeUTF(name2);
                    firstPlayer.getOutputStream().flush();
                    new DataOutputStream(secondPlayer.getOutputStream()).writeUTF(name1);
                    secondPlayer.getOutputStream().flush();

                    // Sending both players to the game handler
                    new GameHandler(firstPlayer, secondPlayer, name1, name2);
                }
            } catch (IOException e) {
                System.out.println("One of the clients disconnected");     
                break;
            } 
        }
    }

}
