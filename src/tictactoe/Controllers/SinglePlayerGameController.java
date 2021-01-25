/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import tictactoe.Scenes.MainMenuBase;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import tictactoedb.TicTacToeDB;

/**
 *
 * @author karim
 */
public class SinglePlayerGameController {

   
    private Cell[][] cells;
    private String mainPlayer;
    private Stage stage;
    private final Button mainMenuBtn;
    private final Button playAgainBtnn;
    private final Label playerName1;
    private final Label playerName2;
    private final Label turnLabel;
    private final String name1;
    private final String name2;
    private final GridPane gridPane;
    private final AnchorPane videoPane;
    private Label player1Score;
    private Label player2Score;
    private int score1 = 0;
    private int score2 = 0;
    private MainMenuBase mainMenuBase;
    private int step = 0;
    private int [] numsI = new int[9];
    private int [] numsJ = new int[9];
    private String [] playerarr =  new String[9];
    private String difficulty;
    
    
    private MediaPlayer clickSound;
    private MediaPlayer winVideo;
    private MediaView winView;
    private MediaPlayer loseVideo;
    private MediaView loseView;
    private MediaPlayer tieVideo;
    private MediaView tieView;
    Timeline fade = new Timeline();
    
    private final String winTile = "-fx-background-color: #adff2f;"
                + " -fx-opacity: 0.7;"
                + " -fx-border-color: black;"
                + " -fx-border-width: 1px;";
    
    public SinglePlayerGameController(Stage primaryStage,
            Button mainMeniBtn,
            Button playAgainBtn,
            GridPane gridPane,
            Label player1Name,
            Label computer,
            Label turnLabel,
            Label player1Score,
            Label player2Score,
            String name,
            String Difficulty,
            AnchorPane videoPane)
    {
    
        mainMenuBtn = mainMeniBtn;
        playAgainBtnn = playAgainBtn;
        this.playerName1 = player1Name;
        this.playerName2 = computer;
        this.turnLabel = turnLabel;
        this.name1 = name;
        this.name2 = "Computer";
        this.gridPane = gridPane;
        this.videoPane = videoPane;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.difficulty = Difficulty;
        mainPlayer = name;
        
        turnLabel.setText(name1 + " turn");
        
        // Media controls
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
        
        // Inititializing labels and cells
        updateScore();
        labelInit();
        cellsInit();
        
        playAgainBtn.setOnAction(e -> {
            clickSound.play();
            winVideo.stop();
            loseVideo.stop();
            tieVideo.stop();
            fade.stop();
            videoPane.getChildren().removeAll(winView, loseView, tieView);
            cellsReset();
        });
        
        mainMeniBtn.setOnAction(e -> {
            mainMenuBase = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenuBase, 636, 596);
            winVideo.stop();
            loseVideo.stop();
            tieVideo.stop();
            fade.stop();
            videoPane.getChildren().removeAll(winView, loseView, tieView);
            AnimationHelper.fadeAnimate(mainMenuBase);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
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
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j].resetPlayer();
                cells[i][j].setStyle("");
            }
        }
        
        step = 0;
        for(int l = 0; l < numsI.length; l++){
            numsI[l]= 0;
            numsJ[l] = 0;
            playerarr[l] = null;
        }
        
    }
    
    public Integer randomNumber(){
        Random rand = new Random(); 
        int rand_int1 = rand.nextInt(3); 
        return rand_int1;
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
               // System.out.println(this);
                getChildren().addAll(l1, l2);
                turnLabel.setText(name2 + " turn"); 

            } 
            else if(player.equals(name2)) {
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
                  player = null;
                  getChildren().removeAll(l1, l2, oShape);
                  mainPlayer = name1;
            }
        }
        
        
        public void handleClick() {
            
            if(player == null && mainPlayer != null) {
                setPlayer(mainPlayer);
                numsI[step] = i;
                numsJ[step] = j;
                playerarr[step] =  new String(mainPlayer);
                step++;
                
                if (hasWon(mainPlayer)) {
                    turnLabel.setText(mainPlayer + " won!");
                    score1++;
                    updateScore();
                    videoPane.getChildren().add(winView);
                    winVideo.play();
                    fadeAnimation(7);
                    colorTiles();
                    mainPlayer = null;
                    new TicTacToeDB(numsI, numsJ, playerarr, mainPlayer);
                }
                
                else if (isBoardFull()){
                    mainPlayer = null;
                    videoPane.getChildren().add(tieView);
                    tieVideo.play();
                    fadeAnimation(10);
                    turnLabel.setText("It's a draw!");  
                    new TicTacToeDB(numsI, numsJ, playerarr, "draw");
                }
            
                if (mainPlayer != null) {

                    if(difficulty.equals("easy"))
                    {
                        setEasy();
                    }
                    else if(difficulty.equals("medium")){
                        setMedium();
                    }
                    else if(difficulty.equals("hard")){
                        setHard();
                    }

                    if (hasWon(name2)) {
                        turnLabel.setText(name2 + " won!");
                        score2++;
                        updateScore();
                        colorTiles();
                        videoPane.getChildren().add(loseView);
                        loseVideo.play();
                        fadeAnimation(10);
                        mainPlayer = null;
                        new TicTacToeDB(numsI, numsJ, playerarr, name2);
                    }
                }
            }
        }
    }
    public void setEasy(){

       int i;
       int j;
       do {
           i = randomNumber();
           j = randomNumber();
       } while (cells[i][j].getPlayer() != null);

       cells[i][j].setPlayer(name2); 
       numsI[step] = i;
       numsJ[step] = j;
       playerarr[step] =  new String(name2);
       step++;
    }
     
     
    public void setMedium() {
        int i= 0,j= 0;
        long bestScore;
        bestScore = Long.MIN_VALUE;
        for (int l = 0; l < 3; l++)
        {
            for (int k = 0; k < 3; k++) 
            {             
                if(cells[l][k].getPlayer() == null)
                {
                    cells[l][k].setPlayer(name2);
                    long  score = miniMedium(cells,0, false);
                    cells[l][k].resetPlayer();
                    if (score > bestScore)
                    {
                        bestScore = score;
                        i = l;
                        j = k;
                    }
                }
            }
        }
        cells[i][j].setPlayer(name2);
        numsI[step] = i;
        numsJ[step] = j;
        playerarr[step] =  new String(name2);
        step++;

    }
     
     
    public long miniMedium(Cell[][] cells,int depth, boolean isMaximizing) {
        long bestScore;
        long score;
        if(hasWon(name2) ){
            return 10;
        }
        if(hasWon(name1)){
            return -10;
        }
        if(isBoardFull()){
            return 0;
        }
        if(depth>=3){
            return 0;
        }

        if(isMaximizing)
        {
           bestScore = Long.MIN_VALUE;
         
            for (int l = 0; l < 3; l++)
            {
                for (int k = 0; k < 3 ; k++) 
                {
                    if(cells[l][k].getPlayer() == null )
                    {
                        cells[l][k].setPlayer(name2);
                        score = miniMedium(cells,depth+1, false);
                        cells[l][k].resetPlayer();
                        bestScore = Math.max(score, bestScore);
                    }

                }                
            }

            return bestScore;
        }
        
        else {
            bestScore = Long.MAX_VALUE;
            for (int l = 0; l < 3; l++)
            {
                for (int k = 0; k < 3 ; k++) 
                {
                    if(cells[l][k].getPlayer() == null )
                    {
                        cells[l][k].setPlayer(name1);
                        score = miniMedium(cells,depth+1, true);
                        cells[l][k].resetPlayer();
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            
             return bestScore;
        }   
    }
    
    public void setHard() {
        int i= 0,j= 0;
        long bestScore;
        bestScore = Long.MIN_VALUE;
        for (int l = 0; l < 3; l++)
        {
            for (int k = 0; k < 3; k++) 
            {             
                if(cells[l][k].getPlayer() == null  )
                {
                    cells[l][k].setPlayer(name2);
                    long  score = minimax(cells,0, false);
                    cells[l][k].resetPlayer();
                    if (score > bestScore)
                    {
                        bestScore = score;
                        i = l;
                        j = k;
                    }
                }
            }
        }
        cells[i][j].setPlayer(name2);
        numsI[step] = i;
        numsJ[step] = j;
        playerarr[step] =  new String(name2);
        step++;
    }
    
    public long minimax(Cell[][] cells,int depth, boolean isMaximizing){
        long bestScore;
        long score;
        if(hasWon(name2) ){
            return 10;
        }
        if(hasWon(name1)){
            return -10;
        }
        if(isBoardFull()){
             return 0;
        }

        if(isMaximizing) {
           bestScore = Long.MIN_VALUE;
         
            for (int l = 0; l < 3; l++)
            {
                for (int k = 0; k < 3 ; k++) 
                {
                    if(cells[l][k].getPlayer() == null )
                    {
                        cells[l][k].setPlayer(name2);
                        score = minimax(cells,depth+1, false);
                        cells[l][k].resetPlayer();
                        bestScore = Math.max(score, bestScore);
                    }

                }              
            }
            
            return bestScore;
        } else {
            bestScore = Long.MAX_VALUE;
          
            for (int l = 0; l < 3; l++)
            {
             
                for (int k = 0; k < 3 ; k++) 
                {
                    if(cells[l][k].getPlayer() == null )
                    {
                        cells[l][k].setPlayer(name1);
                        score = minimax(cells,depth+1, true);
                        cells[l][k].resetPlayer();
                        bestScore = Math.min(score, bestScore);
                    }
                }  
            } 
            
            return bestScore;
        }   
    }
    
    public boolean isBoardFull() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (cells[i][j].getPlayer() == null ){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasWon(String player) {
        
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[i][0].getPlayer())  && player.equals(cells[i][1].getPlayer()) && player.equals(cells[i][2].getPlayer())) {
                return true;
            }
        }
        
        for(int i = 0; i < 3; i++) {
            if (player.equals(cells[0][i].getPlayer()) && player.equals(cells[1][i].getPlayer()) && player.equals(cells[2][i].getPlayer())) {
                return true;
            }
        }
        
        if (player.equals(cells[0][0].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][2].getPlayer())) {
            return true;
        }
        
        if (player.equals(cells[0][2].getPlayer()) && player.equals(cells[1][1].getPlayer()) && player.equals(cells[2][0].getPlayer())) {
            return true;
        }
       
        return false;

    }
    
    public void colorTiles() {
        final String winTile = "-fx-background-color: #adff2f;"
                + " -fx-opacity: 0.7;"
                + " -fx-border-color: black;"
                + " -fx-border-width: 1px;";
        for(int i = 0; i < 3; i++) {
            if (cells[i][0].getPlayer() == cells[i][1].getPlayer()  
                && cells[i][1].getPlayer() == cells[i][2].getPlayer()
                && cells[i][0].getPlayer() != null)   {
                cells[i][0].setStyle(winTile);
                cells[i][1].setStyle(winTile);
                cells[i][2].setStyle(winTile);
            }
        }
        
        for(int i = 0; i < 3; i++) {
            if (cells[0][i].getPlayer() == cells[1][i].getPlayer() 
                && cells[1][i].getPlayer() == cells[2][i].getPlayer()
                && cells[1][i].getPlayer() != null) {
                cells[0][i].setStyle(winTile);
                cells[1][i].setStyle(winTile);
                cells[2][i].setStyle(winTile);
            }
        }
        
        if (cells[0][0].getPlayer() == cells[1][1].getPlayer() && cells[1][1].getPlayer() == cells[2][2].getPlayer()) {
            cells[0][0].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][2].setStyle(winTile);
        }
        
        if (cells[0][2].getPlayer() == cells[1][1].getPlayer() && cells[1][1].getPlayer() == cells[2][0].getPlayer()) {
            cells[0][2].setStyle(winTile);
            cells[1][1].setStyle(winTile);
            cells[2][0].setStyle(winTile);   
        }
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
