package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/** Class which generates the Random World
 * @author Cansin Rodoplu */
public class RandomWorldGenerator {

    /** Instance Variables */
    private Random rand;
    private long seed;
    /** the generated random world TD tile array.*/
    private TETile[][] randomWorld;
    /** ArrayList which stores the x coordinate of the maze.
     * Width, Height coordinate can be obtained through indexing.*/
    private ArrayList<Integer> widthCoordinatesMaze;
    /** ArrayList storing the y coordinates of the maze. */
    private ArrayList<Integer> heightCoordinatesMaze;
    /** Boolean 2D array storing the current rooms in generated world.*/
    private Boolean[][] rectanglesInWorld;
    /** Maximum number of rectangular rooms in the World*/
    private int roomNumber;
    /** The x axis of the starting coordinate of the Avatar*/
    private int avatarStartWidth;
    /** The y axis of the starting coordinate of the Avatar*/
    private int avatarStartHeight;
    /** The x coordinate of the Avatar at a given time during the game.*/
    private int avatarWidth;
    /** The y coordinate of the Avatar at a given time during the game.*/
    private int avatarHeight;
    /** The list of movements*/
    private String movements;

    public RandomWorldGenerator(String input) {
        String seedNumbers = input.replaceAll("[^0-9]", "");
        seed = Long.parseLong(seedNumbers);
        movements = input.replaceAll("[^SAWD]", "");
        rand = new Random(seed);
        randomWorld = new TETile[Engine.WIDTH][Engine.HEIGHT];
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                randomWorld[i][j] = Tileset.TREE;
            }
        }
        widthCoordinatesMaze = new ArrayList<>();
        heightCoordinatesMaze = new ArrayList<>();
        rectanglesInWorld = new Boolean[Engine.WIDTH][Engine.HEIGHT];
        for (int w = 0; w < Engine.WIDTH; w++) {
            for (int h = 0; h < Engine.HEIGHT; h++) {
                rectanglesInWorld[w][h] = false;
            }
        }
    }

    /** randomMaze generates a random maze.
     * Randomness ensured through the command argument provided by the user.*/
    public void randomMaze() {


        int startingHeight = rand.nextInt(2, Engine.HEIGHT);
        int startingWidth = rand.nextInt(2, Engine.WIDTH);
        randomWorld[startingWidth][startingHeight] = Tileset.FLOOR;
        for (int i = 0; i < Engine.WIDTH * Engine.HEIGHT * 2000; i++) {
            int direction = Math.abs(rand.nextInt() % 4);
            switch (direction) {
                case 0:
                    if (startingHeight + 6 < Engine.HEIGHT) {
                        if (randomWorld[startingWidth][startingHeight + 1].equals(Tileset.TREE)
                                && randomWorld[startingWidth][startingHeight + 3].equals(
                                        Tileset.TREE)) {
                            randomWorld[startingWidth][startingHeight + 1] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight + 2] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight + 3] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight + 4] = Tileset.FLOOR;
                            startingHeight += 4;
                        }
                    }
                    break;
                case 1:
                    if (startingHeight - 6 > 0) {
                        if (randomWorld[startingWidth][startingHeight - 3].equals(Tileset.TREE)) {
                            randomWorld[startingWidth][startingHeight - 1] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight - 2] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight - 3] = Tileset.FLOOR;
                            randomWorld[startingWidth][startingHeight - 4] = Tileset.FLOOR;
                            startingHeight -= 4;
                        }
                    }
                    break;
                case 2:
                    if (startingWidth + 7 < Engine.WIDTH) {
                        if (randomWorld[startingWidth + 3][startingHeight].equals(Tileset.TREE)) {
                            randomWorld[startingWidth + 1][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth + 2][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth + 3][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth + 4][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth + 5][startingHeight] = Tileset.FLOOR;

                            startingWidth += 5;
                        }
                    }
                    break;
                case 3:
                    if (startingWidth - 7 > 0) {
                        if (randomWorld[startingWidth - 3][startingHeight].equals(Tileset.TREE)) {

                            randomWorld[startingWidth - 1][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth - 2][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth - 3][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth - 4][startingHeight] = Tileset.FLOOR;
                            randomWorld[startingWidth - 5][startingHeight] = Tileset.FLOOR;

                            startingWidth -= 5;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        mazeCoordinatesHelper();
    }

    /**
     * Takes in a which only has maze generated;
     * fills in the widthCoordinatesMaze and heightCoordinatesMaze ArrayLists.*/
    public void mazeCoordinatesHelper() {
        for (int w = 0; w < Engine.WIDTH; w++) {
            for (int h = 0; h < Engine.HEIGHT; h++) {
                if (randomWorld[w][h].equals(Tileset.FLOOR)) {
                    widthCoordinatesMaze.add(w);
                    heightCoordinatesMaze.add(h);
                }
            }
        }
    }

    /** generates a random number of rooms and assignes it
     *  to roomNumber considering the length of the maze*/
    public void roomNumberGenerator() {
        roomNumber = rand.nextInt(4, widthCoordinatesMaze.size()/2);
    }

    /** Main method generating all the rectangular rooms in the World*/
    public void generateRooms() {
        for (int i = 0; i < roomNumber; i++) {
            int heightRoom = rand.nextInt(2, 6);
            int widthRoom = rand.nextInt(2, 8);
            int randomCoordinateIndex = rand.nextInt(widthCoordinatesMaze.size());
            int coordinateWidth = widthCoordinatesMaze.get(randomCoordinateIndex);
            int coordinateHeight = heightCoordinatesMaze.get(randomCoordinateIndex);
            if (coordinateWidth - (widthRoom / 2) - 1 > 0
                    && coordinateWidth + (widthRoom / 2) + 1 < Engine.WIDTH
                    && coordinateHeight - heightRoom / 2 - 1 > 0
                    && coordinateHeight + heightRoom / 2 + 1 < Engine.HEIGHT) {
                if (isValidRoom(widthRoom, heightRoom, coordinateWidth, coordinateHeight)) {
                    for (int w = coordinateWidth - widthRoom / 2;
                         w < coordinateWidth + widthRoom / 2; w++) {
                        for (int h = coordinateHeight - heightRoom / 2;
                             h < coordinateHeight + heightRoom / 2; h++) {
                            randomWorld[w][h] = Tileset.FLOOR;
                            rectanglesInWorld[w][h] = true;
                        }
                    }
                }
            }
        }
    }

    /** Returns true if the proposed room with width and height
     * can be drawn at the given coordinates without being
     * superposed with other rooms.
     */
    public boolean isValidRoom(int roomWidth, int roomHeight,
                               int coordinateWidth, int coordinateHeight) {
        for (int w = coordinateWidth - roomWidth / 2 - 1;
             w < coordinateWidth + roomWidth / 2 + 1; w++) {
            for (int h = coordinateHeight - roomHeight / 2 - 1;
                 h < coordinateHeight + roomHeight / 2 + 1; h++) {
                if (rectanglesInWorld[w][h]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** This method builds the hallways of our Random World*/
    public void hallwayBuilder() {
        for (int w = 1; w < Engine.WIDTH - 1; w++) {
            for (int h = 1; h < Engine.HEIGHT - 1; h++) {
                if (isHallway(w, h)) {
                    randomWorld[w][h] = Tileset.FLOWER;
                }
            }
        }
    }

    /** Helper method for hallwayBuilder. Takes in coordinates of a tile in randomWorld
     * Returns true if that tile is supposed to be a Hallway.
     * */
    public boolean isHallway(int width, int height) {
        if (randomWorld[width][height].equals(Tileset.TREE)) {
            for (int w = width - 1; w <= width + 1; w++) {
                for (int h = height - 1; h <= height + 1; h++) {
                    if (randomWorld[w][h].equals(Tileset.FLOOR)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    /** calls in the necessary methods to create the World.*/
    public void createWorld() {
        randomMaze();
        roomNumberGenerator();
        generateRooms();
        hallwayBuilder();
        setAvatar();
        setPokemon();
        parseMovements();
    }

    /**
     * Sets the Avatar at the Start of the Game
     */
    public void setAvatar() {
        for (int w = 0; w < Engine.WIDTH; w++) {
            for (int h = 0; h < Engine.HEIGHT; h++) {
                if (randomWorld[w][h].equals(Tileset.FLOOR)) {
                    avatarStartWidth = w;
                    avatarStartHeight = h;
                    break;
                }
            }
        }
        randomWorld[avatarStartWidth][avatarStartHeight] = Tileset.AVATAR;
        avatarWidth = avatarStartWidth;
        avatarHeight = avatarStartHeight;
    }

    public void setPokemon() {
        int randomPokemonWidth = rand.nextInt(2, Engine.WIDTH-2);
        int randomPokemonHeight = rand.nextInt(2, Engine.HEIGHT-2);
        while (!(randomWorld[randomPokemonWidth][randomPokemonHeight].equals(Tileset.FLOOR))) {
            randomPokemonWidth = rand.nextInt(2, Engine.WIDTH-2);
            randomPokemonHeight = rand.nextInt(2, Engine.HEIGHT-2);
        }
        randomWorld[randomPokemonWidth][randomPokemonHeight] = Tileset.WATER;
    }

    /**
     * Parses the movements in the movements string
     * and calls the necessary methods in order to move the avatar
     */
    public void parseMovements() {
        for (int i = 0; i < movements.length(); i++) {
            char m = movements.charAt(i);
            if (m == 'W') {
                moveUp();
            }
            if (m == 'A') {
                moveLeft();
            }
            if (m == 'S') {
                moveDown();
            }
            if (m == 'D') {
                moveRight();
            }
        }
    }

    public void moveUp() {
        if (randomWorld[avatarWidth][avatarHeight + 1].equals(Tileset.FLOOR)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth][avatarHeight + 1] = Tileset.AVATAR;
            avatarHeight += 1;
        } else if (randomWorld[avatarWidth][avatarHeight + 1].equals(Tileset.WATER)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth][avatarHeight + 1] = Tileset.AVATAR;
            avatarHeight += 1;
            Engine.isPokemon = true;
        }

    }

    public void moveLeft() {
        if (randomWorld[avatarWidth - 1][avatarHeight].equals(Tileset.FLOOR)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth - 1][avatarHeight] = Tileset.AVATAR;
            avatarWidth -= 1;
        } else if (randomWorld[avatarWidth - 1][avatarHeight].equals(Tileset.WATER)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth - 1][avatarHeight] = Tileset.AVATAR;
            avatarWidth -= 1;
            Engine.isPokemon = true;
        }

    }

    public void moveRight() {
        if (randomWorld[avatarWidth + 1][avatarHeight].equals(Tileset.FLOOR)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth + 1][avatarHeight] = Tileset.AVATAR;
            avatarWidth += 1;
        } else if (randomWorld[avatarWidth + 1][avatarHeight].equals(Tileset.WATER)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth + 1][avatarHeight] = Tileset.AVATAR;
            avatarWidth += 1;
            Engine.isPokemon = true;
        }
    }

    public void moveDown() {
        if (randomWorld[avatarWidth][avatarHeight - 1].equals(Tileset.FLOOR)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth][avatarHeight - 1] = Tileset.AVATAR;
            avatarHeight -= 1;
        } else if (randomWorld[avatarWidth][avatarHeight - 1].equals(Tileset.WATER)) {
            randomWorld[avatarWidth][avatarHeight] = Tileset.FLOOR;
            randomWorld[avatarWidth][avatarHeight - 1] = Tileset.AVATAR;
            avatarHeight -= 1;
            Engine.isPokemon = true;
        }
    }


    /** getter methods */
    public TETile[][] getRandomWorld() {
        return randomWorld;
    }

    public static void main(String[] input) {

        Engine.TER.initialize(Engine.WIDTH, Engine.HEIGHT);
        RandomWorldGenerator createWorld = new RandomWorldGenerator(input[0]);
        createWorld.createWorld();
        Engine.TER.renderFrame(createWorld.getRandomWorld());

    }

}
