# Sweet Water Design Document


This algorithm creates a random world, dependent on a seed number that the user inputs at the beginning of the game. There are two components to the game:
First of all, the user can explore the world using s (down), d (right), a (left), and a (up) letters as commands.
Second of all, the user can enter a new world -- the sweet water world -- if they find the water icon in the random old world.
The algorithm also makes use of persistence as previous worlds and the status of the player can be saved.

# Classes
All the classes are in the Core folder inside of the byow (stands for build your own world) folder.

## Main.java Class
This is the main entry point for the program. This class simply parses the command line inputs, and lets the byow.Core.Engine class take over in either keyboard or input string mode.

## Engine.java Class
This class has two methods instrumental in creating the random world.

interactWithInputString(String input): This method allows you to simulate user interaction by providing a series of keyboard inputs as a string. It returns a 2D array of TETile objects representing the state of the universe after processing all the key presses provided in the input.

interactWithKeyboard(): This method enables real-time user interaction by reading input directly from the keyboard. It draws the result of each key press to the screen as you interact with the system.
This class is equally key in the persistence process as it saves and reopens the last version of the game if the player wants to.

## RandomWorldGenerator.java
This class creates the random world using the seed number put in by the player of the game.

## PokemonWorld.java
This class creates the pokemon world -- which is not a random world.

