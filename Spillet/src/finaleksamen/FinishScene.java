/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleksamen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author frederikhelth
 */
public class FinishScene {
    
    public Pane scene(Stage primaryStage){
        
        Label label1= new Label("You won!");
        Label label2 = new Label("You finish the game!");
        
        Button button1= new Button("Quit");
        
        button1.setOnAction(e -> primaryStage.close());   
        
        VBox layout_help = new VBox(20);  
        
        layout_help.getChildren().addAll(label1, label2, button1);
        
        return layout_help;
        
    }
    
}
