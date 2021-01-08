/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class GameHandler extends Thread {
    private DataInputStream inputStream;
    private PrintStream printStream;
    private static Vector<GameHandler> games = new Vector<GameHandler>();
    private static Vector<String> names = new Vector<String>();
    
    public GameHandler(Socket s1) {
        try {
            System.out.println("Creating a game session");
            inputStream = new DataInputStream(s1.getInputStream());
            printStream = new PrintStream(s1.getOutputStream());
            games.add(this);
            start();
        } catch (IOException ex) {
            System.out.println("Server is down...");
        }
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                String messageFromClient = inputStream.readLine();
                System.out.println(messageFromClient);
                if(messageFromClient.split("-").length == 1 && messageFromClient != null) {
                    String name = messageFromClient;
                    names.add(name);
                } else {
                    if(messageFromClient != null) {
                        sendMove(messageFromClient);
                    } else {
                        System.out.println("Lost connection to client1 -- gameover");
                        try {
                            inputStream.close();
                            printStream.close();
                        } catch (IOException e) {
                            System.out.println("Couldn't close streams");
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Lost connection to client2");
                games.remove(this);
                break;
            }
        }
    }
    
    private void sendMove(String s) {
//        games.forEach(client -> {
//            client.printStream.println(client.name + "-" + s);          
//        });
        String[] ar = new String[2];
        names.toArray(ar);
        System.out.println(ar);
        System.out.println(ar[0]);
        System.out.println(ar[1]);
         if(ar[0] != null && ar[1] != null) {
            games.forEach(client -> {
                System.out.println("here");
                client.printStream.println(ar[0] +"-"+ar[1] + "-" + s + "-" + ar[0]);          
            });
        }
    }
}
