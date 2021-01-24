/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import tictactoe.Scenes.MainMenuBase;
import tictactoe.Scenes.MultiplayerGameBase;
import tictactoe.Scenes.MultiplayerNameBase;
import tictactoe.Scenes.ReplayMenuBase;
import tictactoe.Scenes.ReplayNameBase;
import tictactoe.Scenes.SinglePlayerGameBase;
import tictactoe.Scenes.SinglePlayerLevelsBase;
import tictactoe.Scenes.SinglePlayerNameBase;
import tictactoe.Scenes.TwoPlayerGameBase;
import tictactoe.Scenes.TwoPlayersNamesBase;

/**
 *
 * @author LENOVO
 */
public abstract class AnimationHelper {
    public static void fadeAnimate(SinglePlayerLevelsBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(TwoPlayersNamesBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(MultiplayerNameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(ReplayNameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(MainMenuBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(MultiplayerGameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(SinglePlayerNameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(SinglePlayerGameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(TwoPlayerGameBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
    
    public static void fadeAnimate(ReplayMenuBase base) {
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(base.opacityProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.seconds(0.3),
                    new KeyValue(base.opacityProperty(), 1));
            Timeline fade = new Timeline(start, end);
            fade.play();
    }
}
