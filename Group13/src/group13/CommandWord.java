package group13;

/**
 * Enum constant collection of 1st word commands.
 * 
 * @author Rasmus Willer
 */
public enum CommandWord {
    // Commands and their String plaintext
    GO("go"), 
    QUIT("quit"), 
    HELP("help"),
    COLLECT("collect"),
    GIVE("give"),
    INVENTORY("inventory"),
    PICKUP("pickup"),
    UNKNOWN("?");
    
    // Declare variable
    private String commandString;
    
    /**
     * Constructor; assign 1st command word.
     * 
     * @param commandString String, 1st command word
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }
    
    /**
     * Return 1st command word.
     * 
     * @return String, 1st command word
     */
    public String toString() {
        return commandString;
    }
}
