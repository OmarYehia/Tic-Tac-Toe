/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.ReplayMenuBase;


/**
 *
 * @author LENOVO
 */
public class ReplayNameController {
    
    private String name;
    private MainMenuBase mainMenu;
    private ReplayMenuBase replayMenu;
    private MediaPlayer clickSound;
    

    public ReplayNameController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField playerName) {
        
        clickSound = new MediaPlayer(new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        
        backBtn.setOnAction(e -> {
            mainMenu = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenu, 636, 596);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        confirmBtn.setOnAction(e -> {
            name = playerName.getText();
            replayMenu = new ReplayMenuBase(primaryStage, name);
            Scene scene = new Scene(replayMenu, 636, 596);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
    }
    
}
