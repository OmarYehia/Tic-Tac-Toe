/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.ServerHandler;

/**
 *
 * @author OMAR YEHIA
 */
public class ServerBaseController {
    private ServerHandler server;
    Thread th;
    
    public ServerBaseController(Button startBtn, Button stopBtn, Label status, Stage primaryStage) {
        
        startBtn.setOnAction(e -> {
            server = new ServerHandler();
            th = new Thread(server);
            System.out.println("Server is online");
            th.start();
            status.setText("ON");
            status.setStyle("-fx-text-fill: green;");
        });
        
        stopBtn.setOnAction(e -> {   
            System.exit(0);
        });
        
        primaryStage.setOnHidden(e -> {
            System.exit(0);
        });
        
        
    }
}
