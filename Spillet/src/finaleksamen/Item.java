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
 
    private Rectangle entity; 
    private boolean hit = false;
    private int x;
    private int y;
    
    public Item(int x, int y){
        
        entity = new Rectangle(20,20);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setArcHeight(40);
        entity.setArcWidth(40);
        entity.setFill(Color.YELLOW);
        
        this.entity = entity;
        this.x = x;
        this.y = y;
    }
    
    /**
     * @param: Rectangle
     * @return: Rectangle
     * Description: Returns the x integer
     */
    public Rectangle drawEntity(Pane gameRoot){
        gameRoot.getChildren().add(this.entity);
        return this.entity;
    }
    
    /**
     * @return: Rectangle
     * Description: returns the coin
     */
    public Rectangle getEntity(){
        return this.entity;
    }
    
    /**
     * @return: boolean
     * Description: returns the value of hit
     */
    public boolean getHit(){
        return this.hit;
    }
    
    /**
     * Description: Sets the value hit to true, and make coin transparent
     */
    public void setHit(){
        this.hit = true;
        this.entity.setFill(Color.TRANSPARENT);
    }
    
    /**
     * @return: int
     * Description: Returns the y integer
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * @return: int
     * Description: Returns the y integer
     */
    public int getY(){
        return this.y;
    }
    
}
