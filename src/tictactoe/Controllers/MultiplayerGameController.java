/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.MultiplayerGameBase;

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
    private final int OTHER_PLAYER_DISCONNECTED = 5;
    
    private String currentPlayer;
    private Stage stage;
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
    
    private Socket socket;
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
    
    
    
    //private int myNum;
  
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
        
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.turnLabel = turnLabel;
        this.myName = name1;
        this.s = s;      
        this.gridPane = gridPane;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.stage = primaryStage;
        
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cell[i][j] = new Cell(i, j);
                this.gridPane.add(cell[i][j], j, i);
            }
        }
        
        
        playAgain.setOnAction(e -> {
            try {
                socket.close();
                MultiplayerGameBase multiGame = new MultiplayerGameBase(primaryStage, myName, s);
                Scene scene = new Scene(multiGame, 636, 596);
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        mainMenu.setOnAction(e -> {
            try{
                socket.close();
                mainMenuBase = new MainMenuBase(primaryStage);
                Scene scene = new Scene(mainMenuBase, 636, 596);
                KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(mainMenuBase.opacityProperty(), 0));
                KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(mainMenuBase.opacityProperty(), 1));
                Timeline fade = new Timeline(start, end);
                fade.play();
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
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
            
            // Sending name to server
            toServer.writeUTF(myName);
            toServer.flush();
            
            Platform.runLater(() -> {
                    turnLabel.setText("Waiting for other player to join");
            });
            
            otherName = fromServer.readUTF();
            
            Platform.runLater(() -> {
                    playerName1.setText(myName);
                    playerName2.setText(otherName);
            });

            if(player == PLAYER1) {
                myToken = 'X';
                otherToken = 'O';
                
                // Changing the status of the turn label
                Platform.runLater(() -> {
                    turnLabel.setText(otherName + " joined. It's your turn");
                });
                
                myTurn = true;
            }
            else if (player == PLAYER2) {
                myToken = 'O';
                otherToken = 'X';
               
                // Changing the status of the turn label
                Platform.runLater(() -> {
                    turnLabel.setText("Please wait for " + otherName + " to move");
                });
            }
            
            // Game start
            while(continueToPlay)   {
                if(player == PLAYER1) {
                    waitForAction(); // Waiting to make a move
                    sendMove(); // Sending the move to the server
                    recieveStatus(); // Recieving board status from the server
                }
                else if (player == PLAYER2) {
                    recieveStatus();
                    waitForAction();
                    sendMove();
                }
            }
            
        } catch (IOException e) {
            System.out.println("Lost connection to server");
            //Platform.runLater(() -> lostConnectionToServer());
        }
    }
    
    private void lostConnectionToServer () {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Server is currently down");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("There seems to be a problem with the server!");
        ButtonType mainMenu = new ButtonType("Main Menu");
        alert.getButtonTypes().setAll(mainMenu);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == mainMenu) {
            mainMenuBase = new MainMenuBase(stage);
            Scene scene = new Scene(mainMenuBase, 636, 596);
            KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(mainMenuBase.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(mainMenuBase.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
            stage.setScene(scene);
            }
    }
    
    /**
    * Puts the current thread into sleep whiling waiting for the player
    * to make a move
    * It also changes status to "waiting" after the thread activates
    * @return nothing
    * @exception throws an InterruptedException but is handled on the spot
    */
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
    
    /**
    * Sends the selected player row and column to the server
    * @return nothing
    * @exception throws an IOException but is handled on the spot
    */
    
    private void sendMove() {
        try {
            toServer.writeInt(rowSent);
            toServer.writeInt(columnSent);
        } catch (IOException e) {
            System.out.println("Lost connection to server");
            //Platform.runLater(() -> lostConnectionToServer());
        }
    }
    
    /**
    * Receives the current board status from the virtual 
    * board on the server side to declare win/lose/draw
    * 
    * If no win/lose/draw situation it switch gives the turn to 
    * the other player
    * @return nothing
    * @exception throws an Interrupted exception but is handled on the spot
    */
    private void recieveStatus() {
        try {
            int status = fromServer.readInt();
            
            if(status == PLAYER1_WON) {
                continueToPlay = false;
                if(myToken == 'X') {
                    // Do something in UI indicating I won ///////// TODO  ++++++ Still need to update scores
                    // Changing the status of the turn label
                    Platform.runLater(() -> {
                        turnLabel.setText("CONGRATULATIONS YOU WON!");
                    });
                } 
                else if (myToken == 'O') {
                    // Do something in UI indication loss ///////////// TODO
                    // Changing the status of the turn label
                    Platform.runLater(() -> {
                        turnLabel.setText("You lost :(");
                    });
                    recieveMove();
                }
            }
            
            else if (status == PLAYER2_WON) {
                continueToPlay = false;
                if (myToken == 'X') {
                    // Do something indicating loss
                    Platform.runLater(() -> {
                        turnLabel.setText("You lost :(");
                    });
                    recieveMove();
                }
                else if(myToken == 'O') {
                    // Do something indicating win
                    Platform.runLater(() -> {
                        turnLabel.setText("CONGRATULATIONS YOU WON!");
                    });
                }
            }
            
            else if (status == DRAW) {
                continueToPlay = false;
                // Do something in UI indicating draw
                Platform.runLater(() -> {
                        turnLabel.setText("Its a draw");
                });
                
                if (myToken == 'O') {
                    recieveMove();
                }
            }
            
            else if (status == OTHER_PLAYER_DISCONNECTED) {
                continueToPlay = false;
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Connection lost");
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.setContentText("The other player has left the game!");
                    ButtonType mainMenu = new ButtonType("Main Menu");
                    alert.getButtonTypes().setAll(mainMenu);
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if (result.get() == mainMenu) {
                        mainMenuBase = new MainMenuBase(stage);
                        Scene scene = new Scene(mainMenuBase, 636, 596);
                        KeyFrame start = new KeyFrame(Duration.ZERO,
                            new KeyValue(mainMenuBase.opacityProperty(), 0));
                        KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                                new KeyValue(mainMenuBase.opacityProperty(), 1));
                        Timeline fade = new Timeline(start, end);
                        fade.play();
                        stage.setScene(scene);
                    }
                });
            }
            
            else {
                recieveMove();
                // Do something to indicate this is my turn
                Platform.runLater(() -> {
                        turnLabel.setText("Your turn to play");
                });
                myTurn = true;
            }
        } catch (IOException e) {
            System.out.println("Lost Connection to server");
            Platform.runLater(() -> lostConnectionToServer());
        }
    }
    
    private void recieveMove() {
        try {
            int row = fromServer.readInt();
            int col = fromServer.readInt();
            cell[row][col].setPlayer(otherToken);
        } catch (IOException e) {
            System.out.println("Lost connection to server");
            Platform.runLater(() -> lostConnectionToServer());
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
                });
                
                
            } else if(player == 'O') {
                Platform.runLater(() -> {
                    oShape = new Ellipse (this.getWidth() / 2, this.getHeight() / 2,
                    this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                    oShape.setFill(null);
                    oShape.setStroke(Color.BLACK);
                    getChildren().add(oShape);
                });

            }
        }
        
        public void resetPlayer() {
            if (player == 'X') {
                getChildren().removeAll(l1, l2);
            }
            if (player == 'O') {
                getChildren().removeAll(oShape);
            }
            player = ' ';
        }
        
        public void handleClick() {
            if(player == ' ' && myTurn) {
                Platform.runLater(() -> {
                    turnLabel.setText("Wait for other player");
                });
                setPlayer(myToken);
                myTurn = false;
                rowSent = row;
                columnSent = col;
                waiting = false;
            }
        }
    }    
}


