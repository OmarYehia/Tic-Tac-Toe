/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.util.Optional;
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
import tictactoe.Scenes.ReplayMenuBase;
import tictactoedb.TicTacToeDB;


/**
 *
 * @author LENOVO
 */
public class ReplayNameController {
    
    private String name;
    private MainMenuBase mainMenu;
    private ReplayMenuBase replayMenu;
    private MediaPlayer clickSound;
    TicTacToeDB db;
    

    public ReplayNameController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField playerName) {
        
        clickSound = new MediaPlayer(new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        db = new TicTacToeDB();
        
        backBtn.setOnAction(e -> {
            mainMenu = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenu, 636, 596);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        confirmBtn.setOnAction(e -> {
            name = playerName.getText();
            if(db.checkPlayerExist(name) == 1) {
                replayMenu = new ReplayMenuBase(primaryStage, name);
                Scene scene = new Scene(replayMenu, 636, 596);
                clickSound.play();
                primaryStage.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unregistered player");
                alert.setHeaderText(null);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setContentText("We're sorry! We don't have any records of this player.");
//                ButtonType mainMenu = new ButtonType("Main Menu");
//                alert.getButtonTypes().setAll(mainMenu);
                Optional<ButtonType> result = alert.showAndWait();
            }
   
        });
        
    }
    
}
