/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

/**
 *
 * @author frederikhelth
 */
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author frederikhelth
 */
public class Finaleksamen extends Application {

    Scene start, help, level1, level2, level3;

    public level currentScene;

    public Stage location;

    @Override
    public void start(Stage primaryStage) throws Exception {

        location = primaryStage;

        location.setTitle("My First JavaFX GUI");

        //Scene 1
        Label label1 = new Label("Welcome back");

        Button button1 = new Button("Start new game");
        button1.setOnAction(e -> primaryStage.setScene(level1));

        Button goToHelp = new Button("Help");
        goToHelp.setOnAction(e -> primaryStage.setScene(help));

        Button quit = new Button("Quit");
        quit.setOnAction(e -> primaryStage.close());

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1, goToHelp, quit);
        start = new Scene(layout1, 300, 250);

        HelpScene helpScene = new HelpScene();
        help = new Scene(helpScene.scene(location, start), 300, 250);

        level _level1 = new level("level1");
        level _level2 = new level("level2");
        level _level3 = new level("level3");

        level1 = new Scene(_level1.scene());
        level2 = new Scene(_level2.scene());
        level3 = new Scene(_level3.scene());

        _level1.test(level1);
        _level2.test(level2);
        _level3.test(level3);

        setExit(level2, _level1, _level2, 1700, 500);
        setExit(level3, _level2, _level3, 1700, 800);

        trigger(level1, _level1, 500, 550);

        location.setScene(start);
        location.show();

        location.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("Hejsa");
            }
        });

        currentScene = _level1;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentScene.update();
            }
        };

        timer.start();

    }

    public void trigger(Scene scene, level trigger, int x, int y) {
        Label riddle = new Label("press E to open");
        riddle.setTextFill(Color.WHITE);
        riddle.setVisible(true);
        riddle.toFront();

        trigger.appRoot.getChildren().add(riddle);
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 50) {

                    riddle.setTranslateY(currentScene.playerYPosition - 30);
                    riddle.setTranslateX(x);
                    riddle.setVisible(true);

                } else {
                    riddle.setVisible(false);
                }

                if (currentScene.isPressed(KeyCode.E)) {
                     if (currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 50) {
                         if(currentScene.isPressed(KeyCode.E)){
                           
                            
                         }
                     }
                }
            }

        };
        timer.start();

    }

    public void setExit(Scene scene, level from, level to, int x, int y) {

        Label label = new Label("Tryk 'E' for enter");
        label.setTextFill(Color.WHITE);
        label.setVisible(true);
        label.toFront();

        from.appRoot.getChildren().add(label);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 50) {

                    label.setTranslateY(currentScene.playerYPosition - 30);
                    label.setTranslateX(x - 550);
                    label.setVisible(true);

                } else {
                    label.setVisible(false);
                }

                if (currentScene.isPressed(KeyCode.E)) {
                    if (currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 50) {
                        location.setScene(scene);
                        currentScene = to;
                    }
                }
            }
        };
        timer.start();
        /*
         btn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
         location.setScene(scene);
         System.out.print("X: " + currentScene.playerXPosition + " Y " + currentScene.playerYPosition);
         currentScene = to;
         }
         });
         */

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
