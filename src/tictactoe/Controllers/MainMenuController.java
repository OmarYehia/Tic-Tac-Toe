/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tictactoe.Scenes.TwoPlayersNamesBase;

public class MainMenuController {
    private TwoPlayersNamesBase namesScene;
    
    public MainMenuController(Stage primaryStage,
            Button singlePlayerBtn,
            Button twoPlayersBtn,
            Button playFriendBtn,
            Button replayBtn,
            Button quitBtn) 
    {
        
        
        singlePlayerBtn.setOnAction(e -> {
            System.out.println("Button pressed");
            
        });
        
        twoPlayersBtn.setOnAction(e -> {
            System.out.println("Button pressed");
            namesScene = new TwoPlayersNamesBase(primaryStage);
            Scene scene = new Scene(namesScene, 636, 596);
            primaryStage.setScene(scene);
        });
        
        playFriendBtn.setOnAction(e -> {
            System.out.println("Button pressed");
        });
        
        replayBtn.setOnAction(e -> {
            System.out.println("Button pressed");
        });
        
        quitBtn.setOnAction(e -> {
            System.out.println("Button pressed");
        });
        
    }
}
