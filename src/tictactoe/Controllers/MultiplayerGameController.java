/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Integer.parseInt;
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
    
    private Cell[][] cells;
    private String currentPlayer;
    private Stage stage;
    private final Button mainMenuBtn;
    private final Button playAgainBtn;
    private final Label playerName1;
    private final Label playerName2;
    private final Label turnLabel;
    private String name1;
    private String name2;
    private final GridPane gridPane;
    private Label player1Score;
    private Label player2Score;
    private int score1 = 0;
    private int score2 = 0;
    private MainMenuBase mainMenuBase;
    private DataInputStream inputStream;
    private Socket s;
    private PrintStream printStream;
    private Thread th;
    
    private int clientNum;
        
    
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
            Socket s,
            DataInputStream inputStream,
            PrintStream printStream) {
        
        // I still also need input and output streams and socket from the previous controller

        
        mainMenuBtn = mainMenu;
        playAgainBtn = playAgain;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.turnLabel = turnLabel;
        this.name1 = name1;
        this.s = s;
        this.printStream = printStream;
        this.inputStream = inputStream;
        
        //this.name2 = null; // Recieve from the server, how IDK
        
        this.gridPane = gridPane;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        
        currentPlayer = name1;
        
        th = new Thread(this);
        th.start();
        // Inititializing labels and cells
        updateScore();
        labelInit();
        cellsInit();
        
        playAgainBtn.setOnAction(e -> {
            cellsReset();
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
        
    }

    @Override
    public void run(){
        while (true) {
            try {
                System.out.println("oooooooo");
                String message = inputStream.readLine();
                System.out.println("Message recieved from server = " + message);

                String[] ar = message.split("-");
                if(name1.equals(ar[0])) {
                    name2 = ar[1];
                    clientNum = 1;
                } else {
                    name1 = ar[0];
                    name2 = ar[1];
                    clientNum = 2;
                    player2Score = player1Score;
                }
                int i = parseInt(ar[2]);
                int j = parseInt(ar[3]);
                currentPlayer = ar[4];
                if(!ar[2].equals("8")) {
                    cells[i][j].setPlayer(currentPlayer);
                }
                ////////////////////////////
                if (hasWon(currentPlayer)) {
                    Platform.runLater(() ->{
                        turnLabel.setText(currentPlayer + " WON!");
//                        try {
//                            s.close();
//                        } catch (IOException e) {
//                            System.out.println("couldn't close socket");
//                        }
                    });
                    //printStream.println("8-8-" + currentPlayer);
                    currentPlayer = null;
                }
                else if (isBoardFull()){
                    currentPlayer = null;
                    //printStream.println("8-8-" + currentPlayer);
                    Platform.runLater(()-> {
                        turnLabel.setText("It's a draw!");
//                        try {
//                            s.close();
//                        } catch (IOException e) {
//                            System.out.println("couldn't close socket");
//                        }
                    });

                } else {
                    currentPlayer = (currentPlayer.equals(name1))? name2: name1;
                }
                /////////////////////////////
                System.out.println(name2);
            for(int k = 0; k < 3; k++) {
                for(int l = 0; l < 3; l++) {
                    System.out.println(cells[k][l].player);
                    
                }
            }
                
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Lost connection to server");
                try{
                    inputStream.close();
                    printStream.close();
                    s.close();
                    break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    public void labelInit() {
        playerName1.setText("Player 1: " + name1);
        playerName2.setText("Player 2");
 
    }
    
    public void cellsInit() {
        cells = new Cell[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
                gridPane.add(cells[i][j], j, i);
                cells[i][j].setI(i);
                cells[i][j].setJ(j);
            }
        }
    }
    
    public void updateScore() {
        Platform.runLater(() -> {
                player1Score.setText("Score: " + score1);
                player2Score.setText("Score: " + score2);
        });
    }
    
    public void cellsReset() {
        System.out.println("Resetcalled");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j].resetPlayer();
            }
        }
    }
    
    
    public class Cell extends Pane {
        private String player = null;  
        public int i;
        public int j;
        private Line l1;
        private Line l2;
        private Ellipse oShape;
        
        public Cell() {
            this.setPrefSize(300, 300);
            this.setOnMouseClicked(e -> handleClick());
        }
        
        public String getPlayer() {
            return player;
        }
        
        public void setI(int n) {
            i = n;
        }
        
        public void setJ(int n) {
            j = n;
        }
        
        public void setPlayer(String p) {
            player = p;

            if(player.equals(name1)) {
                Platform.runLater(() -> {
                l1 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                l2 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                getChildren().addAll(l1, l2);
                turnLabel.setText(name2 +" turn");
                });
                
                
            } else if(player.equals(name2)) {
                Platform.runLater(() -> {
                Ellipse oShape = new Ellipse (this.getWidth() / 2, this.getHeight() / 2,
                this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                oShape.setFill(null);
                oShape.setStroke(Color.BLACK);
                //printStream.println(i + " " + j + " " + player);
                getChildren().add(oShape);
                turnLabel.setText(name1 + " turn");
                });

            }
        }
        
        public void resetPlayer() {
            System.out.println(name1);
            System.out.println(name2);
            if (player.equals(name1)) {
                System.out.println("Resetplayercalled;");
                player = null;
                getChildren().removeAll(l1, l2);
            }
            if (player.equals(name2)) {
                System.out.println("Resetplayercalled;");
                player = null;
                getChildren().removeAll(oShape);
            }
            currentPlayer = name1;
        }
        
        public void handleClick() {
            if(player == null && currentPlayer != null) {
                String message = String.valueOf(i) + "-" + String.valueOf(j) + "-" + currentPlayer;
                System.out.println("Message sent to server = " + message);
                printStream.println(message);
            }
        }
    }
    
    public boolean isBoardFull() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (cells[i][j].getPlayer() == null){
                    
                    return false;}
            }
        }
        return true;
    }
    
    public boolean hasWon(String player) {
        System.out.println("Checking hasWon");
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[i][0].getPlayer())  && player.equals(cells[i][1].getPlayer()) && player.equals(cells[i][2].getPlayer())) {
                if(player.equals(name1)){
                    score1++;
                }
                else {
                    score2++;
                }
                updateScore();
                return true;
            }
        }
        
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[0][i].getPlayer()) && player.equals(cells[1][i].getPlayer()) && player.equals(cells[2][i].getPlayer())) {
                if(player.equals(name1)){
                    score1++;
                }
                else {
                    score2++;
                }
                updateScore();
                return true;
            }
        }
        
        if (player.equals(cells[0][0].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][2].getPlayer())) {
            if(player.equals(name1)){
                    score1++;
                }
            else {
                score2++;
            }
            updateScore();
            return true;
        }
        
        if (player.equals(cells[0][2].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][0].getPlayer())) {
            if(player.equals(name1)){
                    score1++;
                }
            else {
                score2++;
            }
            updateScore();
            return true;
        }
        
        return false;
    }
    
}


