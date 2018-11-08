package group13;

// Import section
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Take user input, parse it and get command words.
 * 
 * @author Rasmus Willer
 */
public class Parser {
    // Declare variables
    private CommandWords commands;
    private Scanner reader;

    /**
     * No-arg constructor; assign command words and Scanner listener.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Take user input and parse them to first and second command word.
     * 
     * @return Command, object with parsed commands.
     */
    public Command getCommand() {
        // Declare and assign method variables
        String inputLine;
        String word1 = null;
        String word2 = null;
        String word3 = null;

        // Symbols to signal waiting for user input
        System.out.print("> ");

        // Call for listen for input from next line
        inputLine = reader.nextLine();

        // Assign received input to variable
        Scanner tokenizer = new Scanner(inputLine);
        // If there is a first word, assign to variable
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            // if second word, assign to variable
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }
        
        // Return command object with parsed user commands
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    /**
     * Print available commands.
     */
    public void showCommands() {
        // Call for print available commands
        commands.showAll();
    }
}
