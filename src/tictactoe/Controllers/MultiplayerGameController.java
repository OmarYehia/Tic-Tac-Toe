/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Integer.parseInt;
import java.net.InetAddress;
import java.net.Socket;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoe.Scenes.MainMenuBase;

/**
 *
 * @author LENOVO
 */
public class MultiplayerGameController implements Runnable {
    
    private final int PORT_NUMBER = 1234;
    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;
    private final int PLAYER1_WON = 1;
    private final int PLAYER2_WON = 2;
    private final int DRAW = 3;
    private final int CONTINUE = 4;
    
    private String currentPlayer;
    private Stage stage;
    private final Button mainMenuBtn;
    private final Button playAgainBtn;
    private final Label playerName1;
    private final Label playerName2;
    private final Label turnLabel;
    private final GridPane gridPane;
    private Label player1Score;
    private Label player2Score;
    private int score1 = 0;
    private int score2 = 0;
    private MainMenuBase mainMenuBase;
    private Socket s;
    private Thread th;
    
    Socket socket;
    private boolean myTurn = false;
    private String myName;
    private String otherName;
    private char myToken = ' ', otherToken = ' ';
    private Cell[][] cell = new Cell[3][3];
    
    // Moves
    private int rowSent;
    private int columnSent;

    // Streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    
    private boolean continueToPlay = true;
    private boolean waiting = true;
    
    private int myNum;

    
    
        
    
    public MultiplayerGameController(
            Stage primaryStage,
            Button mainMenu,
            Button playAgain,
            GridPane gridPane,
            Label playerName1,
            Label playerName2,
            Label turnLabel,
            Label player1Score,
            Label player2Score,
            String name1,
            Socket s) {
        
        mainMenuBtn = mainMenu;
        playAgainBtn = playAgain;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.turnLabel = turnLabel;
        this.myName = name1;
        this.s = s;      
        this.gridPane = gridPane;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        
        
        // Inititializing labels and cells /////////////////////////////////////////////////////////////////////////
//        updateScore();
//        labelInit();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cell[i][j] = new Cell(i, j);
                this.gridPane.add(cell[i][j], j, i);
            }
        }
        
        
        playAgainBtn.setOnAction(e -> {
//            cellsReset();
        });
        
        mainMenu.setOnAction(e -> {
            mainMenuBase = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenuBase, 636, 596);
            KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(mainMenuBase.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(mainMenuBase.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
            primaryStage.setScene(scene);
        });
        
        connectToServer();
        
    }
    
    private void connectToServer() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), PORT_NUMBER);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Server is currently unavailable");
        }
        
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run(){
        try {
            // Recieving which player I am from the server
            int player = fromServer.readInt();

            if(player == PLAYER1) {
                myTurn = true;
                myToken = 'X';
                otherToken = 'O';
                System.out.println("1");
                fromServer.readInt();
                System.out.println("2");


                // Do something to UI   ////// TODO
            }
            else if (player == PLAYER2) {
                myToken = 'O';
                otherToken = 'X';
               // Do something to UI   /////// TODO
            }
            
            // Game start
            while(continueToPlay)   {
                if(player == PLAYER1) {
                                    System.out.println("3");

                    waitForAction();
                                    System.out.println("4");

                    sendMove();
                                    System.out.println("5");

                    recieveStatus();
                                    System.out.println("6");

                }
                else if (player == PLAYER2) {
                    recieveStatus();
                    waitForAction();
                    sendMove();
                }
            }
            
        } catch (IOException e) {
            System.out.println("Lost connection to server");
        }
    }
    
    private void waitForAction() {
        try {
            while(waiting) {
                Thread.sleep(100);
            }
            waiting = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void sendMove() {
        try {
            toServer.writeInt(rowSent);
            toServer.writeInt(columnSent);
        } catch (IOException e) {
            System.out.println("Lost connection to server");
        }
    }
    
    private void recieveStatus() {
        try {
            int status = fromServer.readInt();
            
            if(status == PLAYER1_WON) {
                continueToPlay = false;
                if(myToken == 'X') {
                    // Do something in UI indicating I won
                } 
                else if (myToken == 'O') {
                    // Do something in UI indication loss
                    recieveMove();
                }
            }
            
            else if (status == PLAYER2_WON) {
                continueToPlay = false;
                if (myToken == 'X') {
                    // Do something indicating loss
                    recieveMove();
                }
                else if(myToken == 'O') {
                    // Do something indicating win
                }
            }
            
            else if (status == DRAW) {
                continueToPlay = false;
                // Do something in UI indicating draw
                
                if (myToken == 'O') {
                    recieveMove();
                }
            }
            
            else {
                recieveMove();
                // Do something to indicate this is my turn
                myTurn = true;
            }
        } catch (IOException e) {
            System.out.println("Lost Connection to server");
        }
    }
    
    private void recieveMove() {
        try {
            int row = fromServer.readInt();
            int col = fromServer.readInt();
            cell[row][col].setPlayer(otherToken);
        } catch (IOException e) {
            System.out.println("Lost connection to server");
        }
    }
    
    
    public class Cell extends Pane {
        private char player = ' ';  
        public int row;
        public int col;
        private Line l1;
        private Line l2;
        private Ellipse oShape;
        
        public Cell(int r, int c) {
            row = r;
            col = c;
            //this.setPrefSize(300, 300);
            this.setOnMouseClicked(e -> handleClick());
        }
        
        
        public void setPlayer(char token) {
            player = token;

            if(player == 'X') {
                Platform.runLater(() -> {
                    l1 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                    l2 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                    getChildren().addAll(l1, l2);
                    //turnLabel.setText(myName +" turn");
                });
                
                
            } else if(player == 'O') {
                Platform.runLater(() -> {
                    Ellipse oShape = new Ellipse (this.getWidth() / 2, this.getHeight() / 2,
                    this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                    oShape.setFill(null);
                    oShape.setStroke(Color.BLACK);
                    getChildren().add(oShape);
                    //turnLabel.setText(otherName + " turn");
                });

            }
        }
        
//        public void resetPlayer() {
//            System.out.println(name1);
//            System.out.println(name2);
//            if (player.equals(name1)) {
//                System.out.println("Resetplayercalled;");
//                player = null;
//                getChildren().removeAll(l1, l2);
//            }
//            if (player.equals(name2)) {
//                System.out.println("Resetplayercalled;");
//                player = null;
//                getChildren().removeAll(oShape);
//            }
//            currentPlayer = name1;
//        }
        
        public void handleClick() {
            if(player == ' ' && myTurn) {
                System.out.println("clicked");
                setPlayer(myToken);
                myTurn = false;
                rowSent = row;
                columnSent = col;
                System.out.println("Move made. Waiting for other player to play");
                waiting = false;

            }
        }
    }    
}


