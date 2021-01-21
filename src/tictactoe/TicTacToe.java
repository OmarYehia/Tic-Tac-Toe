/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoe.Scenes.MainMenuBase;


/**
 *
 * @author LENOVO
 */
public class TicTacToe extends Application {
    
    private Pane root;
    private MainMenuBase menuScene;
    MediaPlayer player;
    
    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
        menuScene = new MainMenuBase(primaryStage);
        root.getChildren().add(menuScene);
        
        Scene scene = new Scene(root, 636, 596);
        

        
     
        // Configurations of the stage
        Image applicationIcon = new Image(getClass().getResource("Scenes/Logo.png").toExternalForm());
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
