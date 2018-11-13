package group13;

// Import section
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Lets rooms have descriptions and exits leading to other rooms.
 * 
 * @author Rasmus Willer
 */
public class Room {
    // Declare variables
    private String description;
    private String leftDesc;
    private String roomName;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items = new ArrayList<Item>(); 
    private ArrayList<Character> characters = new ArrayList<Character>();
    public boolean goRight = true;
    public boolean goLeft = true;
   
    
    /**
     * Constructor; assign description of room to variable and instantiate exit
     * HashMap.
     * 
     * @param description String, description of room.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }
    
    public Room(String description, String rightDesc) {
        this.description = description;
        this.leftDesc = rightDesc;
        exits = new HashMap<String, Room>();
    }
    
    public Room(String roomName, String description, String rightDesc) {
        this.description = description;
        this.leftDesc = rightDesc;
        this.roomName = roomName;
        exits = new HashMap<String, Room>();
    }

    public boolean isBlocked(String direction){
        for (int i = 0; i < characters.size(); i++) {
            if(characters.get(i).getMood() == 0){
                if(direction.equals("right") && characters.get(i).blocksRight == 1){
                    return true;
                }
                if(direction.equals("left") && characters.get(i).blocksLeft == 1){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Set an exit point from the room.
     * 
     * @param direction String, direction out of room.
     * @param neighbor Room, the room the exit leads to.
     */
    public void setExit(String direction, Room neighbor) {
        //System.out.println("EXIT");
        exits.put(direction, neighbor);
    }
    
    public String getRoomName(){
        return this.roomName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Return short description of the room.
     * 
     * @return String, short description of the room.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return long description of the room.
     * 
     * @return String, long description of the room.
     */
    public String getLongDescription() {
        return description + ".\n" + getExitString();
    }
    
    public String getLongDescriptionLeft() {
        return leftDesc + ".\n" + getExitString();
    }

    /**
     * Return complex String of exits from the room.
     * 
     * @return String, exits from the room.
     */
    public String getExitString() {
        // Start the String with title
        String returnString = "";
        if(getRoomItems() != null){            
            returnString += "\nItems in room:\n";
            returnString += getRoomItems() + " ";
            returnString += "\n";
        }
        if(getRoomCharacters() != null){
            returnString += "\nCharacters in the room:\n";
            returnString += getRoomCharacters() + "\n";
        }
        
        returnString += "\nExits:";
        // add each exit to the String, separated by space
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        // Return String listing exits
        return returnString;
    }

    /**
     * Get the room associated with an exit.
     * 
     * @param direction String, direction of exit.
     * @return Room, room in that direction.
     */
    
    public Item getItem(int index){
        return items.get(index);
    }
    
    public Item getItem(String itemName){
        Item toReturn = null;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDescription().equals(itemName)){
                return items.get(i);
            }
        }
        return null;
    }
    
    public void setItem(Item newitem){
        items.add(newitem);
    }
    
    public Character getCharacter(String characterName){
        for (int i = 0; i < characters.size(); i++) {
            if(characters.get(i).getName().equals(characterName)){
                return characters.get(i);
            }
        }
        return null;
    }
    
    public void setCharacter(Character newCharacter){
        characters.add(newCharacter);
    }
        
    public String getRoomCharacters(){
        String output = "";
        for (int i = 0; i < characters.size(); i++) {
            output += characters.get(i).getName() + ": " + characters.get(i).getDescription();
        }
        if(output.isEmpty()){
            return null;
        }
        return output;
    }
    
    public String getRoomItems(){
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getDescription() + " ";
        }
        if(output.isEmpty()){
            return null;
        }
        return output;
    }
    
    public void removeItem(String itemName){
        Item toReturn = null;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDescription() == itemName){
                items.remove(i);
            }
        }
    }
    
    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
}
