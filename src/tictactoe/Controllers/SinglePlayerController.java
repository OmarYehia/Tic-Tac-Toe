/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.SinglePlayerNameBase;
/**
 *
 * @author karim
 */
public class SinglePlayerController {

    private SinglePlayerNameBase namesScene;
    private MainMenuBase mainMenuBase;
    private String difficulty;
    public SinglePlayerController(Stage primaryStage,
            Button Easy,
            Button Medium,
            Button hard,
            Button MainMenu)
    {
    
        Easy.setOnAction(e -> {
            System.out.println("Button pressed");
            difficulty = "easy";
            namesScene = new SinglePlayerNameBase(primaryStage, difficulty);
            Scene scene = new Scene(namesScene, 636, 596);
            primaryStage.setScene(scene);
        });
        
        Medium.setOnAction(e -> {
            System.out.println("Button pressed");
            difficulty = "medium";
            namesScene = new SinglePlayerNameBase(primaryStage, difficulty);
            Scene scene = new Scene(namesScene, 636, 596);
            primaryStage.setScene(scene);
        });
        hard.setOnAction(e -> {
            System.out.println("Button pressed");
            difficulty = "hard";
            namesScene = new SinglePlayerNameBase(primaryStage, difficulty);
            Scene scene = new Scene(namesScene, 636, 596);
            primaryStage.setScene(scene);
        });
        MainMenu.setOnAction(e -> {
            mainMenuBase = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenuBase, 636, 596);
            KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(mainMenuBase.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(mainMenuBase.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
            primaryStage.setScene(scene);
        });
    
    
    
    
    
    
    }
    
}
