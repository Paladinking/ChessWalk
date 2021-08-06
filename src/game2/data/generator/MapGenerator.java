package game2.data.generator;

import game2.essentials.Range;


import java.awt.*;
import java.util.*;
import java.util.List;

import static game2.Dungeon.THE_RANDOM;


public class MapGenerator {

    private static final int MAX_TRIES = 100;

    private final int roomCount, enemyCount;

    private final Range roomSize, mapX, mapY;

    private final List<Room> rooms;

    public final static int HOLE = 0, OPEN = 1, WALL = 2, PATH = 3, PLAYER_START = 4, ENEMY = 5;

    public MapGenerator(int rooms, int width, int height, Range roomSize, int enemyCount) {
        this.roomCount = rooms;
        this.mapX = new Range(0, width);
        this.mapY = new Range(0, height);
        this.roomSize = roomSize;
        this.enemyCount = enemyCount;
        this.rooms = new ArrayList<>();
    }

    public int[][] generate() {
        int[][] map = new int[mapX.max][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new int[mapY.max];
        }
        if (roomCount == 0) return map;
        List<Room> mapParts = new ArrayList<>();
        Room room;
        int tries = 0;
        forLoop: for (int i = 0; i < roomCount; i++) {
            boolean redo;
            do {
                if (tries > MAX_TRIES) break forLoop;
                room = generateRoom();
                redo = false;
                checkRedo:
                {
                    for (Room part : mapParts) {
                        if (part.intersectsRoom(room)) {
                            tries++;
                            redo = true;
                            break checkRedo;
                        }
                    }
                    for (Room part : rooms) {
                        if (part.intersectsRoom(room)) {
                            tries++;
                            redo = true;
                            break checkRedo;
                        }
                    }
                }
            } while (redo);
            mapParts.add(room);
        }
        for (Room part : mapParts) {
            part.addToMap(map);
        }
        for (Room part : rooms) {
            System.out.println(part.x + ", "+ part.y + ", " + part.width + ", " + part.height);
            part.addToMap(map);
        }
        List<Room> toConnect = new ArrayList<>(mapParts);
        Room r1 = toConnect.remove(0);
        toConnect.addAll(rooms);
        while (toConnect.size() > 0) {
            Room r2 = toConnect.remove(0);
            List<Point> path = new RoomConnector().getPath(map, r1.getMiddle(), r2.getMiddle());
            if (path == null) {
                System.out.println("BERRY BAD ");
                continue;
            }
            for (Point p : path) {
                map[p.x][p.y] = PATH;
            }
        }
        final Point[] surroundingPoints = new Point[]{new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
                new Point(0, -1), new Point(0, 1), new Point(1, -1), new Point(1, 0), new Point(1, 1)};
        for (int x = 1; x < mapX.max - 1; x++) {
            for (int y = 1; y < mapY.max - 1; y++) {
                if (map[x][y] != 0) continue;
                for (Point p : surroundingPoints) {
                    if (map[x + p.x][y + p.y] == PATH) {
                        map[x][y] = WALL;
                        break;
                    }
                }
            }
        }
        Point start = r1.getMiddle();
        map[start.x][start.y] = PLAYER_START;

        mapParts.remove(r1);
        for (int i = 0; i < enemyCount; i++) {
            Room r = mapParts.get(THE_RANDOM.nextInt(mapParts.size()));
            Point enemyPos = r.getRandomTile(THE_RANDOM);
            if (map[enemyPos.x][enemyPos.y] == OPEN || map[enemyPos.x][enemyPos.y] == PATH) {
                map[enemyPos.x][enemyPos.y] = ENEMY;
            } else {
                i--;
            }
        }
        return map;
    }

    private Room generateRoom() {
        int x = mapX.getRandom(THE_RANDOM), y = mapY.getRandom(THE_RANDOM);
        int width = roomSize.getRandom(THE_RANDOM), height = roomSize.getRandom(THE_RANDOM);
        while (x + width >= mapX.max) x = mapX.getRandom(THE_RANDOM);
        while (y + height >= mapY.max) y = mapY.getRandom(THE_RANDOM);
        return new Room(x, y, width, height);
    }

    public static void printMap(int[][] map) {
        int height = map[0].length;
        for (int i = 0; i < height; i++) {
            StringBuilder s = new StringBuilder();
            for (int[] ints : map) {
                if (ints[i] == WALL) s.append("X ");
                else if (ints[i] == PATH) s.append("P ");
                else s.append("  ");
            }
            System.out.println(s.toString());
        }
    }


    public void addRoom(Room room) {
        rooms.add(room);
    }
}
