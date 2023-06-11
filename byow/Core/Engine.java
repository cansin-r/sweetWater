package byow.Core;

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;


import java.io.File;
import java.io.Serializable;


public class Engine implements Serializable {
    public static final TERenderer TER = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private String seedString = "";
    /** the new world*/
    private TETile[][] newWorld;
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** file in which the text that is the seed number is stored*/
    public static final File SEEDFILE = Utils.join(CWD, "savedFile.txt");
    private String description = "";
    private boolean gameStarted = false;
    private int mouseX;
    private int mouseY;
    public static boolean isPokemon = false;
    private boolean pokemonEncountered = false;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource mainMenu;
        mainMenu = new MainMenu();
        while (mainMenu.possibleNextInput()) {
            char c = mainMenu.getNextKey();
            if (c == 'N') {
                System.out.println("New Game");
                seedString += c;
                seedDraw();
            } else if (c == 'L') {
                String contents = new String(Utils.readContents(SEEDFILE));
                seedString = contents;
                newWorld = interactWithInputString(seedString);
                TER.initialize(Engine.WIDTH, Engine.HEIGHT + 5);
                gameStarted = true;
                TER.renderFrame(newWorld);

            } else if (c == 'Q') {
                Utils.writeContents(SEEDFILE, seedString);
                System.exit(0);
            }
            if (c == '1' || c == '0' || c == '2' || c == '3'
                    || c == '4' || c == '5' || c == '9' || c == '6'
                    || c == '7' || c == '8') {
                seedString += c;
                StdDraw.text(0.5, 0.4, seedString);
                StdDraw.pause(500);
                StdDraw.clear();
                seedDraw();
            }
            if (c == 'S' && !(seedString.contains("S"))) {
                StdDraw.clear();
                StdDraw.text(0.5, 0.6, "SEED : ");
                StdDraw.text(0.5, 0.4, seedString);
                StdDraw.pause(1000);
                gameStarted = true;
                TER.initialize(WIDTH, HEIGHT + 5);
                newWorld = interactWithInputString(seedString);
                TER.renderFrame(newWorld);
            }
            while (gameStarted) {
                mouseX = (int) Math.round(StdDraw.mouseX());
                mouseY = (int) Math.round(StdDraw.mouseY());
                if (mouseY < HEIGHT && mouseX < WIDTH && mouseX >= 0 && mouseY >= 0) {
                    description = newWorld[mouseX][mouseY].description();
                }
                if (StdDraw.hasNextKeyTyped()) {
                    c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (c == 'W' || c == 'A' || c == 'S' || c == 'D') {
                        seedString += c;
                        newWorld = interactWithInputString(seedString);
                        if (isPokemon && !pokemonEncountered) {
                            PokemonWorld pok = new PokemonWorld();
                            TER.renderFrame(pok.getPokemonWorld());
                            StdDraw.text(10, 43, "Welcome to the Game Island!");
                            StdDraw.show();
                            StdDraw.pause(3000);
                            pokemonEncountered = true;
                        }
                    }
                    if (c == ':') {
                        break;
                    }
                }
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.text(10, 42, description);
                StdDraw.text(10, 43, "Find Water to travel to the Game Island!");
                StdDraw.show();
                StdDraw.pause(100);
                TER.renderFrame(newWorld);
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        RandomWorldGenerator randomWorld = new RandomWorldGenerator(input);
        randomWorld.createWorld();
        TETile[][] finalWorldFrame = randomWorld.getRandomWorld();
        return finalWorldFrame;
    }

    public String getSeedString() {
        return seedString;
    }

    public static void seedDraw() {
        StdDraw.clear();
        StdDraw.text(0.5, 0.6, "ENTER SEED NUMBER AND PRESS S TO START GAME");
    }
}
