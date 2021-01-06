package tictactoe.Scenes;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tictactoe.Controllers.TwoPlayersNamesController;

public class TwoPlayersNamesBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle rectangle;
    protected final ImageView imageView;
    protected final DropShadow dropShadow;
    protected final Button backBtn;
    protected final DropShadow dropShadow0;
    protected final Button confirmBtn;
    protected final DropShadow dropShadow1;
    protected final Label label;
    protected final TextField player1Name;
    protected final Label label0;
    protected final TextField player2Name;

    private TwoPlayersNamesController controller;
    
    public TwoPlayersNamesBase(Stage primaryStage) {

        anchorPane = new AnchorPane();
        rectangle = new Rectangle();
        imageView = new ImageView();
        dropShadow = new DropShadow();
        backBtn = new Button();
        dropShadow0 = new DropShadow();
        confirmBtn = new Button();
        dropShadow1 = new DropShadow();
        label = new Label();
        player1Name = new TextField();
        label0 = new Label();
        player2Name = new TextField();

        anchorPane.setId("AnchorPane");
        anchorPane.setPrefHeight(543.0);
        anchorPane.setPrefWidth(648.0);

        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setHeight(608.0);
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rectangle.setWidth(648.0);
        rectangle.setFill(LinearGradient.valueOf("linear-gradient(from 100% 100% to 0% 0%, rgba(61,131,145,1) 100%, rgba(255,255,255,1) 0%)"));

        imageView.setFitHeight(139.0);
        imageView.setFitWidth(337.0);
        imageView.setLayoutX(255.0);
        imageView.setLayoutY(34.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("Logo.png").toExternalForm()));

        dropShadow.setColor(javafx.scene.paint.Color.valueOf("#00000094"));
        imageView.setEffect(dropShadow);

        backBtn.setLayoutX(101.0);
        backBtn.setLayoutY(422.0);
        backBtn.setMnemonicParsing(false);
        backBtn.setPrefHeight(45.0);
        backBtn.setPrefWidth(154.0);
        backBtn.setText("Back");
        backBtn.setFont(new Font(20.0));

        dropShadow0.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow0.setOffsetX(5.0);
        dropShadow0.setOffsetY(5.0);
        dropShadow0.setSpread(0.02);
        backBtn.setEffect(dropShadow0);
        backBtn.setCursor(Cursor.HAND);

        confirmBtn.setLayoutX(394.0);
        confirmBtn.setLayoutY(422.0);
        confirmBtn.setMnemonicParsing(false);
        confirmBtn.setPrefHeight(45.0);
        confirmBtn.setPrefWidth(154.0);
        confirmBtn.setText("Confirm");
        confirmBtn.setFont(new Font(20.0));

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        confirmBtn.setEffect(dropShadow1);
        confirmBtn.setCursor(Cursor.HAND);

        label.setLayoutX(59.0);
        label.setLayoutY(254.0);
        label.setPrefHeight(35.0);
        label.setPrefWidth(182.0);
        label.setText("Player 1 Name");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextOverrun(javafx.scene.control.OverrunStyle.WORD_ELLIPSIS);
        label.setFont(new Font("Arial", 24.0));

        player1Name.setLayoutX(267.0);
        player1Name.setLayoutY(256.0);
        player1Name.setPrefHeight(31.0);
        player1Name.setPrefWidth(323.0);
        player1Name.setPromptText("Please enter a name");
        

        label0.setLayoutX(59.0);
        label0.setLayoutY(316.0);
        label0.setPrefHeight(35.0);
        label0.setPrefWidth(182.0);
        label0.setText("Player 2 Name");
        label0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label0.setTextOverrun(javafx.scene.control.OverrunStyle.WORD_ELLIPSIS);
        label0.setFont(new Font("Arial", 24.0));

        player2Name.setLayoutX(267.0);
        player2Name.setLayoutY(318.0);
        player2Name.setPrefHeight(31.0);
        player2Name.setPrefWidth(323.0);
        player2Name.setPromptText("Please enter a name");

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(backBtn);
        anchorPane.getChildren().add(confirmBtn);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(player1Name);
        anchorPane.getChildren().add(label0);
        anchorPane.getChildren().add(player2Name);
        getChildren().add(anchorPane);
        
        
        controller = new TwoPlayersNamesController(
                primaryStage,
                backBtn,
                confirmBtn,
                player1Name,
                player2Name);
    }
}
