/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import java.io.PrintStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.SinglePlayerGameBase;

/**
 *
 * @author karim
 */
public class SinglePlayerNameController {

     private MainMenuBase mainMenu;
     private String name;
     private SinglePlayerGameBase singleGame;
     private MediaPlayer clickSound;
     
    public SinglePlayerNameController(Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField player1Name,
            String difficulty)
    {
        
        clickSound = new MediaPlayer(
                new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        
        backBtn.setOnAction(e -> {
            mainMenu = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenu, 636, 596);
            AnimationHelper.fadeAnimate(mainMenu);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        confirmBtn.setOnAction(e -> {
            name = player1Name.getText();
            singleGame = new SinglePlayerGameBase(primaryStage, name, difficulty);
            Scene scene = new Scene(singleGame, 636, 596);
            AnimationHelper.fadeAnimate(singleGame);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
    }
    
}
