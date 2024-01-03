import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    private static final int TILE_SIZE = 30;
    private static final int FPS = 30;
    private static final int SLEEP = 1000 / FPS;

    enum Tile {
        AIR,
        FLUX,
        UNBREAKABLE,
        PLAYER,
        STONE,
        FALLING_STONE,
        BOX,
        FALLING_BOX,
        KEY1,
        LOCK1,
        KEY2,
        LOCK2
    }

    enum Input {
        UP, DOWN, LEFT, RIGHT
    }

    public int playerX = 1;
    public int playerY = 1;
    public int[][] map = {
        {2, 2, 2, 2, 2, 2, 2, 2},
        {2, 3, 0, 1, 1, 2, 0, 2},
        {2, 4, 2, 6, 1, 2, 0, 2},
        {2, 8, 4, 1, 1, 2, 0, 2},
        {2, 4, 1, 1, 1, 9, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2},
    };

    public Queue<Input> inputs = new ArrayDeque<>();

    void remove(int tile) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[y][x] == tile) {
                    map[y][x] = Tile.AIR.ordinal();
                }
            }
        }
    }

    void moveToTile(int newX, int newY) {
        map[playerY][playerX] = Tile.AIR.ordinal();
        map[newY][newX] = Tile.PLAYER.ordinal();
        playerX = newX;
        playerY = newY;
    }

    void moveHorizontal(int dx) {
        if (map[playerY][playerX + dx] == Tile.FLUX.ordinal()
            || map[playerY][playerX + dx] == Tile.AIR.ordinal()) {
            moveToTile(playerX + dx, playerY);
        } else if ((map[playerY][playerX + dx] == Tile.STONE.ordinal()
            || map[playerY][playerX + dx] == Tile.BOX.ordinal())
            && map[playerY][playerX + dx + dx] == Tile.AIR.ordinal()
            && map[playerY + 1][playerX + dx] != Tile.AIR.ordinal()) {
            map[playerY][playerX + dx + dx] = map[playerY][playerX + dx];
            moveToTile(playerX + dx, playerY);
        } else if (map[playerY][playerX + dx] == Tile.KEY1.ordinal()) {
            remove(Tile.LOCK1.ordinal());
            moveToTile(playerX + dx, playerY);
        } else if (map[playerY][playerX + dx] == Tile.KEY2.ordinal()) {
            remove(Tile.LOCK2.ordinal());
            moveToTile(playerX + dx, playerY);
        }
    }

    void moveVertical(int dy) {
        if (map[playerY + dy][playerX] == Tile.FLUX.ordinal()
            || map[playerY + dy][playerX] == Tile.AIR.ordinal()) {
            moveToTile(playerX, playerY + dy);
        } else if (map[playerY + dy][playerX] == Tile.KEY1.ordinal()) {
            remove(Tile.LOCK1.ordinal());
            moveToTile(playerX, playerY + dy);
        } else if (map[playerY + dy][playerX] == Tile.KEY2.ordinal()) {
            remove(Tile.LOCK2.ordinal());
            moveToTile(playerX, playerY + dy);
        }
    }

    void update() {
        while (inputs.size() > 0) {
            Input current = inputs.poll();
            if (current == Input.LEFT) {
                moveHorizontal(-1);
            } else if (current == Input.RIGHT) {
                moveHorizontal(1);
            } else if (current == Input.UP) {
                moveVertical(-1);
            } else if (current == Input.DOWN) {
                moveVertical(1);
            }
        }

        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                if ((map[y][x] == Tile.STONE.ordinal() || map[y][x] == Tile.FALLING_STONE.ordinal())
                    && map[y + 1][x] == Tile.AIR.ordinal()) {
                    map[y + 1][x] = Tile.FALLING_STONE.ordinal();
                    map[y][x] = Tile.AIR.ordinal();
                } else if (
                    (map[y][x] == Tile.BOX.ordinal() || map[y][x] == Tile.FALLING_BOX.ordinal())
                        && map[y + 1][x] == Tile.AIR.ordinal()) {
                    map[y + 1][x] = Tile.FALLING_BOX.ordinal();
                    map[y][x] = Tile.AIR.ordinal();
                } else if (map[y][x] == Tile.FALLING_STONE.ordinal()) {
                    map[y][x] = Tile.STONE.ordinal();
                } else if (map[y][x] == Tile.FALLING_BOX.ordinal()) {
                    map[y][x] = Tile.BOX.ordinal();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

}
