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
        
        Label label1= new Label("Help info here");
        Button button1= new Button("Back");
        button1.setOnAction(e -> primaryStage.setScene(GoBackScene));   
        VBox layout_help = new VBox(20);     
        layout_help.getChildren().addAll(label1, button1);
        
        return layout_help;
        
    }
    
}