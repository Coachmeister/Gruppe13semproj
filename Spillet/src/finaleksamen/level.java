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

/**
 *
 * @author frederikhelth
 */
public class level {
    
    public HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    
    public ArrayList<Node> platforms = new ArrayList<Node>();
    
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
    
    public void test(Scene scene){
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }
    
    level(String levelFile){
        this.levelFile = levelFile;
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
                            color = Color.BROWN;
                        break;
                        case "1":
                            color = Color.GREEN;
                        break;
                    }
                     
                    if(no_color == false){
                        Node platform = createEntity(col*60, row*60, 60, 60, color);
                        platforms.add(platform);
                    }
                }
            }
            /*
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                for(int j = 0; j < country.length; j++){
                switch(country[j]){
                    case "-1":
                        break;
                    case "0":
                        Node platform = createEntity(j*60, j*60, 60, 60, Color.BROWN);
                        platforms.add(platform);
                        break;
                }
            }

            }
            */
            
            /*
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }
            */
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
        /*
        levelWidth = level1.LEVEL1[0].length() * 60;
        try {
        for(int i = 0; i < level1.LEVEL1.length; i++){
            String lined = level1.LEVEL1[i];
            for(int j = 0; j < lined.length(); j++){
                switch(lined.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j*60, i*60, 60, 60, Color.BROWN);
                        platforms.add(platform);
                        break;
                }
            }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        */
        player = createEntity(0, 100, 40, 40, Color.BLUE);
        
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
        
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot, LabelXPosition, LabelYPosition);
        
        return appRoot;
        
    }
    
    public void update(){
        
        if(isPressed(KeyCode.W) && player.getTranslateY() >= 5){
            jumpPlayer();
        }
        
        if(isPressed(KeyCode.A) && player.getTranslateX() >= 5){
            movePlayerX(-5);
        }
        
        if(isPressed(KeyCode.D) && player.getTranslateX() + 40 <= levelWidth - 5){
            movePlayerX(5);
        }
        
        if(playerVelocity.getY() < 10){
            playerVelocity = playerVelocity.add(0, 1);
        }
        
        movePlayerY((int)playerVelocity.getY());
        
        
        
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
    
    
    public Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle entity = new Rectangle(w,h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        
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
