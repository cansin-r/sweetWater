package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class PokemonWorld {

    /** pokemonWorld */
    private TETile[][] pokemonWorld;
    private int pokemonWidth = Engine.WIDTH / 2;
    private int pokemonHeight = Engine.HEIGHT / 2;

    public PokemonWorld() {
        pokemonWorld = new TETile[Engine.WIDTH][Engine.HEIGHT];
        for (int w = 0; w < Engine.WIDTH; w++) {
            for (int h = 0; h < Engine.HEIGHT; h++) {
                pokemonWorld[w][h] = Tileset.WATER;
            }
        }
        for (int w = 20; w < pokemonWidth; w++) {
            for (int h = 5 ; h < pokemonHeight; h++) {
                pokemonWorld[w][h] = Tileset.SAND;
            }
        }
    }

    public TETile[][] getPokemonWorld() {
        return pokemonWorld;
    }
}
