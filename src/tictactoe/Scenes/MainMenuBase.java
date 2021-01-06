package tictactoe.Scenes;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tictactoe.Controllers.MainMenuController;


public class MainMenuBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle background;
    protected final ImageView logo;
    protected final DropShadow dropShadowLogo;
    protected final Button singlePlayerBtn;
    protected final DropShadow dropShadowBtns;
    protected final Button twoPlayersBtn;
    protected final Button playFriendBtn;
    protected final Button replayBtn;
    protected final Label copyRightsLabel;
    protected final Button quitBtn;
    
    private MainMenuController controller;

    public MainMenuBase(Stage primaryStage) {

        anchorPane = new AnchorPane();
        background = new Rectangle();
        logo = new ImageView();
        dropShadowLogo = new DropShadow();
        singlePlayerBtn = new Button();
        dropShadowBtns = new DropShadow();
        twoPlayersBtn = new Button();
        playFriendBtn = new Button();
        replayBtn = new Button();
        copyRightsLabel = new Label();
        quitBtn = new Button();


        anchorPane.setId("Tic-Tac-Toe");
        anchorPane.setPrefHeight(543.0);
        anchorPane.setPrefWidth(648.0);

        background.setArcHeight(5.0);
        background.setArcWidth(5.0);
        background.setHeight(608.0);
        background.setStroke(javafx.scene.paint.Color.BLACK);
        background.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        background.setWidth(648.0);
        background.setFill(LinearGradient.valueOf("linear-gradient(from 100% 100% to 0% 0%, rgba(61,131,145,1) 100%, rgba(255,255,255,1) 0%)"));

        logo.setFitHeight(139.0);
        logo.setFitWidth(337.0);
        logo.setLayoutX(255.0);
        logo.setLayoutY(34.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
        logo.setImage(new Image(getClass().getResource("Logo.png").toString()));

        dropShadowLogo.setColor(javafx.scene.paint.Color.valueOf("#00000094"));
        logo.setEffect(dropShadowLogo);

        singlePlayerBtn.setLayoutX(192.0);
        singlePlayerBtn.setLayoutY(216.0);
        singlePlayerBtn.setMnemonicParsing(false);
        singlePlayerBtn.setPrefHeight(45.0);
        singlePlayerBtn.setPrefWidth(265.0);
        singlePlayerBtn.setText("Single Player");
        singlePlayerBtn.setFont(new Font(20.0));
        singlePlayerBtn.setCursor(Cursor.HAND);
        singlePlayerBtn.setFocusTraversable(false);

        dropShadowBtns.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadowBtns.setOffsetX(5.0);
        dropShadowBtns.setOffsetY(5.0);
        dropShadowBtns.setSpread(0.02);
        singlePlayerBtn.setEffect(dropShadowBtns);

        twoPlayersBtn.setLayoutX(192.0);
        twoPlayersBtn.setLayoutY(281.0);
        twoPlayersBtn.setMnemonicParsing(false);
        twoPlayersBtn.setPrefHeight(45.0);
        twoPlayersBtn.setPrefWidth(265.0);
        twoPlayersBtn.setText("Two Players");
        twoPlayersBtn.setFont(new Font(20.0));
        twoPlayersBtn.setFocusTraversable(false);

        twoPlayersBtn.setEffect(dropShadowBtns);
        twoPlayersBtn.setCursor(Cursor.HAND);

        playFriendBtn.setLayoutX(192.0);
        playFriendBtn.setLayoutY(349.0);
        playFriendBtn.setMnemonicParsing(false);
        playFriendBtn.setPrefHeight(45.0);
        playFriendBtn.setPrefWidth(265.0);
        playFriendBtn.setText("Play a Friend");
        playFriendBtn.setFont(new Font(20.0));
        playFriendBtn.setFocusTraversable(false);

        playFriendBtn.setEffect(dropShadowBtns);
        playFriendBtn.setCursor(Cursor.HAND);

        replayBtn.setLayoutX(192.0);
        replayBtn.setLayoutY(417.0);
        replayBtn.setMnemonicParsing(false);
        replayBtn.setPrefHeight(45.0);
        replayBtn.setPrefWidth(265.0);
        replayBtn.setText("Replay");
        replayBtn.setFont(new Font(20.0));
        replayBtn.setFocusTraversable(false);

        replayBtn.setEffect(dropShadowBtns);
        replayBtn.setCursor(Cursor.HAND);

        copyRightsLabel.setLayoutX(175.0);
        copyRightsLabel.setLayoutY(555.0);
        copyRightsLabel.setPrefHeight(53.0);
        copyRightsLabel.setPrefWidth(299.0);
        copyRightsLabel.setText("ITI Java Project | All Rights Reserved (C) 2021");
        copyRightsLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        quitBtn.setLayoutX(192.0);
        quitBtn.setLayoutY(480.0);
        quitBtn.setMnemonicParsing(false);
        quitBtn.setPrefHeight(45.0);
        quitBtn.setPrefWidth(265.0);
        quitBtn.setText("Quit");
        quitBtn.setFont(new Font(20.0));
        quitBtn.setFocusTraversable(false);

        quitBtn.setEffect(dropShadowBtns);
        quitBtn.setCursor(Cursor.HAND);

        anchorPane.getChildren().add(background);
        anchorPane.getChildren().add(logo);
        anchorPane.getChildren().add(singlePlayerBtn);
        anchorPane.getChildren().add(twoPlayersBtn);
        anchorPane.getChildren().add(playFriendBtn);
        anchorPane.getChildren().add(replayBtn);
        anchorPane.getChildren().add(copyRightsLabel);
        anchorPane.getChildren().add(quitBtn);
        getChildren().add(anchorPane);
        
        controller = new MainMenuController(primaryStage,
                singlePlayerBtn,
                twoPlayersBtn,
                playFriendBtn,
                replayBtn,
                quitBtn);
    }
    
    
}
