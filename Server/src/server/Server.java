/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.scenes.ServerBase;


/**
 *
 * @author LENOVO
 */
public class Server extends Application {
    
    private ServerBase server;
    private Pane root;

    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
        server = new ServerBase(primaryStage);
        root.getChildren().add(server);
        
        Scene scene = new Scene(root, 636, 596);

        // Configurations of the stage
        Image applicationIcon = new Image(getClass().getResource("scenes/Logo.png").toExternalForm());
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
