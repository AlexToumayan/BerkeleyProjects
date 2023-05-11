package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile FLOOR = new TETile('·', new Color(255, 255, 255), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile LIGHT = new TETile('♦', new Color(255, 255, 0), Color.black, "light");
    public static final TETile COIN = new TETile('¢', new Color(212, 175, 55), Color.black, "coin");
    public static final TETile WALL = new TETile('O', Color.red,
            Color.white, "WALL",
            "/Users/alextoumayan/Desktop/CS61BFall2022/fa22-s1355/proj3/byow/TileEngine/Background.png");
    public static final TETile fireAvatar = new TETile('O', Color.red,
            Color.white, "you",
            "/Users/alextoumayan/Desktop/CS61BFall2022/fa22-s1355/proj3/byow/TileEngine/Avatar.png");
    public static final TETile UNKNOWN = new TETile(' ', Color.BLACK, Color.BLACK, "unknown");

}


