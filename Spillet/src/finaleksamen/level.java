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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author frederikhelth
 */
public class level {
    
    public HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    
    public ArrayList<Node> platforms = new ArrayList<Node>();
    public ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<Exit> exits = new ArrayList<Exit>();
    public ArrayList<Entity> entitys = new ArrayList<Entity>();
    
    public Pane appRoot = new Pane();
    public Pane gameRoot = new Pane();
    public Pane uiRoot = new Pane();
    
    public Node player;
    public Point2D playerVelocity = new Point2D(0, 0);
    public boolean canJump = true;
    
    public int levelWidth = 30; // Standard with 30 
    
    public String levelFile;
    
    public int playerXPosition;
    public int playerYPosition;
    
    public int playerStartXPosition = 0;
    public int playerStartYPosition = 0;
    
    public Rectangle debugRectangle;
    public Label debugRectangleXLabel;
    public Label debugRectangleYLabel;
    
    public Label coins;
    public Label lifes;
    public boolean debug = false;
    
    
    
    /**
     * @param levelFile name of the level file
     */
    level(String levelFile){
        this.levelFile = levelFile;
    }
    
    /**
     * @param value sets the debug value
     */
    public void setDebug(boolean value){
        this.debug = value;
    }
    
    /**
     * @param scene
     * Description: Method for listing to key press and key release.
     */
    public void init(Scene scene){
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }
    
    /**
     * @param x
     * @param y
     * Description: When in debug mode, show what rectangle the current player is located at
     */
    public void setDebugRectangle(double x, double y){
        if(debug == true){
            
            // Calculate closest number that adds up to 60
            int x_cord = ((int)x / 60) * 60;
            int y_cord = ((int)y / 60) * 60;

            debugRectangleXLabel.setText("Current Rectangle X: " + x_cord);
            debugRectangleYLabel.setText("Current Rectangle Y: " + y_cord);

            debugRectangle.setTranslateX(x_cord);
            debugRectangle.setTranslateY(y_cord);
        }
    }
    
    /**
     * Description: Sets up everyting for level map and player
     */
    public Pane scene(){
        
        
        String csvFile = "Resources/levels/"+ this.levelFile +".csv"; // fetch level file
        BufferedReader br = null;
        
        try {

            br = new BufferedReader(new FileReader(csvFile)); // Load file
            
            String delims = ","; // Split up string by every comma and make an array
            Color color = null;
            
            boolean no_color = false;
            
            
            // Loop threw every line in file
            for(int row = 0; row < 12; row++){
                String rows = br.readLine(); // read line
                String[] tokens = rows.split(delims); // Split string up in array by every comma
                for(int col = 0; col < levelWidth; col++){ // Loop threw the array         
                    no_color = false; // if no color
                    switch(tokens[col]){
                        case "-1":
                            no_color = true; // in case the token returns -1, there is no block
                            break;
                        case "0": 
                            color = Color.BROWN; // Color the rectangle 0 brown
                        break;
                        default:
                            color = Color.BROWN; // Every other number will be brown too
                        break;
                    }
                    
                    // if there IS a color add it to platform array
                    if(no_color == false){
                        Node platform = createEntity(col*60, row*60, 60, 60, color);
                        platforms.add(platform);
                    }
                }
            }
            
        } catch (FileNotFoundException e) { // Helps you to know, if the file you have entered dosent exists
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        levelWidth = levelWidth * 60; // The width of the map
        
        Rectangle bg = new Rectangle(1280, 720);
        
        player = createEntity(playerStartXPosition, playerStartYPosition, 40, 40, Color.BLUE); // Draw the player
        
        // Sets the coin label
        coins = new Label("Coins collected: 0");
        coins.setTranslateY(10);
        coins.setTextFill(Color.YELLOW);
        
        // Sets the life label
        lifes = new Label("Lifes: 10 / 10");
        lifes.setTranslateX(150);
        lifes.setTranslateY(10);
        lifes.setTextFill(Color.RED);
        
        // Sets the label for showing player location on the X-axes
        Label LabelXPosition = new Label("X: 0");
        LabelXPosition.setTranslateY(50);
        LabelXPosition.setTextFill(Color.WHITE);
        
        // Listing to the player movement
        player.translateXProperty().addListener((obs, old, newValue) -> {
           int offset = newValue.intValue();
           
           playerXPosition = offset;
           // Makes sure that the camera follows player
           if(offset > 640 && offset < levelWidth - 640){
               gameRoot.setLayoutX(-(offset - 640));
           }
           LabelXPosition.setText("X: "+offset);
            
        });
        
        // Sets the label for showing player location on the X-axes
        Label LabelYPosition = new Label("Y: 0");
        LabelYPosition.setTranslateY(70);
        LabelYPosition.setTextFill(Color.WHITE);
        
        // Listens to the players movement on the Y-Axes
        player.translateYProperty().addListener((obs, old, newValue) -> {
           int offset = newValue.intValue();
           
           playerYPosition = offset;
           // Write the the label the y position
           LabelYPosition.setText("Y: "+offset);
        });
        
        // Sets all elements to the appRoot pane
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot, coins, lifes);
        
        // Only if level is in debug mode
        if(debug == true){
            debugRectangleXLabel = new Label();

            debugRectangleXLabel.setTranslateY(100);
            debugRectangleXLabel.setTextFill(Color.WHITE);

            debugRectangleYLabel = new Label();

            debugRectangleYLabel.setTranslateY(120);
            debugRectangleYLabel.setTextFill(Color.WHITE);

            debugRectangle = new Rectangle(60,60);
            debugRectangle.setTranslateX(100);
            debugRectangle.setTranslateY(100);
            debugRectangle.setOpacity(0.4);
            debugRectangle.setFill(Color.WHITE);
            gameRoot.getChildren().addAll(debugRectangle);
            
            // Adds rectangle that shows what rectangle the player is at, and the labels for Y and X coords
            
            appRoot.getChildren().addAll(debugRectangleXLabel, debugRectangleYLabel, LabelXPosition, LabelYPosition);
        }
        
        return appRoot;
        
    }
    
    /**
     * @param text
     * @param x
     * @param y
     * Description: Adds a label to the current level
     */
    public void addText(String text, int x, int y){
        
        Label label = new Label(text);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setTextFill(Color.WHITE);
        
        gameRoot.getChildren().addAll(label);
    }
    
    /**
     * @param x
     * @param y
     * Description: Sets the player position on level load
     */
    public void setPlayerPosition(int x, int y){
        this.playerStartXPosition = x;
        this.playerStartYPosition = y;
    }
    
    public void addFinish(){
        
    }
    
    /**
     * @param coins
     * Description: adds one more coin to the label
     */
    public void addCoinToText(int coins){
        this.coins.setText("Coins collected:  " + coins);
    }
    
    /**
     * @param lifes
     * Description: remove one life from life label
     */
    public void setLifes(int lifes){
        this.lifes.setText("Lifes: " + lifes + " / 10");
    }
    
    /**
     * @param entity
     * Description: Add a rectangle to the level
     */
    public void addEntity(Entity entity){
        entitys.add(entity);
        gameRoot.getChildren().add(entity.getEntity());
    }
    
    /**
     * @param name
     * Description: adds one coin to player inventory
     */
    public void addItem(Item coin){
        items.add(coin);
        coin.drawEntity(gameRoot);
    }
    
    /**
     * @param width
     * Description: if the current level is more than 30 rows, set this method
     */
    public void setLevelWidth(int width){
        levelWidth = width;
    }
    
    /**
     * @param exit
     * Description: Creates a exit to another level, and draws a white rectangle to show player where to press E to enter
     */
    public void setExit(Exit exit){
  
        exits.add(exit);
        
        Rectangle entity = new Rectangle(60,80);
        entity.setTranslateX(exit.x);
        entity.setTranslateY(exit.y-20);
        entity.setArcHeight(40);
        entity.setFill(exit.color);
        entity.toBack();
        
        gameRoot.getChildren().add(exit.getLabel());
        gameRoot.getChildren().add(entity);
        
    }
    
    /**
     * Description: Move the player on the X-axes and check for collision
     */
    public void movePlayerX(int value){

        boolean movingRight = value > 0;
        
        for(int i = 0; i < Math.abs(value); i++){
            for(Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingRight){
                        if(player.getTranslateX() + 40 == platform.getTranslateX()){
                            return;
                        }
                    }
                    else {
                        if(player.getTranslateX() == platform.getTranslateX() + 60){
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
        
    }
    
    /**
     * @param
     * description: Move the player on the Y-axes and check for collision
     */
    public void movePlayerY(int value){
        
        boolean movingDown = value > 0;
        
        for(int i = 0; i < Math.abs(value); i++){
            for(Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if(player.getTranslateY() + 40 == platform.getTranslateY()){
                            player.setTranslateY(player.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    }
                    else {
                        if(player.getTranslateY() == platform.getTranslateY() + 60){
                            return;
                        }
                    }
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
        
    }
    
    /**
     * @param x
     * @param y
     * @param w
     * @param h
     * @param color
     * @return: Rectangle
     * Description: Draw the rectangle for the player
     */
    public Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle entity = new Rectangle(w,h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.toFront();
        
        gameRoot.getChildren().add(entity);
        return entity;
        
    }
    
    /**
     * @param x
     * @param y
     * @param w
     * @param h
     * @param color
     * @return: Rectangle
     * Description: Draw the rectangle for the coin and make it have a radius 50%
     */
    public Node createCoin(int x, int y){
        Rectangle entity = new Rectangle(20,20);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setArcHeight(40);
        entity.setArcWidth(40);
        entity.setFill(Color.YELLOW);
        
        gameRoot.getChildren().add(entity);
        return entity;
        
    }
    
    /**
     * Method for make the player jump
     */
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }
    
    /**
     * @param x
     * @param y
     * @param w
     * @param h
     * @param color
     * @return: boolean
     * Description: if an key is pressed or not
     */
    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }
    
}