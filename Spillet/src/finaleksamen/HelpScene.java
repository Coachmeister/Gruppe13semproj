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
public class HelpScene {
    
    public Pane scene(Stage primaryStage, Scene GoBackScene){
        
        Label label1= new Label("Welcome to the help menu! Here you can find information about the game.");
        Label label2 = new Label("Player movement: WASD.");
        Label label3 = new Label("Interaction with rooms, items and triggers: E");
      
        Button button1= new Button("Back");
        button1.setOnAction(e -> primaryStage.setScene(GoBackScene));   
        VBox layout_help = new VBox(20);     
        layout_help.getChildren().addAll(label1, button1,label2, label3);
        
        return layout_help;
        
    }
    
}