package group13;

import java.util.ArrayList;

/*
FRederiks Brance! Jonas Kommentar!
sdfsdfdsfsdf en ændring
*/

/**
 * Instantiation of Game class generate rooms and creates an input parser.
 * A method call runs the game.
 * 
 * @author Rasmus Willer
 * christian
 */
public class Game {
    // Declare variables
    private Parser parser;
    private Room currentRoom;
    private String currentRoomName;
    private int hasBanana = 0;
    private int monkyHappy = 0;
    ArrayList<Item> inventory = new ArrayList<Item>();
    ArrayList<Character> character = new ArrayList<Character>();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    /**
     * No-arg constructor; Creates rooms and input parser.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Generate rooms with description and sets exit points to other rooms,
     * and sets which room is the current.
     */
    private void createRooms() {
        // Declare variables
        Room start, stairs, banana, monky, noMonky, flood;
        
        // Instantiate rooms with description
        start = new Room("start", "Første rum", "Du har været her før?");
        stairs = new Room("stairs", "Trapper!", "Trapper!");
        banana = new Room("banana", "Hey en banan!", "Hey en banan!");
        monky = new Room("monky", "Goddag abe", "Goddag abe!");
        noMonky = new Room("noMonky", "INGEN abe", "INGEN abe!");
        flood = new Room("flood", "Du hænger i over en flod!", "Du hænger i over en flod!");
        /*
        office = new Room("in the computing admin office");
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        */
        // Set exits to other rooms
        start.setExit("right", stairs);
        
        stairs.setExit("left", banana);
        stairs.setExit("right", banana);
        
        banana.setExit("left", stairs);
        banana.setExit("right", monky);
        
        monky.setExit("left", banana);
        monky.setExit("right", flood);
        
      
        flood.setExit("left", monky);
        
        
        banana.setItem(new Item("Banana"));
        
        monky.setCharacter(new Character(
                "Abe",  // Navn
                "En vred abe der ikke har fået banan i langtid", // Beskrivelse
                "Nu er jeg glad", // Når den er glad
                "Banana" // Hvad skal karateren have?
        ));
        
        // Set which room is current
        currentRoom = start;
        //currentRoomName = "start";
    }

    /**
     * Prints welcome message, runs the game, quit message.
     */
    public void play() {
        // Print welcome message
        printWelcome();

        // Game loop variable
        boolean finished = false;
        
        // Game loop
        while (! finished) {
            // Get new user input
            Command command = parser.getCommand();
            // Process command and if quit turn true
            finished = processCommand(command);
        }
        // Exit message
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print welcome message
     */
    private void printWelcome() {
        System.out.println();
        
        // Welcome message
         System.out.println(ANSI_GREEN + "Welcome to the World of Zuul!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "World of Zuul is a new, incredibly boring " +
                "adventure game." + ANSI_RESET);
        // Help message
        System.out.println(ANSI_RED + "Type '" + CommandWord.HELP + "' if you need help." + ANSI_RESET);
        System.out.println();
        
        // Current room message
        System.out.println(currentRoom.getLongDescription());
    }
       

    /**
     * Processes the user command and perform the action.
     * 
     * @param command Command, contains parsed user input.
     * @return boolean, true for quit.
     */
    private boolean processCommand(Command command) {
        // Declare and assign quit variable to false
        boolean wantToQuit = false;
        
        // Declare and assign first command word
        CommandWord commandWord = command.getCommandWord();

        // If unknown command, then error message
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        if(commandWord == CommandWord.INVENTORY){
            getInventory();
        }
        
        if(commandWord == CommandWord.PICKUP){
            getItem(command);
        }
        
        if(commandWord == CommandWord.GIVE){
            giveCharaterItem(command);
        }
        
        // If help command, print help message
        if (commandWord == CommandWord.HELP) {
            printHelp();
        // If go command, then call go method with parsed command
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        // If quit command, call quit method with parsed command
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        // Return quit variable, true if want to quit
        return wantToQuit;
    }

    /**
     * Print help message and show available commands.
     */
    private void printHelp() {
        // Help message
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        
        // Print available commands
        parser.showCommands();
    }

    /**
     * Go to another room according to parsed command.
     * 
     * @param command Command, parsed user commands.
     */
    private void goRoom(Command command) {
        // If no second parsed user command, print error and return
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        
        // Assign second command word as direction
        String direction = command.getSecondWord();
        // Assign which room to go to
        
        Room nextRoom = currentRoom.getExit(direction);
            
        //System.out.print(direction);
        /*
        if(currentRoom.isBlocked(direction)){
            
        }
        */
        
        System.out.println(ANSI_RED + "T" + ANSI_RESET);
        
        // If no room, write error message
        if (nextRoom == null) {
            System.out.println("There is no door!");
        // Set next room to current and print room description
        }else if(currentRoom.isBlocked(direction)){
            System.out.print("This path is blocked.");
        }else {
            currentRoom = nextRoom;
            if(direction.equals("right")){
                System.out.println(currentRoom.getLongDescription());
            }else if(direction.equals("left")){
                System.out.println(currentRoom.getLongDescriptionLeft());
            }
        }
    }
    

    /**
     * Confirm quit user request and return quit status.
     * 
     * @param command Command, parsed user command.
     * @return boolean, true for quit.
     */
    private boolean quit(Command command) {
        // If second command word, print error message and return false
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        // Otherwise return true
        } else {
            return true;
        }
    }
    
    private void giveCharaterItem(Command command){
        
        if(!command.hasSecondWord()) {
            System.out.println("Give to who?");
            return;
        }
        
        if(!command.hasThirdWord()) {
            System.out.println("Give what?");
            return;
        }
        
        Character getCharacter = currentRoom.getCharacter(command.getSecondWord());

        if(getCharacter == null){
            System.out.print("This charater is not here?");
        }else{
            if(getCharacter.getNeeds().equals(command.getThirdWord())){
                if(getItemInInventory(command.getThirdWord())){
                    getCharacter.setMood(1);
                }else{
                    System.out.println("You don't have this item.");
                }
            }else{
                System.out.println("You can't use this item on this charater.");
            }
        }
        
    }

    private void getInventory() {
        String output = "";
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("Du har i din inventory: ");
        System.out.println(output);
    }
    
    private boolean getItemInInventory(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getDescription().equals(itemName)){
                return true;
            }
        }
        return false;
    }


    private void getItem(Command command) {
        // If no second parsed user command, print error and return
        if(!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }
        
        // Assign second command word as direction
        String item = command.getSecondWord();
        // Assign which room to go to
        
        Item newItem = currentRoom.getItem(item);
       
        // If no room, write error message
        if (newItem == null) {
            System.out.println("That item is not here!");
        // Set next room to current and print room description
        } else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.print("Picked up: " + item);
        }
    }
}
