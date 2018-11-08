package group13;

// Import section
import java.util.HashMap;

/**
 * Handles procedures with command word listing and validity.
 * 
 * @author Rasmus Willer
 */
public class CommandWords {
    // Declare HashMap variable
    private HashMap<String, CommandWord> validCommands;

    /**
     * No-arg constructor; make a collection of valid commands.
     */
    public CommandWords() {
        // Assign HashMap to variable
        validCommands = new HashMap<String, CommandWord>();
        // Iterate throught the command words and if not unknown, add to HashMap
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Check 1st command word and return CommandWord object with appropriate
     * constant.
     * 
     * @param commandWord String, 1st command word from user input
     * @return CommandWord, 1st command word constant
     */
    public CommandWord getCommandWord(String commandWord) {
        /* Assign command constant to CommandWord object, assign null if not a 
           valid command word */
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * Check command if valid.
     * 
     * @param aString String, command word to be checked.
     * @return boolean, true if valid command.
     */
    public boolean isCommand(String aString) {
        // Check for matching key in HashMap
        return validCommands.containsKey(aString);
    }

    /**
     * Print list of valid 1st command words.
     */
    public void showAll() {
        // Iterate through command words and print them
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
