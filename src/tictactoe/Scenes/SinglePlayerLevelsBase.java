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
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tictactoe.Controllers.SinglePlayerController;

public class SinglePlayerLevelsBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle rectangle;
    protected final ImageView imageView;
    protected final DropShadow dropShadow;
    protected final Button Easy;
    protected final DropShadow dropShadow0;
    protected final Button Medium;
    protected final DropShadow dropShadow1;
    protected final Button hard;
    protected final DropShadow dropShadow2;
    protected final Label label;
    protected final Button MainMenu;
    protected final DropShadow dropShadow3;
    private SinglePlayerController controller;

    public SinglePlayerLevelsBase(Stage primaryStage) {

        anchorPane = new AnchorPane();
        rectangle = new Rectangle();
        imageView = new ImageView();
        dropShadow = new DropShadow();
        Easy = new Button();
        dropShadow0 = new DropShadow();
        Medium = new Button();
        dropShadow1 = new DropShadow();
        hard = new Button();
        dropShadow2 = new DropShadow();
        label = new Label();
        MainMenu = new Button();
        dropShadow3 = new DropShadow();

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

        Easy.setLayoutX(192.0);
        Easy.setLayoutY(216.0);
        Easy.setMnemonicParsing(false);
        Easy.setPrefHeight(45.0);
        Easy.setPrefWidth(265.0);
        Easy.setText("Easy");
        Easy.setFont(new Font(20.0));
        Easy.setCursor(Cursor.HAND);

        dropShadow0.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow0.setOffsetX(5.0);
        dropShadow0.setOffsetY(5.0);
        dropShadow0.setSpread(0.02);
        Easy.setEffect(dropShadow0);

        Medium.setLayoutX(192.0);
        Medium.setLayoutY(281.0);
        Medium.setMnemonicParsing(false);
        Medium.setPrefHeight(45.0);
        Medium.setPrefWidth(265.0);
        Medium.setText("Medium");
        Medium.setFont(new Font(20.0));

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        Medium.setEffect(dropShadow1);
        Medium.setCursor(Cursor.HAND);

        hard.setLayoutX(192.0);
        hard.setLayoutY(349.0);
        hard.setMnemonicParsing(false);
        hard.setPrefHeight(45.0);
        hard.setPrefWidth(265.0);
        hard.setText("Hard");
        hard.setFont(new Font(20.0));

        dropShadow2.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow2.setOffsetX(5.0);
        dropShadow2.setOffsetY(5.0);
        dropShadow2.setSpread(0.02);
        hard.setEffect(dropShadow2);
        hard.setCursor(Cursor.HAND);

        label.setLayoutX(175.0);
        label.setLayoutY(555.0);
        label.setPrefHeight(53.0);
        label.setPrefWidth(299.0);
        label.setText("ITI Java Project | All Rights Reserved (C) 2021");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        MainMenu.setLayoutX(192.0);
        MainMenu.setLayoutY(480.0);
        MainMenu.setMnemonicParsing(false);
        MainMenu.setPrefHeight(45.0);
        MainMenu.setPrefWidth(265.0);
        MainMenu.setText("Main Menu");
        MainMenu.setFont(new Font(20.0));

        dropShadow3.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow3.setOffsetX(5.0);
        dropShadow3.setOffsetY(5.0);
        dropShadow3.setSpread(0.02);
        MainMenu.setEffect(dropShadow3);
        MainMenu.setCursor(Cursor.HAND);

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(Easy);
        anchorPane.getChildren().add(Medium);
        anchorPane.getChildren().add(hard);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(MainMenu);
        getChildren().add(anchorPane);
        controller = new SinglePlayerController(primaryStage,
        Easy,
        Medium,
        hard,
        MainMenu);

    }
}
