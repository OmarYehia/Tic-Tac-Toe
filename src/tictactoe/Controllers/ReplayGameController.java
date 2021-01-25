/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoe.Scenes.ReplayMenuBase;

/**
 *
 * @author LENOVO
 */
public class ReplayGameController implements Runnable{
    
    private Cell[][] cells;
    private String mainPlayer;
    private Stage stage;
    private final Button mainMenuBtn;
    private final Button playAgainBtn;
    private final Label playerName1;
    private final Label playerName2;
    private final Label turnLabel;
    private final AnchorPane videoPane;
    private String name1;
    private String name2;
    private final GridPane gridPane;
    private int score1 = 0;
    private int score2 = 0;
    private char token;
    private char otherPlayerToken;
    private int[] rowArr;
    private int[] colArr;
    private String[] playerTurns;
    
    private boolean continueGame = true;
    private Thread th;
    private ReplayMenuBase replayMenuBase;
    
    int row;
    int col;
    
    private MediaPlayer clickSound;
    private MediaPlayer winVideo;
    private MediaView winView;
    private MediaPlayer loseVideo;
    private MediaView loseView;
    private MediaPlayer tieVideo;
    private MediaView tieView;
    Timeline fade = new Timeline();
    
    
    public ReplayGameController(
            Stage primaryStage,
            Button backBtn,
            Button playAgain,
            GridPane gridPane,
            Label playerName1,
            Label playerName2,
            Label turnLabel,
            String name1,
            String name2,
            char token,
            char otherPlayerToken,
            int[] rowArr,
            int[] colArr,
            String[] playerTurns,
            AnchorPane videoPane) {
        
        mainMenuBtn = backBtn;
        playAgainBtn = playAgain;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.turnLabel = turnLabel;
        this.name1 = name1.toUpperCase();
        this.name2 = name2.toUpperCase();
        this.gridPane = gridPane;
        this.token = token;
        this.rowArr = rowArr;
        this.colArr = colArr;
        this.playerTurns = playerTurns;
        this.otherPlayerToken = otherPlayerToken;
        this.videoPane = videoPane;

        
        mainPlayer = name1;
        
        // Inititializing labels and cells
        labelInit();
        cellsInit();
        
        // Media
        clickSound = new MediaPlayer(
                new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        winVideo = new MediaPlayer(
                new Media(getClass().getResource("video/winning.mp4").toExternalForm()));
        winView = new MediaView(winVideo);
        winView.setFitHeight(397.0);
        winView.setFitWidth(491.0);
        
        loseVideo = new MediaPlayer(
                new Media(getClass().getResource("video/losing.mp4").toExternalForm()));
        loseView = new MediaView(loseVideo);
        loseView.setFitHeight(397.0);
        loseView.setFitWidth(491.0);
        
        tieVideo = new MediaPlayer(
                new Media(getClass().getResource("video/tie.mp4").toExternalForm()));
        tieView = new MediaView(tieVideo);
        tieView.setFitHeight(397.0);
        tieView.setFitWidth(491.0);

        
        playAgainBtn.setOnAction(e -> {
            th = new Thread(this);
            clickSound.play();
            winVideo.stop();
            loseVideo.stop();
            tieVideo.stop();
            fade.stop();
            videoPane.getChildren().removeAll(winView, loseView, tieView);
            th.start();
            playAgainBtn.setDisable(true);
        });
        
        backBtn.setOnAction(e -> {
            th.stop();
            replayMenuBase = new ReplayMenuBase(primaryStage, name1);
            Scene scene = new Scene(replayMenuBase, 636, 596);
            winVideo.stop();
            loseVideo.stop();
            tieVideo.stop();
            fade.stop();
            videoPane.getChildren().removeAll(winView, loseView, tieView);
            AnimationHelper.fadeAnimate(replayMenuBase);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
        });
        
    }
    
    @Override
    public void run() {
        int numberOfMoves = rowArr.length;
        int i = 0;

        if (token == 'O') {
            String temp = name1;
            name1 = name2;
            name2 = temp;
        }
        
        while(continueGame && i < numberOfMoves) {
            // draw first move
            row = rowArr[i];
            col = colArr[i];
            Platform.runLater(() -> {
                cells[row][col].setPlayer(name1);
            });
            i++;
            waitForAction(1);
            if (hasWon(name1)) {
                Platform.runLater(() -> {
                    turnLabel.setText(name1 + " won!");
                    videoPane.getChildren().add(winView);
                    winVideo.play();
                    fadeAnimation(7);
                });   
                continueGame = false;
                break;
            }
            else if (isBoardFull()){
                Platform.runLater(() -> {
                    turnLabel.setText("It's a draw!");
                    videoPane.getChildren().add(tieView);
                    tieVideo.play();
                    fadeAnimation(10);
                });   
                continueGame = false;
                break;
            }
            
            waitForAction(1200);
            // draw second move
            row = rowArr[i];
            col = colArr[i];
            Platform.runLater(() -> {  
                cells[row][col].setPlayer(name2);
            });
            i++;
            waitForAction(1);
            if (hasWon(name2)) {
                Platform.runLater(() -> {
                    turnLabel.setText(name2 + " won!");
                    videoPane.getChildren().add(loseView);
                    loseVideo.play();
                    fadeAnimation(10);
                });
                continueGame = false;
                break;
            }
            
            waitForAction(1200);
            
        }
    }
    
    private void waitForAction(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void labelInit() {
        if (token == 'X') {
            playerName1.setText("Player 1: " + name1);
            playerName2.setText("Player 2: " +name2);
        } else if(token == 'O') {
            playerName1.setText("Player 1: " + name2);
            playerName2.setText("Player 2: " +name1);
        }
        
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
        final String winTile = "-fx-background-color: #adff2f;"
                + " -fx-opacity: 0.7;"
                + " -fx-border-color: black;"
                + " -fx-border-width: 1px;";
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[i][0].getPlayer())  && player.equals(cells[i][1].getPlayer()) && player.equals(cells[i][2].getPlayer())) {
                cells[i][0].setStyle(winTile);
                cells[i][1].setStyle(winTile);
                cells[i][2].setStyle(winTile);
                return true;
            }
        }
        
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[0][i].getPlayer()) && player.equals(cells[1][i].getPlayer()) && player.equals(cells[2][i].getPlayer())) {
                cells[0][i].setStyle(winTile);
                cells[1][i].setStyle(winTile);
                cells[2][i].setStyle(winTile);
                return true;
            }
        }
        
        if (player.equals(cells[0][0].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][2].getPlayer())) {
            cells[0][0].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][2].setStyle(winTile);
            return true;
        }
        
        if (player.equals(cells[0][2].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][0].getPlayer())) {
            cells[0][2].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][0].setStyle(winTile);   
            return true;
        }
        
        return false;
    } 
    
    public void fadeAnimation(int duartion) {
        KeyFrame start = new KeyFrame(Duration.seconds(duartion),
                new KeyValue(videoPane.opacityProperty(), 1));
        KeyFrame end = new KeyFrame(Duration.seconds(duartion + 2),
                new KeyValue(videoPane.opacityProperty(), 0));
        fade = null;
        fade = new Timeline(start, end);
        fade.play();
    } 
}


