package byow.Core;
import byow.InputDemo.InputSource;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Cansin Rodoplu
 */
public class MainMenu implements InputSource {

    public MainMenu() {
        StdDraw.text(0.5, 0.7, "CS61B: The Game");
        StdDraw.text(0.5, 0.45, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.35, "Quit Game (Q)");
    }
    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
