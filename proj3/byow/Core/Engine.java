package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    private TETile[][] world;
    private Avatar avatar;
    private int numberOfRooms;
    private int[][] roomCenters;
    private TETile[][] fogWorld;

    private void saveGame() {
        try {
            File saveFile = new File("save.txt");
            FileWriter writer = new FileWriter(saveFile);
            writer.write(avatar.getX() + " " + avatar.getY() + "\n");

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    writer.write(world[x][y].description() + "\t");
                }
                writer.write("\n");
            }

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    writer.write(fogWorld[x][y].description() + "\t");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        try {
            File saveFile = new File("save.txt");
            Scanner scanner = new Scanner(saveFile);

            int avatarX = scanner.nextInt();
            int avatarY = scanner.nextInt();
            scanner.nextLine();

            world = new TETile[WIDTH][HEIGHT];
            fogWorld = new TETile[WIDTH][HEIGHT];

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    String tileDescription = scanner.next();
                    world[x][y] = TETile.byDescription(tileDescription);
                }
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }

            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    String tileDescription = scanner.next();
                    fogWorld[x][y] = TETile.byDescription(tileDescription);
                }
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }

            this.avatar = new Avatar(avatarX, avatarY);

            world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;

            ter.initialize(WIDTH, HEIGHT);
            renderFogFrame();

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TETile[][] generateWorld(String input) {
        this.world = new TETile[WIDTH][HEIGHT];
        fillWorldWithEmptyTiles(world);
        String seedString = input.replaceAll("[^0-9]", "");
        long seed = Long.parseLong(seedString);
        Random random = new Random(seed);

        this.fogWorld = new TETile[WIDTH][HEIGHT];
        fillWorldWithFog(fogWorld);

        generateRoomsAndHallways(world, random);
        int unlockedDoorRoomIndex = placeUnlockedDoor(world, random);
        placeLockedDoors(world, unlockedDoorRoomIndex);
        int[] avatarStart = roomCenters[random.nextInt(numberOfRooms)];
        avatar = new Avatar(avatarStart[0], avatarStart[1]);
        world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;


        updateFog(avatar.getX(), avatar.getY());
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
        StdDraw.show();
        return world;
    }
    private void fillWorldWithFog(TETile[][] fogWorld) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                fogWorld[x][y] = Tileset.UNKNOWN;
            }
        }
    }
    private void fillWorldWithEmptyTiles(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    private void generateRoomsAndHallways(TETile[][] world, Random random) {
        numberOfRooms = random.nextInt(9) + 6;
        roomCenters = new int[numberOfRooms][2];
        double hexagonRoomProbability = 0.2; // 20% chance to create a hexagon-shaped room

        for (int i = 0; i < numberOfRooms; i++) {
            if (random.nextDouble() < hexagonRoomProbability) {
                int roomSize = random.nextInt(4) + 2;
                int roomX = random.nextInt(WIDTH - 2 * roomSize - 4) + 2;
                int roomY = random.nextInt(HEIGHT - 2 * roomSize - 4) + 2;


                createHexagonRoom(world, roomX, roomY, roomSize);
                roomCenters[i] = new int[]{roomX + roomSize, roomY + roomSize};
            } else {
                int roomWidth = random.nextInt(5) + 3;
                int roomHeight = random.nextInt(5) + 3;
                int roomX = random.nextInt(WIDTH - roomWidth - 4) + 2;
                int roomY = random.nextInt(HEIGHT - roomHeight - 4) + 2;


                createRoom(world, roomX, roomY, roomWidth, roomHeight);
                roomCenters[i] = new int[]{roomX + roomWidth / 2, roomY + roomHeight / 2};
            }
        }
        for (int i = 0; i < numberOfRooms - 1; i++) {
            int[] start = roomCenters[i];
            int[] end = roomCenters[i + 1];
            createHallway(world, start[0], start[1], end[0], end[1], random);
        }
    }
    private void createHexagonRoom(TETile[][] world, int x, int y, int size) {
        for (int i = 0; i < size * 2; i++) {
            int startX = x + Math.abs(size - 1 - i);
            int endX = x + 2 * size - Math.abs(size - 1 - i) - 2;

            for (int j = startX; j <= endX; j++) {
                if (i == 0 || i == size * 2 - 1 || j == startX || j == endX) {
                    world[j][y + i] = Tileset.WALL;
                } else {
                    world[j][y + i] = Tileset.FLOOR;
                }
            }
        }
    }
    private void createRoom(TETile[][] world, int x, int y, int width, int height) {
        for (int i = x - 1; i <= x + width; i++) {
            for (int j = y - 1; j <= y + height; j++) {
                if (i == x - 1 || i == x + width || j == y - 1 || j == y + height) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }
    private void createHallway(TETile[][] world, int x1, int y1, int x2, int y2, Random random) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int x = x1;
        int y = y1;

        boolean horizontalFirst = random.nextBoolean();

        if (horizontalFirst) {
            while (x != x2) {
                placeFloorAndWalls(world, x, y);
                if (dx > 0) {
                    x++;
                } else {
                    x--;
                }
            }
            while (y != y2) {
                placeFloorAndWalls(world, x, y);
                if (dy > 0) {
                    y++;
                } else {
                    y--;
                }
            }
        } else {
            while (y != y2) {
                placeFloorAndWalls(world, x, y);
                if (dy > 0) {
                    y++;
                } else {
                    y--;
                }
            }
            while (x != x2) {
                placeFloorAndWalls(world, x, y);
                if (dx > 0) {
                    x++;
                } else {
                    x--;
                }
            }
        }
    }
    private void placeFloorAndWalls(TETile[][] world, int x, int y) {
        if (world[x][y] != Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    if (world[x + xOffset][y + yOffset] == Tileset.NOTHING) {
                        world[x + xOffset][y + yOffset] = Tileset.WALL;
                    }
                }
            }
        }
    }
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        displayMainMenu();

        boolean gameInProgress = false;

        while (!gameInProgress) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 'n') {
                    promptAndGenerateWorld();
                    gameInProgress = true;
                } else if (key == ':' && gameInProgress) {
                    saveGame();
                } else if (key == 'l') {
                    loadGame();
                    renderFogFrame();
                    gameInProgress = true;
                } else if (key == 'q') {
                    System.exit(0);
                }
            }
        }

        boolean needsRendering = true;

        // Main game loop
        while (true) {
            // Render the frame only if needed
            if (needsRendering) {
                renderFogFrame();
                needsRendering = false;
            }

            // Handle user input
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                boolean avatarMoved = false;

                if (key == ':') {
                    saveGame();
                } else if (key == 'w') {
                    int newX = avatar.getX();
                    int newY = avatar.getY() + 1;
                    if (isValidMove(world, newX, newY)) {
                        world[avatar.getX()][avatar.getY()] = Tileset.FLOOR;
                        avatar.updatePosition(newX, newY);
                        world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;
                        avatarMoved = true;
                    }
                } else if (key == 'a') {
                    int newX = avatar.getX() - 1;
                    int newY = avatar.getY();
                    if (isValidMove(world, newX, newY)) {
                        world[avatar.getX()][avatar.getY()] = Tileset.FLOOR;
                        avatar.updatePosition(newX, newY);
                        world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;
                        avatarMoved = true;
                    }
                } else if (key == 's') {
                    int newX = avatar.getX();
                    int newY = avatar.getY() - 1;
                    if (isValidMove(world, newX, newY)) {
                        world[avatar.getX()][avatar.getY()] = Tileset.FLOOR;
                        avatar.updatePosition(newX, newY);
                        world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;
                        avatarMoved = true;
                    }
                } else if (key == 'd') {
                    int newX = avatar.getX() + 1;
                    int newY = avatar.getY();
                    if (isValidMove(world, newX, newY)) {
                        world[avatar.getX()][avatar.getY()] = Tileset.FLOOR;
                        avatar.updatePosition(newX, newY);
                        world[avatar.getX()][avatar.getY()] = Tileset.fireAvatar;
                        avatarMoved = true;
                    }
                } else if (key == 'q') {
                    System.exit(0);
                }


                if (avatarMoved) {
                    updateFog(avatar.getX(), avatar.getY());
                    needsRendering = true;
                }
            }
            // Handle mouse clicks
            if (StdDraw.isMousePressed()) {
                int mouseX = (int) StdDraw.mouseX();
                int mouseY = (int) StdDraw.mouseY();
                if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT && fogWorld[mouseX][mouseY] != Tileset.UNKNOWN) {
                    displayTileInfo(mouseX, mouseY);
                    needsRendering = true;
                }
            }
        }
    }
    private void renderFogFrame() {
        TETile[][] renderWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (fogWorld[x][y] == Tileset.UNKNOWN) {
                    renderWorld[x][y] = fogWorld[x][y];
                } else {
                    renderWorld[x][y] = world[x][y];
                }
            }
        }
        ter.renderFrame(renderWorld);
    }
    private void updateFog(int x, int y) {
        for (int xOffset = -2; xOffset <= 2; xOffset++) {
            for (int yOffset = -2; yOffset <= 2; yOffset++) {
                int newX = x + xOffset;
                int newY = y + yOffset;
                if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT) {
                    fogWorld[newX][newY] = world[newX][newY];
                }
            }
        }
    }
    private boolean isValidMove(TETile[][] world, int x, int y) {
        return world[x][y] != Tileset.WALL && world[x][y] != Tileset.NOTHING;
    }
    private void displayMainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        Font titleFont = new Font("Monaco", Font.BOLD, 40);
        Font menuFont = new Font("Monaco", Font.PLAIN, 20);
        Color titleColor = new Color(255, 0, 0);

        // Draw the title
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(titleColor);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "DungeonForge");

        // Draw the menu options
        StdDraw.setFont(menuFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 1 / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 1 / 2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 1 / 2 - 4, "Quit (Q)");
        StdDraw.show();
    }

    private void promptAndGenerateWorld() {
        StringBuilder input = new StringBuilder();
        boolean inputComplete = false;

        while (!inputComplete) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == '\b' && input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                } else {
                    input.append(key);
                }

                if (key == 'N' || key == 'n') {
                    displaySeedPrompt(input.toString());
                } else if (key == 'S' || key == 's') {
                    inputComplete = true;
                } else {
                    displaySeedPrompt(input.toString());
                }
            }
        }
        TETile[][] world = generateWorld(input.toString());
        ter.initialize(WIDTH, HEIGHT);
        boolean needsRendering = true;
    }

    private int placeUnlockedDoor(TETile[][] world, Random random) {
        int roomIndex = random.nextInt(numberOfRooms);
        int[] roomCenter = roomCenters[roomIndex];

        int x = roomCenter[0];
        int y = roomCenter[1];

        world[x][y] = Tileset.UNLOCKED_DOOR;
        fogWorld[x][y] = Tileset.UNLOCKED_DOOR; // Reveal the unlocked door in fogWorld
        return roomIndex;
    }

    private void placeLockedDoors(TETile[][] world, int unlockedDoorRoomIndex) {
        int[] unlockedRoomCenter = roomCenters[unlockedDoorRoomIndex];

        // Iterate over the wall tiles surrounding the room containing the unlocked door
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                int x = unlockedRoomCenter[0] + xOffset;
                int y = unlockedRoomCenter[1] + yOffset;

                // If the current tile is a wall, check for floor tiles with walls on parallel sides
                if (world[x][y] == Tileset.WALL) {
                    if ((world[x + 1][y] == Tileset.FLOOR && world[x - 1][y] == Tileset.FLOOR) ||
                            (world[x][y + 1] == Tileset.FLOOR && world[x][y - 1] == Tileset.FLOOR)) {
                        world[x][y] = Tileset.LOCKED_DOOR;
                        fogWorld[x][y] = Tileset.LOCKED_DOOR; // Reveal the locked door in fogWorld
                    }
                }
            }
        }
    }





    private void displayTileInfo(int x, int y) {
        // Check if the tile is visible in the fogWorld
        if (fogWorld[x][y] != Tileset.UNKNOWN) {
            // Get the actual tile from the world (not the fogWorld)
            TETile tile = world[x][y];
            String tileInfo = tile.description();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(WIDTH - 98, HEIGHT - 2, tileInfo);
            StdDraw.show();
        }
    }

    private void displaySeedPrompt(String input) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter seed (followed by 'S'):");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, input);
        StdDraw.show();
    }

    public TETile[][] interactWithInputString(String input) {
        return generateWorld(input);
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.interactWithKeyboard();
    }
}
