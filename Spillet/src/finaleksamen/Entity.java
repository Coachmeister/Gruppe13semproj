/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author frederikhelth
 */
public class Entity {
    
    public Rectangle entity;
    private int x;
    private int y;
    private int positionX;
    private int positionY;
    private boolean visible = false;
    
    public Entity(int x, int y, int positionX){
        this.x = x;
        this.y = y;
        this.positionX = positionX;
        
        
        entity = new Rectangle(60,60);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(Color.BLACK);
        
        this.entity = entity;
        
        
    }
    
    public Rectangle getEntity(){
        return this.entity;
    }
    
    public void changeColor(int x){
        if(visible == false){
            if(x == positionX){
                entity.setFill(Color.RED);
            }
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
}
