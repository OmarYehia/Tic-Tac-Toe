/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.Controllers;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import tictactoe.Scenes.ReplayGameBase;
import tictactoe.Scenes.ReplayNameBase;
import tictactoedb.TicTacToeDB;


/**
 *
 * @author LENOVO
 */
public class ReplayMenuController {
    
    private ReplayNameBase replayName;
    private ReplayGameBase replayGame;
    private TicTacToeDB db;
    private char playerToken;
    private char otherPlayerToken;
    private MediaPlayer clickSound;

    public ReplayMenuController(
            Stage primaryStage,
            Button backBtn,
            Button selectBtn,
            ListView listView,
            String name) {
        
            db = new TicTacToeDB();
            
            String[] opponents = db.getPlayerOpponentsName(name);
            String[] dates = db.getPlayerDates(name);
            int[] matchIDs = db.getPlayerMatchIDs(name);

            
            
            String[] items = listItems(name, opponents, dates);
            
            ObservableList<String> list = FXCollections.observableArrayList(items);
            listView.setItems(list);
        
        
            clickSound = new MediaPlayer(new Media(getClass().getResource("/sounds/click-sound.mp3").toExternalForm()));
        
        
        
        
        backBtn.setOnAction(e -> {
            replayName = new ReplayNameBase(primaryStage);
            Scene scene = new Scene(replayName, 636, 596);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
        selectBtn.setOnAction(e -> {
            String selectedMatchString = listView.getSelectionModel().getSelectedItem().toString();
            int selectedGameID = returnGameID(selectedMatchString, matchIDs);
            
            int[] rowArr = db.getMatchRow(selectedGameID);
            int[] colArr = db.getMatchColumn(selectedGameID);
            String[] playerTurns = db.getMatchStepPlayerName(selectedGameID);
            String secondPlayerName = returnSecondPlayerName(playerTurns, name);

            replayGame = new ReplayGameBase(primaryStage,
                    name,
                    secondPlayerName,
                    playerToken,
                    otherPlayerToken,
                    rowArr, 
                    colArr,
                    playerTurns);
            Scene scene = new Scene(replayGame, 636, 596);
            clickSound.play();
            primaryStage.setScene(scene);
        });
        
    }
    
    private String[] listItems(String name, String[] opponents, String[] dates) {
        int len = dates.length;
        String[] temp = new String[len];
        
        for(int i = 0; i < len; i++){
                temp[i] = new String((i+1) + "- " + name +
                        " VS " + opponents[i] + "  |  " + dates[i]);
            }
        
        return temp;
    }
    
    private int returnGameID(String listItem, int[] matchIDs) {
        String[] temp = listItem.split("-");
        int selectedIndex = Integer.parseInt(temp[0]);
        
        return matchIDs[selectedIndex - 1];
    }
    
    private String returnSecondPlayerName(String[] playerTurns, String mainPlayer) {
        if(playerTurns[0].equals(mainPlayer.toLowerCase())) {
            playerToken = 'X';
            otherPlayerToken = 'O';
            return playerTurns[1];
        } else {
            playerToken = 'O';
            otherPlayerToken = 'X';
            return playerTurns[0];
        }
    }
    
}
