/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import tictactoe.Scenes.MainMenuBase;
import tictactoedb.TicTacToeDB;

/**
 *
 * @author LENOVO
 */
public class TwoPlayerGameController {
    
    private Cell[][] cells;
    private String mainPlayer;
    private Stage stage;
    private final Button mainMenuBtn;
    private final Button playAgainBtn;
    private final Label playerName1;
    private final Label playerName2;
    private final Label turnLabel;
    private final String name1;
    private final String name2;
    private final GridPane gridPane;
    private Label player1Score;
    private Label player2Score;
    private int score1 = 0;
    private int score2 = 0;
    private MainMenuBase mainMenuBase;
    
    private MediaPlayer clickSound;
    private MediaPlayer gameOver;
    
    private int step;
    private int[] rowArr;
    private int[] colArr;
    private String[] players;
    
    public TwoPlayerGameController(
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
            String name2) {
        
        this.mainMenuBtn = mainMenu;
        this.playAgainBtn = playAgain;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.turnLabel = turnLabel;
        this.name1 = name1;
        this.name2 = name2;
        this.gridPane = gridPane;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.mainPlayer = name1;
        
        step = 0;
        rowArr = new int[9];
        colArr = new int[9];
        players = new String[9];
        
        // Sounds
        clickSound = new MediaPlayer(
                new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        gameOver = new MediaPlayer(
                new Media(getClass().getResource("/sounds/013 - Victory.mp3").toExternalForm()));
        
        // Inititializing labels and cells
        updateScore();
        labelInit();
        cellsInit();
        
        playAgainBtn.setOnAction(e -> {
            cellsReset();
            gameOver.stop();
        });
        
        mainMenu.setOnAction(e -> {
            mainMenuBase = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenuBase, 636, 596);
            AnimationHelper.fadeAnimate(mainMenuBase);
            gameOver.stop();
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
    }
    
    public void labelInit() {
        if (!name1.equals("")) {
            playerName1.setText("Player 1: " + name1);
        }
        if (!name2.equals("")) {
            playerName2.setText("Player 2: " + name2);
        }
        if (name1.equals(""))
            playerName1.setText("Player1");
        if (name2.equals(""))
            playerName2.setText("Player2");  
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
        player1Score.setText("Score: " + score1);
        player2Score.setText("Score: " + score2);
    }
    
    public void cellsReset() {
        System.out.println("Resetcalled");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j].resetPlayer();
                cells[i][j].setStyle("");
                step = 0;
                rowArr = new int[9];
                colArr = new int[9];
                players = new String[9];
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
                l1 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                l2 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                getChildren().addAll(l1, l2);
                turnLabel.setText(name2 + " turn");   
            } else if(player.equals(name2)) {
                oShape = new Ellipse (this.getWidth() / 2, this.getHeight() / 2,
                this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                oShape.setFill(null);
                oShape.setStroke(Color.BLACK);
                getChildren().add(oShape);
                turnLabel.setText(name1 + " turn");
            }
        }
        
        public void resetPlayer() {
            if (player != null) {
                System.out.println("Resetplayercalled;");
                player = null;
                getChildren().removeAll(l1, l2, oShape);
                mainPlayer = name1;
            }
        }
        
        public void handleClick() {
            if(player == null && mainPlayer != null) {
                setPlayer(mainPlayer);
                
                // Setting the database variables
                rowArr[step] = this.i;
                colArr[step] = this.j;
                players[step] = new String(mainPlayer);
                step++;
                
                if (hasWon(mainPlayer)) {
                    turnLabel.setText(mainPlayer + " won!");
                    new TicTacToeDB(rowArr, colArr, players, mainPlayer);
                    mainPlayer = null;
                }
                else if (isBoardFull()){
                    mainPlayer = null;
                    turnLabel.setText("It's a draw!");
                    new TicTacToeDB(rowArr, colArr, players, "draw");

                } else {
                    mainPlayer = (mainPlayer == name1)? name2: name1;
                }
            } 
        }
    }
    
    public boolean isBoardFull() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (cells[i][j].getPlayer() == null){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasWon(String player) {
        final String winTile = "-fx-background-color: #adff2f;"
                + " -fx-opacity: 0.7;"
                + " -fx-border-color: black;"
                + " -fx-border-width: 1px;";
        
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[i][0].getPlayer())  && player.equals(cells[i][1].getPlayer()) && player.equals(cells[i][2].getPlayer())) {
                if(player.equals(name1)){
                    score1++;
                }
                else {
                    score2++;
                }
                cells[i][0].setStyle(winTile);
                cells[i][1].setStyle(winTile);
                cells[i][2].setStyle(winTile);
                gameOver.play();
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
                cells[0][i].setStyle(winTile);
                cells[1][i].setStyle(winTile);
                cells[2][i].setStyle(winTile);
                gameOver.play();
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
            cells[0][0].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][2].setStyle(winTile);
            gameOver.play();
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
            cells[0][2].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][0].setStyle(winTile);
            gameOver.play();
            updateScore();
            return true;
        }
        
        return false;

    }
    
}


