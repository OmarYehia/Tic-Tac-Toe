/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.TwoPlayerGameBase;

/**
 *
 * @author LENOVO
 */
public class TwoPlayersNamesController {
    
    private String name1;
    private String name2;
    private MainMenuBase mainMenu;
    private TwoPlayerGameBase gameBase;
    
    
    public TwoPlayersNamesController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField player1Name,
            TextField player2Name) {

            backBtn.setOnAction(e -> {
                mainMenu = new MainMenuBase(primaryStage);
                Scene scene = new Scene(mainMenu, 636, 596);
                primaryStage.setScene(scene);
            });
            
            confirmBtn.setOnAction(e -> {
                name1 = player1Name.getText();
                name2 = player2Name.getText();
                if(name1.equals("") || name2.equals(""))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Unvalid Names");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter valid names");
                    alert.showAndWait();
                }
                else {
                    gameBase = new TwoPlayerGameBase(primaryStage, name1, name2);
                    Scene scene = new Scene(gameBase, 636, 596);
                    primaryStage.setScene(scene);
                }
            });
            
    }
    
}
