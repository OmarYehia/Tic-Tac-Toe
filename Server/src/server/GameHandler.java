/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import tictactoedb.TicTacToeDB;

public class GameHandler implements Runnable {
    
    private final int PLAYER1_WON = 1;
    private final int PLAYER2_WON = 2;
    private final int DRAW = 3;
    private final int CONTINUE = 4;
    private final int OTHER_PLAYER_DISCONNECTED = 5;
    
    private final Socket firstPlayer;
    private final Socket secondPlayer;
    private final String firstPlayerName;
    private final String secondPlayerName;
    
    private int step;
    private int[] rowArr;
    private int[] colArr;
    private String[] players;
    
    
    private char[][] cell = new char[3][3];
    
    Thread th;
    
    private boolean connection = true;

    
    public GameHandler(Socket s1, Socket s2, String name1, String name2) {
        firstPlayer = s1;
        secondPlayer = s2;
        firstPlayerName = name1;
        secondPlayerName = name2;
        
        step = 0;
        rowArr = new int[9];
        colArr = new int[9];
        players = new String[9];
        
        th = new Thread(this);
        th.start();
        
        // Initializing virtual board cells with empty tokens
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++)  {
                cell[i][j] = ' ';
            }
        }
    }
    
    @Override
    public void run() {
        //while(connection) {
            try {
                // Streams for first player
                DataInputStream fromFirstPlayer = new DataInputStream(firstPlayer.getInputStream());
                DataOutputStream toFirstPlayer = new DataOutputStream(firstPlayer.getOutputStream());
                
                // Streams for second player
                DataInputStream fromSecondPlayer = new DataInputStream(secondPlayer.getInputStream());
                DataOutputStream toSecondPlayer = new DataOutputStream(secondPlayer.getOutputStream());
                
                // Notify first player that someone joined the game
                //toFirstPlayer.writeInt(1);
                
                // Starting the game
                while(connection){
                    try{
                        
                        int row = fromFirstPlayer.readInt();
                        int col = fromFirstPlayer.readInt();

                        cell[row][col] = 'X';
                        rowArr[step] = row;
                        colArr[step] = col;
                        players[step] = new String(firstPlayerName);
                        step++;
                    
                        // Checking if first player has won
                        if(hasWon('X')) {
                            toFirstPlayer.writeInt(PLAYER1_WON);
                            toSecondPlayer.writeInt(PLAYER1_WON);
                            sendMove(toSecondPlayer, row, col);
                            
                            new TicTacToeDB(rowArr, colArr, players, firstPlayerName);
                            
                            break;
                        } 
                        /* Checking if the first is already full
                           This is only checked for first player because only
                           his last move could result in a draw
                        */
                        else if(isBoardFull()) {
                            toFirstPlayer.writeInt(DRAW);
                            toSecondPlayer.writeInt(DRAW);
                            sendMove(toSecondPlayer, row, col);
                            
                            new TicTacToeDB(rowArr, colArr, players, "draw");
                            
                            break;
                        }
                        /* Since first player didn't win or the board isn't full
                           we notify other player that it's their turn to play
                        */
                        else {
                            try{
                                toSecondPlayer.writeInt(CONTINUE);
                            } catch (IOException p2) {
                                System.out.println("Lost connection to second player");
                                toFirstPlayer.writeInt(OTHER_PLAYER_DISCONNECTED);
                            }
                            sendMove(toSecondPlayer, row, col);
                        }
                    } catch (IOException player1) {
                        System.out.println("Lost connection to first player");
                        //toFirstPlayer.writeInt(OTHER_PLAYER_DISCONNECTED);
                        toSecondPlayer.writeInt(OTHER_PLAYER_DISCONNECTED);
                        connection = false;
                    }
                    
                    try{
                        // Now we wait for the second player to make their move
                        int row = fromSecondPlayer.readInt();
                        int col = fromSecondPlayer.readInt();
                        
                        cell[row][col] = 'O';
                        rowArr[step] = row;
                        colArr[step] = col;
                        players[step] = new String(secondPlayerName);
                        step++;

                        /* Like the first player we check if second player won with
                           the move they just made
                        */
                        if(hasWon('O')) {
                            toFirstPlayer.writeInt(PLAYER2_WON);
                            toSecondPlayer.writeInt(PLAYER2_WON);
                            sendMove(toFirstPlayer, row, col);
                            
                            new TicTacToeDB(rowArr, colArr, players, secondPlayerName);
                            
                            break;
                        }
                        /* If player 2 didn't win we notify first player that it's
                           now their turn to play
                        */
                        else {
                            try {
                                toFirstPlayer.writeInt(CONTINUE);
                            } catch (IOException p2) {
                                System.out.println("First player disconnected");
                                toSecondPlayer.writeInt(OTHER_PLAYER_DISCONNECTED);
                            }
                            sendMove(toFirstPlayer, row, col);
                        }
                    } catch (IOException player2) {
                        System.out.println("Lost connection to second player");
                        toFirstPlayer.writeInt(OTHER_PLAYER_DISCONNECTED);
                        connection = false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Lost connection to players"); 
                //break;
                //e.printStackTrace();
            }
        //}
    }
    
    /**
    * Sends the coordinates of the move to a selected player
    * The combination between row and column is the other player 
    * selected cell
    * 
    * @param toPlayer the DataOutputStream object for the selected player
    * @param row an integer indicating the row
    * @param col and integer indicating the column
    * @exception It throws IOException if it couldn't send move to a client
    * but it's handled on the spot rather than propagating to the calling function
    * @return nothing
    */
    private void sendMove(DataOutputStream toPlayer, int row, int col) {
        try {
            toPlayer.writeInt(row);
            toPlayer.writeInt(col);
        } catch (IOException e) {
            System.out.println("Lost connection to player");
        }
    }
    
    /**
    * Checks whether the board is full or not after a player made their move
    * Takes no arguments
    * @return true if the board is full or false if the board is not full
    */
    
    private boolean isBoardFull() {
    for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (cell[i][j] == ' '){
                    return false;}
            }
        }
        return true;
    }
    
    /**
    * Checks whether the play the user just made would declare him a winner
    * @param token is a character 'X' or 'O' to check the virtual board state
    * @return true if that token made a winning move or false if not
    */
    private boolean hasWon(char token) {
        // Checking coloumns
        for(int i = 0; i < 3; i++) {
            if ((cell[i][0] == token) && (cell[i][1] == token) && (cell[i][2] == token)) {
                return true;
            }
        }
        // Checking rows
        for(int i = 0; i < 3; i++) {
            if ((cell[0][i] == token) && (cell[1][i] == token) && (cell[2][i] == token)) {
                return true;
            }
        }
        // Checking first diagonal
        if ((cell[0][0] == token) && (cell[1][1] == token) && (cell[2][2] == token)) {
            return true;
        }
        // Checking second diagonal
        if ((cell[0][2] == token) && (cell[1][1] == token) && (cell[2][0] == token)) {
            return true;
        }
        return false;
    }
}
    

