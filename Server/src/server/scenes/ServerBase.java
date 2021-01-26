package server.scenes;

import controllers.ServerBaseController;
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

/**
 *
 * @author OMAR YEHIA
 */
public class ServerBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle rectangle;
    protected final ImageView imageView;
    protected final DropShadow dropShadow;
    protected final Button startBtn;
    protected final DropShadow dropShadow0;
    protected final Label label;
    protected final Button stopBtn;
    protected final DropShadow dropShadow1;
    protected final Label label0;
    protected final Label status;
    
    private ServerBaseController controller;

    public ServerBase(Stage primaryStage) {

        anchorPane = new AnchorPane();
        rectangle = new Rectangle();
        imageView = new ImageView();
        dropShadow = new DropShadow();
        startBtn = new Button();
        dropShadow0 = new DropShadow();
        label = new Label();
        stopBtn = new Button();
        dropShadow1 = new DropShadow();
        label0 = new Label();
        status = new Label();

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
        imageView.setLayoutX(268.0);
        imageView.setLayoutY(32.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("Logo.png").toExternalForm()));

        dropShadow.setColor(javafx.scene.paint.Color.valueOf("#00000094"));
        imageView.setEffect(dropShadow);

        startBtn.setLayoutX(96.0);
        startBtn.setLayoutY(420.0);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(45.0);
        startBtn.setPrefWidth(158.0);
        startBtn.setText("Start");
        startBtn.setFont(new Font(20.0));

        dropShadow0.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow0.setOffsetX(5.0);
        dropShadow0.setOffsetY(5.0);
        dropShadow0.setSpread(0.02);
        startBtn.setEffect(dropShadow0);
        startBtn.setCursor(Cursor.HAND);

        label.setLayoutX(175.0);
        label.setLayoutY(555.0);
        label.setPrefHeight(53.0);
        label.setPrefWidth(299.0);
        label.setText("ITI Java Project | All Rights Reserved (C) 2021");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        stopBtn.setLayoutX(395.0);
        stopBtn.setLayoutY(420.0);
        stopBtn.setMnemonicParsing(false);
        stopBtn.setPrefHeight(45.0);
        stopBtn.setPrefWidth(158.0);
        stopBtn.setText("Stop");
        stopBtn.setFont(new Font(20.0));

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        stopBtn.setEffect(dropShadow1);
        stopBtn.setCursor(Cursor.HAND);

        label0.setLayoutX(232.0);
        label0.setLayoutY(226.0);
        label0.setPrefHeight(53.0);
        label0.setPrefWidth(185.0);
        label0.setText("Server Status");
        label0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label0.setUnderline(true);
        label0.setFont(new Font(32.0));

        status.setLayoutX(297.0);
        status.setLayoutY(304.0);
        status.setPrefHeight(53.0);
        status.setPrefWidth(70.0);
        status.setText("OFF");
        status.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        status.setFont(new Font(32.0));
        status.setStyle("-fx-text-fill: red;");

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(startBtn);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(stopBtn);
        anchorPane.getChildren().add(label0);
        anchorPane.getChildren().add(status);
        getChildren().add(anchorPane);
        
        controller = new ServerBaseController(startBtn, stopBtn, status, primaryStage);

    }
}
