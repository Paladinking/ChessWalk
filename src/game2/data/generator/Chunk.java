package game2.data.generator;

import game2.enums.Direction;
import game2.essentials.Range;
import game2.essentials.TileMap;

public class Chunk {

    public static int CHUNK_DIMENSION;

    private final Chunk previous;

    public final Direction next;

    private TileMap tileMap;

    public Chunk(Chunk previous, Direction next) {
        this.previous = previous;
        this.next = next;
    }

    public int[][] generate() {
        MapGenerator generator = new MapGenerator(10, CHUNK_DIMENSION, CHUNK_DIMENSION, new Range(4, 10), 0);
        Room exit, entrance;
        exit = getEdgeRoom(next);
        if (previous != null) {
            entrance = getEdgeRoom(previous.next.opposite());
            generator.addRoom(entrance);
        } else {
            entrance = null;
        }
        generator.addRoom(exit);
        int[][]map = generator.generate();
        if (entrance != null) entrance.openWall(map, previous.next.opposite());
        exit.openWall(map, next);
        MapGenerator.printMap(map);
        return map;
    }

    public void setTileMap(TileMap tileMap){
        this.tileMap = tileMap;
    }
    /*
    private static final int[] xValues = {9, 0, 9, 22};
    private static final int[] yValues = {0, 9, 22, 9};
    private static final int[] width = {5, 2, 5, 2};
    private static final int[] heights = {2, 5, 2, 5};

    private static Room getEdgeRoom2(Direction direction){
        int o = direction.ordinal();
        return new Room(xValues[o], yValues[o], width[o], heights[o]);
    }
     */

    private static Room getEdgeRoom(Direction direction) {
        int width = 7, height = 4,
                a = CHUNK_DIMENSION / 2 - width / 2, b = CHUNK_DIMENSION / 2 - height / 2,
                c = (direction.ordinal() + 1) % 2, d = direction.ordinal() % 2,
                x = (c * a) + d * (b + b * (direction.ordinal() - 2)),
                y = (d * a) + c * (b + b * (direction.ordinal() - 1)),
                w = d * height + c * (width - 1),
                h = d * (width - 1) + c * height;
        return new Room(x, y, w, h);
    }


    public static void clearPaths(int[][] map) {
        for (int[] mapPart : map) {
            for (int i = 0; i < mapPart.length; i++) {
                if (mapPart[i] == MapGenerator.PATH) mapPart[i] = MapGenerator.OPEN;

            }
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }
}
