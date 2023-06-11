# Build Your Own World Design Document

**Partner 1:**
Cansin Rodoplu

This algorithm creates a random world, dependent on a seed number that the user inputs at the beginning of the game. There are two components to the game:
First of all, the user can explore the world using s (down), d (right), a (left), and a (up) letters as commands.
Second of all, the user can enter a new world if they find the water icon in the random old world.

## Classes and Data Structures
The RandomWorldGenerator Class in Core will be the main driver for generating my Random world.

The randomness is derived through the string argument passed in by the user.

The RandomWorldGenerator Class contains the following instance variables:

* rand --> random object
* int seed

Random object gets initialized in the RandomWorldGenerator constructor which parses the string to find the integer and sets it as seed.


## Algorithms

## Persistence
