/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.MultiplayerGameBase;

/**
 *
 * @author LENOVO
 */
public class MultiPlayerNameController {
    
    private String name;
    private MainMenuBase mainMenu;
    private MultiplayerGameBase multiGame;
    private Socket socket;
    private MediaPlayer clickSound;
    private final int PORT_NUMBER = 1234;
    private boolean isServerConnected;
    

    public MultiPlayerNameController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField playerName,
            Socket s) {
        
        this.socket = s;
        
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
                name = playerName.getText();
                multiGame = new MultiplayerGameBase(primaryStage, name, socket);
                Scene scene = new Scene(multiGame, 636, 596);
                AnimationHelper.fadeAnimate(multiGame);
                clickSound.play();
                primaryStage.setScene(scene);
        });
        
    }
    
    
    
}
