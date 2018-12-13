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

    Scene start, help, dead, level1, level2, level3, level4, level5, level6, level7, finish;
    
    public level currentScene;
    public ArrayList<Inventory> inventory = new ArrayList<Inventory>();
    public Stage location;
    public boolean enter = false;
    public MediaPlayer coinSound;
    public int lifes = 10;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
   
        location = primaryStage;
        
        location.setTitle("Dont play this game"); // Window title
        
        String musicFile = "Resources/sounds/coin.wav"; // Make a string with the sound of coins being hit.

        Media sound = new Media(new File(musicFile).toURI().toString());
        this.coinSound = new MediaPlayer(sound); // Load sound
        
        Label label1= new Label("Welcome to Dont Player This Game");
        
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
        
        FinishScene finishScene = new FinishScene();
        finish = new Scene(finishScene.scene(location), 300, 250);

        level _level1 = new level("1_frederik_1");
        _level1.setPlayerPosition(0, 0);
        _level1.addText("Move using A and D", 100, 400);
        _level1.addText("Jump using W", 500, 400);
        _level1.addItem(new Item(700, 620));
        _level1.addItem(new Item(900, 620));
        
        level _level2 = new level("2_frederik_2");
        _level2.setPlayerPosition(0, 0);
        _level2.setDebug(false);
        _level2.addItem(new Item(360, 520));
        _level2.addItem(new Item(1220, 100));
        
        level _level3 = new level("3_jonas_1");
        _level3.setPlayerPosition(0, 0);
        _level3.setDebug(true);
        _level3.addItem(new Item(240, 420));
        _level3.addItem(new Item(420, 300));
        
        level _level4 = new level("4_jonas_2");
        _level4.setPlayerPosition(0, 0);
        _level4.setDebug(true);
        _level4.addItem(new Item(420, 480));
        _level4.addItem(new Item(1260, 240));
        
        level _level5 = new level("5_marcus_1");
        _level5.setPlayerPosition(0, 0);
        _level5.setDebug(true);
        _level5.addItem(new Item(720, 120));
        _level5.addItem(new Item(1260, 300));
        
        level _level6 = new level("6_christian_1");
        _level6.setPlayerPosition(0, 0);
        _level6.setDebug(false);
        _level6.setLevelWidth(60);
        
        _level6.addItem(new Item(10, 559));
        _level6.addItem(new Item(1090, 19));
        _level6.addItem(new Item(1150, 79));
        _level6.addItem(new Item(1630, 379));
        _level6.addItem(new Item(1750, 379));
        _level6.addItem(new Item(1870, 379));
        _level6.addItem(new Item(1990, 379));
        _level6.addItem(new Item(2470, 499));
        
        level _level7 = new level("7_frederik_3_logic");
        _level7.setPlayerPosition(0, 0);
        _level7.setDebug(true);
        _level7.addItem(new Item(1560, 480));
        
        
        level1 = new Scene(_level1.scene());
        _level1.init(level1);
        
        level2 = new Scene(_level2.scene());
        _level2.init(level2);
        
        level3 = new Scene(_level3.scene());
        _level3.init(level3);
        
        level4 = new Scene(_level4.scene());
        _level4.init(level4);
        
        level5 = new Scene(_level5.scene());
        _level5.init(level5);
        
        level6 = new Scene(_level6.scene());
        _level6.init(level6);
        
        level7 = new Scene(_level7.scene());
        _level7.init(level7);
        _level7.setDebug(true);
        _level7.addEntity(new Entity(960, 540, 780, 480));
        _level7.addEntity(new Entity(1140, 540, 960, 480));
        _level7.addEntity(new Entity(1380, 540, 1140, 480));
       
        
        _level1.setExit(new Exit(_level2, level2, 1680, 600));
        
        _level2.setExit(new Exit(_level1, level1, 0, 600));
        _level2.setExit(new Exit(_level3, level3, 1740, 540));
        
        _level3.setExit(new Exit(_level2, level2, 0, 600));
        _level3.setExit(new Exit(_level4, level4, 1740, 360));
        
        _level4.setExit(new Exit(_level3, level3, 0, 360));
        _level4.setExit(new Exit(_level5, level5, 1740, 420));
        _level5.setExit(new Exit(_level4, level4, 0, 540));
        _level5.setExit(new Exit(_level6, level6, 1740, 240));
        _level6.setExit(new Exit(_level5, level5, 0, 360));
        _level6.setExit(new Exit(_level7, level7, 3360, 480));
        _level7.setExit(new Exit(_level6, level6, 0, 480));
        _level7.setExit(new Exit(1680,480));
        
        location.setScene(start);
        location.show();
        
        // Sets first level to _level1
        currentScene = _level1;
        
        // Adds timer, and runs update method
        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now){
                update();
            }
        };
        
        timer.start();
        
    }
    
    /**
     * Functions for keyboard input
     */
    public void update(){
        
        // Listens to the W key being pressed down
        if(currentScene.isPressed(KeyCode.W) && currentScene.player.getTranslateY() >= 5){
            currentScene.jumpPlayer();
        }
        
        // Listens to the A key being pressed down
        if(currentScene.isPressed(KeyCode.A) && currentScene.player.getTranslateX() >= 5){
            currentScene.movePlayerX(-5);
        }
        
        // Listens to the D key being pressed down
        if(currentScene.isPressed(KeyCode.D) && currentScene.player.getTranslateX() + 40 <= currentScene.levelWidth - 5){
            currentScene.movePlayerX(5);
        }
        
        // Listens to the E key being pressed down, and mark enter virable to true
        if(currentScene.isPressed(KeyCode.E)){
            enter = true;
        }
        
        // If key is NOT pressed down, but have been released ( enter = true )
        if(currentScene.isPressed(KeyCode.E) == false && enter == true){
            enter = false; // To make sure this command only runs once
            goRoom();
        }
        
        if(currentScene.playerVelocity.getY() < 10){
            currentScene.playerVelocity = currentScene.playerVelocity.add(0, 1);
        }
        
        checkCoinCollision();
        checkFall();
        checkRoomHover();
        checkEntity();
        
        // If level is in debug mode, this will show some options for the develepher
        currentScene.setDebugRectangle(currentScene.playerXPosition, currentScene.playerYPosition);
        
        currentScene.movePlayerY((int)currentScene.playerVelocity.getY());
        
    }
    
    /**
     * If user is within a doors x and y's coordinates
     */
    public void checkRoomHover(){
        // Loop threw room exists Array
        for(int i = 0; i < currentScene.exits.size(); i++){
            
            int x = currentScene.exits.get(i).getX();
            int y = currentScene.exits.get(i).getY();

            // Checks the current position of the player is within coords of a exit
            if(currentScene.playerXPosition >= x && currentScene.playerXPosition <= x + 60 && 
                    currentScene.playerYPosition >= y-80 && currentScene.playerYPosition <= y + 80){
                // Show label to tell user to press E to enter
                currentScene.exits.get(i).setLabelVisible(true);
            }else{
               currentScene.exits.get(i).setLabelVisible(false);
            }

        }
    }
    
    /**
     * Function for entering a room
     */
    public void goRoom(){
        // Loop threw room exists Array
        for(int i = 0; i < currentScene.exits.size(); i++){
                
            int x = currentScene.exits.get(i).getX();
            int y = currentScene.exits.get(i).getY();
            
            // Checks if exit exists on the current player coordinates
            if(currentScene.playerXPosition >= x-60 && currentScene.playerXPosition <= x + 60 && 
                    currentScene.playerYPosition >= y-80 && currentScene.playerYPosition <= y + 80){
                
                boolean is_finish = currentScene.exits.get(i).getFinish();
            
                if(is_finish == true){
                    location.setScene(finish);
                }else{

                    // Sets the new virables
                    location.setScene(currentScene.exits.get(i).getScene());
                    currentScene = currentScene.exits.get(i).getLevel();
                    // Transfers the current lifes and inventory to next level.
                    currentScene.addCoinToText(inventory.size());
                    currentScene.setLifes(this.lifes);

                }
            }

        }
    }
    
    /**
     * Checks if user hits a coin
     */
    public void checkCoinCollision(){
        // Loop threw coins
        for(int i = 0; i < currentScene.items.size(); i++){
            
            int x = currentScene.items.get(i).getX();
            int y = currentScene.items.get(i).getY();
            
            // if player hits a coin
            if(currentScene.playerXPosition >= x-20 && currentScene.playerXPosition <= x + 20 &&
                    currentScene.playerYPosition >= y-20 && currentScene.playerYPosition <= y + 20){
                if(currentScene.items.get(i).getHit() == false){
                    
                    // Coin have been hit, run setHit method
                    currentScene.items.get(i).setHit();
                    this.coinSound.play(); // Play sound
                    this.coinSound.setOnEndOfMedia(() -> this.coinSound.stop()); // Resets the sound, so another coin sound can be played
                    inventory.add(new Inventory()); // Adds one to inventory
                    //System.out.print(inventory.size()); 
                    currentScene.addCoinToText(inventory.size()); // Adds + 1 to coin label
                }
            }

        }
    }
    
    /**
     * If user falls out of the map reset, and take one life
     * If no lifes left, then go to dead scene
     */
    public void checkFall(){
        // Checks if user is lower than 1000 on the Y-axes
        if(currentScene.playerYPosition > 1000){
            // Reset user position to Level start position
            currentScene.player.setTranslateX(currentScene.playerStartXPosition);
            currentScene.player.setTranslateY(currentScene.playerStartXPosition);
            
            // Fixes camera location
            currentScene.gameRoot.setLayoutX(-(currentScene.playerStartXPosition));
            
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
     * Checks if user hits and X and Y coordinate wich triggers a new block to appear
     */
    public void checkEntity(){
        for(int i = 0; i < currentScene.entitys.size(); i++){
                
            currentScene.entitys.get(i).changeColor(currentScene.playerXPosition, currentScene.playerYPosition);

        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

