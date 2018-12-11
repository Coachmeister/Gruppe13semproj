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
public class MainScene {
    
    public Pane scene(Stage primaryStage, Scene help, Scene level1){
        
        //Scene 1
        Label label1= new Label("Welcome back");
        
        Button button1= new Button("Start new game");
        button1.setOnAction(e -> primaryStage.setScene(level1)); 
        
        Button goToHelp = new Button("Help");
        goToHelp.setOnAction(e -> primaryStage.setScene(help)); 
         
        Button quit = new Button("Quit");
        quit.setOnAction(e -> primaryStage.close()); 
        
        VBox layout1 = new VBox(20);     
        layout1.getChildren().addAll(label1, button1, goToHelp, quit);
        
        return layout1;
        
    }
    
}
