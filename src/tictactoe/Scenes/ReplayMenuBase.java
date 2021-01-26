package tictactoe.Scenes;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.LinearGradient;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tictactoe.Controllers.ReplayMenuController;

/**
 *
 * @author OMAR YEHIA
 */
public class ReplayMenuBase extends StackPane {

    protected final AnchorPane anchorPane;
    protected final Rectangle rectangle;
    protected final ImageView logo;
    protected final DropShadow dropShadow;
    protected final Label label;
    protected final VBox vBox;
    protected final ListView listView;
    protected final Button backBtn;
    protected final DropShadow dropShadow0;
    protected final Button selectBtn;
    protected final DropShadow dropShadow1;
    
    private ReplayMenuController controller;

    public ReplayMenuBase(Stage primaryStage, String name) {

        anchorPane = new AnchorPane();
        rectangle = new Rectangle();
        logo = new ImageView();
        dropShadow = new DropShadow();
        label = new Label();
        vBox = new VBox();
        listView = new ListView();
        backBtn = new Button();
        dropShadow0 = new DropShadow();
        selectBtn = new Button();
        dropShadow1 = new DropShadow();

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

        logo.setFitHeight(139.0);
        logo.setFitWidth(337.0);
        logo.setLayoutX(267.0);
        logo.setLayoutY(33.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
        logo.setImage(new Image(getClass().getResource("Logo.png").toExternalForm()));

        dropShadow.setColor(javafx.scene.paint.Color.valueOf("#00000094"));
        logo.setEffect(dropShadow);

        label.setLayoutX(225.0);
        label.setLayoutY(214.0);
        label.setText("List of Games");
        label.setFont(new Font("Arial", 32.0));

        vBox.setLayoutX(124.0);
        vBox.setLayoutY(278.0);

        listView.setPrefHeight(232.0);
        listView.setPrefWidth(400.0);

        backBtn.setLayoutX(148.0);
        backBtn.setLayoutY(535.0);
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

        selectBtn.setLayoutX(347.0);
        selectBtn.setLayoutY(535.0);
        selectBtn.setMnemonicParsing(false);
        selectBtn.setPrefHeight(45.0);
        selectBtn.setPrefWidth(154.0);
        selectBtn.setText("Select");
        selectBtn.setFont(new Font(20.0));

        dropShadow1.setColor(javafx.scene.paint.Color.valueOf("#857e7e96"));
        dropShadow1.setOffsetX(5.0);
        dropShadow1.setOffsetY(5.0);
        dropShadow1.setSpread(0.02);
        selectBtn.setEffect(dropShadow1);
        selectBtn.setCursor(Cursor.HAND);

        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(logo);
        anchorPane.getChildren().add(label);
        vBox.getChildren().add(listView);
        anchorPane.getChildren().add(vBox);
        anchorPane.getChildren().add(backBtn);
        anchorPane.getChildren().add(selectBtn);
        getChildren().add(anchorPane);
        
        controller = new ReplayMenuController(primaryStage,
                backBtn,
                selectBtn,
                listView,
                name);
    }
}
