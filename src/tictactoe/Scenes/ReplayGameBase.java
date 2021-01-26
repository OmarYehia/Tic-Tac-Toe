package tictactoe.Scenes;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tictactoe.Controllers.ReplayGameController;

/**
 *
 * @author OMAR YEHIA
 */
public class ReplayGameBase extends AnchorPane {

    protected final Rectangle rectangle;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final DropShadow dropShadow;
    protected final Button mainMeniBtn;
    protected final DropShadow dropShadow0;
    protected final Label player1Name;
    protected final Label player2Name;
    protected final Label player1Score;
    protected final Label player2Score;
    protected final Label turnLabel;
    protected final Button playAgainBtn;
    protected final DropShadow dropShadow1;
    protected final AnchorPane videoPane;
    
    private ReplayGameController controller;

    public ReplayGameBase(Stage primaryStage,
            String name1,
            String name2,
            char token,
            char otherPlayerToken,
            int[] rowArr,
            int[] colArr,
            String[] playerTurns) {

        rectangle = new Rectangle();
        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        dropShadow = new DropShadow();
        mainMeniBtn = new Button();
        dropShadow0 = new DropShadow();
        player1Name = new Label();
        player2Name = new Label();
        player1Score = new Label();
        player2Score = new Label();
        turnLabel = new Label();
        playAgainBtn = new Button();
        dropShadow1 = new DropShadow();
        videoPane = new AnchorPane();

        setId("AnchorPane");
        setPrefHeight(543.0);
        setPrefWidth(648.0);

        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setHeight(608.0);
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rectangle.setWidth(648.0);
        rectangle.setFill(LinearGradient.valueOf("linear-gradient(from 100% 100% to 0% 0%, rgba(61,131,145,1) 100%, rgba(255,255,255,1) 0%)"));


        gridPane.setGridLinesVisible(true);
        gridPane.setLayoutX(130.0);
        gridPane.setLayoutY(127.0);
        gridPane.setPrefHeight(354.0);
        gridPane.setPrefWidth(389.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        dropShadow.setHeight(39.66);
        dropShadow.setRadius(13.11);
        dropShadow.setSpread(0.15);
        dropShadow.setWidth(14.78);
        gridPane.setEffect(dropShadow);

        mainMeniBtn.setLayoutX(52.0);
        mainMeniBtn.setLayoutY(530.0);
        mainMeniBtn.setMnemonicParsing(false);
        mainMeniBtn.setPrefHeight(17.0);
        mainMeniBtn.setPrefWidth(103.0);
        mainMeniBtn.setText("Back");
        mainMeniBtn.setFont(new Font(16.0));

        dropShadow0.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow0.setOffsetX(5.0);
        dropShadow0.setOffsetY(5.0);
        dropShadow0.setSpread(0.02);
        mainMeniBtn.setEffect(dropShadow0);
        mainMeniBtn.setCursor(Cursor.HAND);

        player1Name.setLayoutX(75.0);
        player1Name.setLayoutY(41.0);
        player1Name.setText("Player 1 Name");
        player1Name.setFont(new Font(17.0));

        player2Name.setLayoutX(464.0);
        player2Name.setLayoutY(41.0);
        player2Name.setText("Player 2 Name");
        player2Name.setFont(new Font(17.0));
        
        videoPane.setLayoutX(79.0);
        videoPane.setLayoutY(160.0);
        videoPane.setPrefHeight(397.0);
        videoPane.setPrefWidth(491.0);
        videoPane.setMouseTransparent(true);

        turnLabel.setLayoutX(249.0);
        turnLabel.setLayoutY(505.0);
        turnLabel.setText("Player 1 turn");
        turnLabel.setFont(new Font(27.0));

        playAgainBtn.setLayoutX(487.0);
        playAgainBtn.setLayoutY(530.0);
        playAgainBtn.setMnemonicParsing(false);
        playAgainBtn.setPrefHeight(17.0);
        playAgainBtn.setPrefWidth(103.0);
        playAgainBtn.setText("Play");
        playAgainBtn.setFont(new Font(16.0));

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        playAgainBtn.setEffect(dropShadow1);
        playAgainBtn.setCursor(Cursor.HAND);

        getChildren().add(rectangle);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        getChildren().add(gridPane);
        getChildren().add(mainMeniBtn);
        getChildren().add(player1Name);
        getChildren().add(player2Name);
        getChildren().add(player1Score);
        getChildren().add(player2Score);
        getChildren().add(turnLabel);
        getChildren().add(videoPane);
        getChildren().add(playAgainBtn);
           
        controller = new ReplayGameController(
                primaryStage,
                mainMeniBtn,
                playAgainBtn,
                gridPane,
                player1Name,
                player2Name,
                turnLabel,
                name1,
                name2,
                token,
                otherPlayerToken,
                rowArr,
                colArr,
                playerTurns,
                videoPane);
    }
}
