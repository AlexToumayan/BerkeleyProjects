# DungeonForge

DungeonForge is a tile-based dungeon explorer game built in Java. The player navigates through a procedurally generated world and interacts with various elements within the dungeon. 

The main functionalities of DungeonForge include:
- Procedural world generation
- Keyboard-based interactions
- Visualization of the game world
- Saving and loading game states

## Game Flow and Main Methods

The game initializes with a main menu, which allows players to start a new game, load a saved game, or quit the game. When a new game is chosen, the player is prompted to enter a seed for the world generation. The seed is a string followed by 'S' or 's', which is used as input for the `generateWorld` method to create the game world.

Each room of the dungeon is placed randomly, and each room is filled with floor tiles and surrounded by wall tiles. There are also corridors connecting the rooms. The `placeUnlockedDoor` method places an unlocked door in a random room. The `placeLockedDoors` method places locked doors on the walls surrounding the room with the unlocked door.

The player avatar can move around the dungeon by using the W (up), A (left), S (down), D (right) keys. The `moveAvatar` method handles these movements. When attempting a move, the `isValidMove` method checks whether the destination tile is a wall or an empty space, and only allows the move if the tile is neither.

The dungeon is covered with fog of war, which is removed as the avatar moves around, revealing the dungeon layout. This is handled by the `updateFogOfWar` method.

When a tile is revealed, its information is displayed in the game UI. This is handled by the `displayTileInfo` method.

## Usage

Compile and run the game using a Java IDE or the command line by running the main method in the `Engine` class.

The player interacts with the game primarily through the keyboard. The main menu, new game creation, and player movement all rely on keyboard inputs.

## Game Saving and Loading

DungeonForge supports saving and loading game states. The game state is saved when the player inputs ':Q' or ':q'. The player can load the saved game state from the main menu by choosing the 'Load Game (L)' option.
