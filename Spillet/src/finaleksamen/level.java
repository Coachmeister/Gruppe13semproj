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
    
    public Pane appRoot = new Pane();
    public Pane gameRoot = new Pane();
    public Pane uiRoot = new Pane();
    
    public Node player;
    public Point2D playerVelocity = new Point2D(0, 0);
    public boolean canJump = true;
    
    public int levelWidth;
    
    public String levelFile;
    
    public int playerXPosition;
    public int playerYPosition;
    
    public int playerStartXPosition = 0;
    public int playerStartYPosition = 0;
    
    public Label coins;
    public Label lifes;
    
    public Color[] entityColors = {Color.ORANGE, Color.BROWN, Color.RED, Color.PURPLE, Color.GOLD};
    
    level(String levelFile){
        this.levelFile = levelFile;
    }
    
    public void init(Scene scene){
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }
    
    public void addText(String text, int x, int y){
        
        Label label = new Label(text);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setTextFill(Color.WHITE);
        
        gameRoot.getChildren().addAll(label);
    }
    
    public void setPlayerPosition(int x, int y){
        this.playerStartXPosition = x;
        this.playerStartYPosition = y;
    }
    
    public void addCoinToText(int coins){
        this.coins.setText("Coins collected: "+coins+" / 27");
    }
    
    public void setLifes(int lifes){
        this.lifes.setText("Lifes: "+lifes+" / 10");
    }
    
    public Pane scene(){
        
        String csvFile = "Resources/levels/"+ this.levelFile +".csv";
        BufferedReader br = null;
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            
            String delims = ",";
            levelWidth = 30 * 60;
            Color color = null;
            
            boolean no_color = false;
            
            color = getRandomColor();
            
            for(int row = 0; row < 12; row++){
                String rows = br.readLine();
                String[] tokens = rows.split(delims);
                for(int col = 0; col < 30; col++){            
                    no_color = false;
                    switch(tokens[col]){
                        case "-1":
                            no_color = true;
                            break;
                        case "0":
                            color = color;
                        break;
                        default:
                            color = color;
                        break;
                    }
                     
                    if(no_color == false){
                        Node platform = createEntity(col*60, row*60, 60, 60, color);
                        platforms.add(platform);
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
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
        
        
        Rectangle bg = new Rectangle(1280, 720);
        
        player = createEntity(playerStartXPosition, playerStartYPosition, 40, 40, Color.BLUE);
        
        coins = new Label("Coins collected: 0 / 27");
        coins.setTranslateY(10);
        coins.setTextFill(Color.YELLOW);
        
        lifes = new Label("Lifes: 10 / 10");
        lifes.setTranslateX(150);
        lifes.setTranslateY(10);
        lifes.setTextFill(Color.RED);
        
        Label LabelXPosition = new Label("X: 0");
        LabelXPosition.setTranslateY(50);
        LabelXPosition.setTextFill(Color.WHITE);
        
        player.translateXProperty().addListener((obs, old, newValue) -> {
           int offset = newValue.intValue();
           
           playerXPosition = offset;
           
           if(offset > 640 && offset < levelWidth - 640){
               gameRoot.setLayoutX(-(offset - 640));
           }
            LabelXPosition.setText("X: "+offset);
            
        });
        
        Label LabelYPosition = new Label("Y: 0");
        LabelYPosition.setTranslateY(70);
        LabelYPosition.setTextFill(Color.WHITE);
        
        player.translateYProperty().addListener((obs, old, newValue) -> {
           int offset = newValue.intValue();
           
           playerYPosition = offset;
           
           LabelYPosition.setText("Y: "+offset);
        });
        
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot, coins, lifes, LabelXPosition, LabelYPosition);
        
        return appRoot;
        
    }
    
    public void addItem(Item name){
        items.add(name);
        name.drawEntity(gameRoot);
    }
    
    public void removeItem(Item name){
        items.add(name);
    }
    
    public boolean getItem(int x, int y){
        for(int i = 0; i < items.size(); i++){
            System.out.println(items.get(i).getName());
        }
        return false;
    }
    
    public void getItems(){
        for(int i = 0; i < items.size(); i++){
            System.out.println(items.get(i).getName());
        }
    }
    
    public void setExit(Exit exit){
  
        exits.add(exit);
        
        Rectangle entity = new Rectangle(60,80);
        entity.setTranslateX(exit.x);
        entity.setTranslateY(exit.y);
        entity.setArcHeight(40);
        entity.setFill(Color.WHITE);
        entity.toBack();
        
        gameRoot.getChildren().add(exit.getLabel());
        gameRoot.getChildren().add(entity);
        
    }
    
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
    
    public Color getRandomColor() {
        return this.entityColors[new Random().nextInt(this.entityColors.length)];
    }
    
    public Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle entity = new Rectangle(w,h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.toFront();
        
        gameRoot.getChildren().add(entity);
        return entity;
        
    }
    
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
    
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }
    
    public boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }
    
}