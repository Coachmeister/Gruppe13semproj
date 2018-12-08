/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

import java.io.File;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author frederikhelth
 */
public class Item {
 
    private String name;
    private Rectangle entity; 
    private boolean hit = false;
    private int x;
    private int y;
    
    public Item(String name, int x, int y){
        
        entity = new Rectangle(20,20);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setArcHeight(40);
        entity.setArcWidth(40);
        entity.setFill(Color.YELLOW);
        
        this.entity = entity;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public Rectangle drawEntity(Pane gameRoot){
        gameRoot.getChildren().add(this.entity);
        return this.entity;
    }
    
    public Rectangle getEntity(){
        return this.entity;
    }
    
    public boolean getHit(){
        
        return this.hit;
    }
    
    public void setHit(){
        this.hit = true;
        this.entity.setFill(Color.TRANSPARENT);
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
}
