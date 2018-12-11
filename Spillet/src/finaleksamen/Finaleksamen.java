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
import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

    Scene start, help, dead, level1, level2;
    
    public level currentScene;
    public ArrayList<Inventory> inventory = new ArrayList<Inventory>();
    public Stage location;
    public boolean enter = false;
    public MediaPlayer coinSound;
    public int lifes = 10;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
   
        location = primaryStage;
        
        location.setTitle("My First JavaFX GUI");
        
        String musicFile = "Resources/sounds/coin.wav";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        this.coinSound = new MediaPlayer(sound);
        
        //Scene 1
        Label label1= new Label("Welcome back");
        
        Button button1= new Button("Start new game");
        button1.setOnAction(e -> primaryStage.setScene(level1)); 
        
        Button goToHelp = new Button("Help");
        goToHelp.setOnAction(e -> primaryStage.setScene(help)); 
         
        Button quit = new Button("Quit");
        quit.setOnAction(e -> primaryStage.close()); 
        
        VBox layout1 = new VBox(20);     
        layout1.getChildren().addAll(label1, button1, goToHelp, quit);
        start= new Scene(layout1, 300, 250);
        
        HelpScene helpScene = new HelpScene();
        help = new Scene(helpScene.scene(location, start), 300, 250);

        level _level1 = new level("level3");
        _level1.setPlayerPosition(0, 0);
        _level1.addItem(new Item("Test", 300, 500));
        _level1.addItem(new Item("Test", 350, 500));
        
        level _level2 = new level("level3");
        _level2.setPlayerPosition(0, 0);
        _level2.addItem(new Item("Test", 450, 500));
        
        level1 = new Scene(_level1.scene());
        level2 = new Scene(_level2.scene());
        
        _level1.init(level1);
        _level1.setExit(new Exit(_level2, level2, 100, 550));
        _level1.addText("hejsa med dig!", 100, 100);
        
        _level2.init(level2);
        _level2.setExit(new Exit(_level1, level1, 200, 550));
        
        
        location.setScene(start);
        location.show();
        
        currentScene = _level1;
        
        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now){
                update();
            }
        };
        
        timer.start();
        
    }
    
    public void update(){
        
        if(currentScene.isPressed(KeyCode.W) && currentScene.player.getTranslateY() >= 5){
            currentScene.jumpPlayer();
        }
        
        if(currentScene.isPressed(KeyCode.A) && currentScene.player.getTranslateX() >= 5){
            currentScene.movePlayerX(-5);
        }
        
        if(currentScene.isPressed(KeyCode.D) && currentScene.player.getTranslateX() + 40 <= currentScene.levelWidth - 5){
            currentScene.movePlayerX(5);
        }
        
        if(currentScene.isPressed(KeyCode.E)){
            enter = true;
        }
        
        if(currentScene.isPressed(KeyCode.E) == false && enter == true){
            enter = false;
            goRoom();
        }
        
        if(currentScene.playerVelocity.getY() < 10){
            currentScene.playerVelocity = currentScene.playerVelocity.add(0, 1);
        }
        
        checkCoinCollision();
        checkFall();
        checkRoomHover();
        
        currentScene.movePlayerY((int)currentScene.playerVelocity.getY());
        
    }
    
    public void checkRoomHover(){
        for(int i = 0; i < currentScene.exits.size(); i++){
                
            int x = currentScene.exits.get(i).getX();
            int y = currentScene.exits.get(i).getY();

            if(currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 60 && 
                    currentScene.playerYPosition >= y-80 && currentScene.playerYPosition <= y + 80){
                currentScene.exits.get(i).setLabelVisible(true);
            }else{
               currentScene.exits.get(i).setLabelVisible(false);
            }

        }
    }
    
    public void goRoom(){
        for(int i = 0; i < currentScene.exits.size(); i++){
                
            int x = currentScene.exits.get(i).getX();
            int y = currentScene.exits.get(i).getY();

            if(currentScene.playerXPosition >= x-60 && currentScene.playerXPosition <= x + 60 && 
                    currentScene.playerYPosition >= y-80 && currentScene.playerYPosition <= y + 80){
                location.setScene(currentScene.exits.get(i).getScene());
                currentScene = currentScene.exits.get(i).getLevel();
                currentScene.addCoinToText(inventory.size());
                currentScene.setLifes(this.lifes);
            }

        }
    }
    
    public void checkCoinCollision(){
        for(int i = 0; i < currentScene.items.size(); i++){
                
            int x = currentScene.items.get(i).getX();
            int y = currentScene.items.get(i).getY();

            if(currentScene.playerXPosition >= x-20 && currentScene.playerXPosition <= x + 20 &&
                    currentScene.playerYPosition >= y-20 && currentScene.playerYPosition <= y + 20){
                if(currentScene.items.get(i).getHit() == false){
                    currentScene.items.get(i).setHit();
                    this.coinSound.play();
                    this.coinSound.setOnEndOfMedia(() -> this.coinSound.stop());
                    inventory.add(new Inventory());
                    System.out.print(inventory.size());
                    currentScene.addCoinToText(inventory.size());
                }
            }

        }
    }
    
    public void checkFall(){
        if(currentScene.playerYPosition > 1000){
            currentScene.player.setTranslateX(currentScene.playerStartXPosition);
            currentScene.player.setTranslateY(currentScene.playerStartXPosition);
            this.lifes -= 1;
            currentScene.setLifes(this.lifes);
            if(this.lifes == 0){
                                
                DeadScene deadScene = new DeadScene();
                dead = new Scene(deadScene.scene(location, start), 300, 250);
                location.setScene(dead);
                
            }
            System.out.println(this.lifes);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

