/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import helpers.AnimationHelper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.TwoPlayerGameBase;

/**
 *
 * @author OMAR YEHIA
 */
public class TwoPlayersNamesController {
    
    private String name1;
    private String name2;
    private MainMenuBase mainMenu;
    private TwoPlayerGameBase gameBase;
    private MediaPlayer clickSound;
    
    public TwoPlayersNamesController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField player1Name,
            TextField player2Name) {
        
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
                name1 = player1Name.getText();
                name2 = player2Name.getText();
                clickSound.play();
                if(name1.equals("") || name2.equals(""))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Unvalid Names");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter valid names");
                    alert.showAndWait();
                } else if (name1.toLowerCase().equals(name2.toLowerCase())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Unvalid Names");
                    alert.setHeaderText(null);
                    alert.setContentText("Can't set the same name for both players.");
                    alert.showAndWait();
                }
                else {
                    gameBase = new TwoPlayerGameBase(primaryStage, name1, name2);
                    Scene scene = new Scene(gameBase, 636, 596);
                    AnimationHelper.fadeAnimate(gameBase);
                    clickSound.play();
                    primaryStage.setScene(scene);
                }
            });
            
    }
    
}
