import game.Game;

/**
 * The main Class of the program.
 *
 */
public class Ass5Game {
    /**
     * The main method of the program.
     *
     * @param  args   the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game(800, 600);
        game.initialize();
        game.run();
    }
}
