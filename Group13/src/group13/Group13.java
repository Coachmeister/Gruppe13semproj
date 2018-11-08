package group13;

/**
 * Launcher for the game.
 * 
 * @author Rasmus Willer
 */
public class Group13 {

    /**
     * Starting point of game. Instantiates game and calls to play it.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instantiate Game
        Game game = new Game();
        // Run game
        game.play();
    }
}
