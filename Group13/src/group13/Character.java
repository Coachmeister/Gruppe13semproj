/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group13;

/**
 *
 * @author frederikhelth
 */
public class Character {
    
    public String name;
    public String description;
    public String happyDescription;
    public String wants;
    public int mood;
    public int blocksLeft = 0;
    public int blocksRight = 1;
    
    public Character(String name, String description, String happyDescription, String wants){
        this.name = name;        
        this.description = description;
        this.wants = wants;
        this.mood = 0;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setMood(int mood){
        this.description = this.happyDescription;
        this.mood = mood;
    }
    
    public String getNeeds(){
        return this.wants;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getMood(){
        return this.mood;
    }
    
}
