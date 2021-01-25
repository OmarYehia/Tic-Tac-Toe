package tictactoe.Scenes;

import java.net.Socket;
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
import tictactoe.Controllers.MultiPlayerNameController;

public class MultiplayerNameBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle rectangle;
    protected final ImageView imageView;
    protected final DropShadow dropShadow;
    protected final Button backBtn;
    protected final DropShadow dropShadow0;
    protected final Button confirmBtn;
    protected final DropShadow dropShadow1;
    protected final Label label;
    protected final TextField playerName;
    
    private MultiPlayerNameController controller;

    public MultiplayerNameBase(Stage primaryStage) {

        anchorPane = new AnchorPane();
        rectangle = new Rectangle();
        imageView = new ImageView();
        dropShadow = new DropShadow();
        backBtn = new Button();
        dropShadow0 = new DropShadow();
        confirmBtn = new Button();
        dropShadow1 = new DropShadow();
        label = new Label();
        playerName = new TextField();

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
        backBtn.setCancelButton(true);

        confirmBtn.setLayoutX(394.0);
        confirmBtn.setLayoutY(422.0);
        confirmBtn.setMnemonicParsing(false);
        confirmBtn.setPrefHeight(45.0);
        confirmBtn.setPrefWidth(154.0);
        confirmBtn.setText("Confirm");
        confirmBtn.setFont(new Font(20.0));
        confirmBtn.setDefaultButton(true);

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        confirmBtn.setEffect(dropShadow1);
        confirmBtn.setCursor(Cursor.HAND);

        label.setLayoutX(255.0);
        label.setLayoutY(277.0);
        label.setPrefHeight(35.0);
        label.setPrefWidth(113.0);
        label.setText("Username");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextOverrun(javafx.scene.control.OverrunStyle.WORD_ELLIPSIS);
        label.setFont(new Font("Arial", 24.0));

        playerName.setLayoutX(163.0);
        playerName.setLayoutY(329.0);
        playerName.setPrefHeight(31.0);
        playerName.setPrefWidth(323.0);
        playerName.setPromptText("Please enter your username");
        

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(playerName);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(backBtn);
        anchorPane.getChildren().add(confirmBtn);
        anchorPane.getChildren().add(label);
        
        getChildren().add(anchorPane);
        
        playerName.requestFocus();
        
        controller = new MultiPlayerNameController(
                primaryStage,
                backBtn,
                confirmBtn,
                playerName);

    }
}
