/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.io.PrintStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Socket s;
    

    public MultiPlayerNameController(
            Stage primaryStage,
            Button backBtn,
            Button confirmBtn,
            TextField playerName) {
        
//        try {
//            s = new Socket(InetAddress.getLocalHost(), 1234);
//            inputStream = new DataInputStream(s.getInputStream());
//            printStream = new DataOutputStream(s.getOutputStream());
//        } catch (IOException ex) {
//            //ex.printStackTrace();
//            
//            mainMenu = new MainMenuBase(primaryStage);
//            Scene scene = new Scene(mainMenu, 636, 596);
//            primaryStage.setScene(scene);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Failure connecting to server");
//            alert.setHeaderText("The server is currently down");
//            alert.setContentText("Please try again later");
//            alert.showAndWait();
//        }
        
        backBtn.setOnAction(e -> {
            mainMenu = new MainMenuBase(primaryStage);
            Scene scene = new Scene(mainMenu, 636, 596);
            primaryStage.setScene(scene);
        });
        
        confirmBtn.setOnAction(e -> {
            name = playerName.getText();
            multiGame = new MultiplayerGameBase(primaryStage, name, s);
            Scene scene = new Scene(multiGame, 636, 596);
            primaryStage.setScene(scene);
        });
        
    }
    
}
