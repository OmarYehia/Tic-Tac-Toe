/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import helpers.DatabaseConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tictactoe.Scenes.MultiplayerNameBase;
import tictactoe.Scenes.ReplayNameBase;
import tictactoe.Scenes.SinglePlayerLevelsBase;
import tictactoe.Scenes.TwoPlayersNamesBase;

public class MainMenuController {
    private MultiplayerNameBase multiplayerNameScene;
    private TwoPlayersNamesBase namesScene;
    private ReplayNameBase replayNameScene;
    private SinglePlayerLevelsBase singlePlayerNameScene;
//    private Socket s;
    
    
    private MediaPlayer mainMenuSound;
    private MediaPlayer clickSound;
    
    public MainMenuController(Stage primaryStage,
            Button singlePlayerBtn,
            Button twoPlayersBtn,
            Button playFriendBtn,
            Button replayBtn,
            Button quitBtn,
            ToggleButton soundBtn) 
    {
        
        // Sound
        mainMenuSound = new MediaPlayer(
                new Media(getClass().getResource("/sounds/13-super-machine.mp3").toExternalForm()));
        mainMenuSound.setOnEndOfMedia(new Runnable() {
            public void run() {
              mainMenuSound.seek(Duration.ZERO);
            }
        });
        mainMenuSound.play();
        mainMenuSound.setVolume(0);

        Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3),
                        new KeyValue(mainMenuSound.volumeProperty(), 0.1)));
        timeline.play(); 
        
        clickSound = new MediaPlayer(
                new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        
        // Buttons
        singlePlayerBtn.setOnAction(e -> {
            singlePlayerNameScene = new SinglePlayerLevelsBase(primaryStage);
            Scene scene = new Scene(singlePlayerNameScene, 636, 596);
            AnimationHelper.fadeAnimate(singlePlayerNameScene);
            mainMenuSound.stop();
            clickSound.play();
            primaryStage.setScene(scene);
            
        });
        
        twoPlayersBtn.setOnAction(e -> {
            namesScene = new TwoPlayersNamesBase(primaryStage);
            Scene scene = new Scene(namesScene, 636, 596);
            AnimationHelper.fadeAnimate(namesScene);
            mainMenuSound.stop();
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        playFriendBtn.setOnAction(e -> {
                multiplayerNameScene = new MultiplayerNameBase(primaryStage);
                Scene scene = new Scene(multiplayerNameScene, 636, 596);
                AnimationHelper.fadeAnimate(multiplayerNameScene);
                mainMenuSound.stop();
                clickSound.play();
                primaryStage.setScene(scene);
        });
        
        replayBtn.setOnAction(e -> {
            if(isDBConnected(DatabaseConfig.DB_URL, DatabaseConfig.DB_USERNAME, DatabaseConfig.DB_PASSWORD)){
                replayNameScene = new ReplayNameBase(primaryStage);
                Scene scene = new Scene(replayNameScene,636, 596);
                AnimationHelper.fadeAnimate(replayNameScene);
                mainMenuSound.stop();
                clickSound.play();
                primaryStage.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Database unavailable");
                alert.setHeaderText(null);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setContentText("Appearantly the database isn't connected. Please connect it and try again.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        });
        
        soundBtn.setOnAction(e -> {
            clickSound.play();
            if(soundBtn.isSelected()) {
                mainMenuSound.stop();
            }
            else {
                mainMenuSound.play();
                mainMenuSound.setVolume(0);
                timeline.play(); 
            }
            
        });
        
        quitBtn.setOnAction(e -> {
            clickSound.play();
            System.exit(0);
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
        });
        
    }
    
    public boolean isDBConnected(String url, String username, String password){
        boolean connected = false;
        
        try (Connection con = DriverManager.getConnection(url, username, password);){  
            connected = true;
        } catch (SQLException e) {
            System.out.println("The database is not connected");
        } finally {
            return connected;
        }  
    }
 }
