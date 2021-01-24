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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
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
            System.out.println("Button pressed");
            namesScene = new TwoPlayersNamesBase(primaryStage);
            Scene scene = new Scene(namesScene, 636, 596);
            AnimationHelper.fadeAnimate(namesScene);
            mainMenuSound.stop();
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        playFriendBtn.setOnAction(e -> {
            System.out.println("Button pressed");
            multiplayerNameScene = new MultiplayerNameBase(primaryStage);
            Scene scene = new Scene(multiplayerNameScene, 636, 596);
            AnimationHelper.fadeAnimate(multiplayerNameScene);
            mainMenuSound.stop();
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        replayBtn.setOnAction(e -> {
            System.out.println("Button pressed");
            replayNameScene = new ReplayNameBase(primaryStage);
            Scene scene = new Scene(replayNameScene,636, 596);
            AnimationHelper.fadeAnimate(replayNameScene);
            mainMenuSound.stop();
            clickSound.play();
            primaryStage.setScene(scene);
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
            System.out.println("Button pressed");
            clickSound.play();
            System.exit(0);
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
        });
        
    }
}
