/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 *
 * @author frederikhelth
 */
public class Exit {
    
    public level level;
    public Label label;
    public Scene scene;
    public boolean finish_line = false;
    public Color color;
    public int x;
    public int y;
    
    public Exit(level level, Scene scene, int x, int y) {
        this.scene = scene;
        this.level = level;
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
        // Create a label above the door
        label = new Label("Press E for enter");
        label.setTranslateX(x-20);
        label.setTranslateY(y-40);
        label.setTextFill(Color.WHITE);
        label.setVisible(false);
        
    }
    
    public Exit(int x, int y) {
        this.finish_line = true;
        this.color = Color.GOLD;
        this.x = x;
        this.y = y;
        
        // Create a label above the door
        label = new Label("Press E to finish");
        label.setTranslateX(x-20);
        label.setTranslateY(y-40);
        label.setTextFill(Color.WHITE);
        label.setVisible(false);
        
    }
    
    /**
     * @return: Label
     * Description: Returns the label
     */
    public Label getLabel(){
        return this.label;
    }
    
    /**
     * @param: visible
     * Description: sets the label visibility
     */
    public void setLabelVisible(Boolean visible){
        this.label.setVisible(visible);
    }
    
    /**
     * @param: level
     * Description: returns the level
     */
    public level getLevel(){
        return this.level; 
    }
    
    /**
     * @param: Scene
     * Description: returns the scene
     */
    public Scene getScene(){
        return this.scene; 
    }

    /**
     * @return: boolean
     * Description: Returns the value, if this is finish line of the game or not
     */
    public boolean getFinish(){
        return finish_line;
    }
    
    /**
     * @return: int
     * Description: Returns the x integer
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
