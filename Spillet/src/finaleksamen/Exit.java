/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

import javafx.scene.Scene;

/**
 *
 * @author frederikhelth
 */
public class Exit {
    
    public level level;
    public Scene scene;
    public int x;
    public int y;
    
    public Exit(level level, Scene scene, int x, int y) {
        this.scene = scene;
        this.level = level;
        this.x = x;
        this.y = y;
    }
    
    public level getLevel(){
        return this.level; 
    }
    
    public Scene getScene(){
        return this.scene; 
    }
    
    public int getX(){
        return this.x; 
    }
    
    public int getY(){
        return this.y; 
    }
    
}
