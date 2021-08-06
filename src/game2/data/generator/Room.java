package game2.data.generator;

import game2.enums.Direction;
import game2.essentials.Range;

import java.awt.*;
import java.util.*;

public class Room  {
    protected final int x, y, width, height;

    private final Rectangle bounds;

    Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x - 1, y - 1, width + 2, height + 2);
    }

    public boolean pointInRoom(int x, int y) {
        return (x > this.x && x < this.x + this.width && y > this.y && y <= this.y + this.height);
    }

    public boolean intersectsRoom(Room room) {
        return  bounds.intersects(room.bounds);
    }


    Point getMiddle(){
        return new Point(x + width / 2, y + height / 2);
    }

    Point getRandomEdge(Random random){
        Direction dir = Direction.random(random);
        if (dir == Direction.UP){
            return new Point(new Range(x + 1, x + width - 1).getRandom(random), y);
        } else if (dir == Direction.DOWN){
            return new Point(new Range(x + 1, x + width - 1).getRandom(random), y + height);
        } else if (dir == Direction.LEFT){
            return new Point(x, new Range(y + 1, y +height -1).getRandom(random));
        } else {
            return new Point(x + width, new Range(y + 1, y +height -1).getRandom(random));
        }
    }

    public void openWall(int[][] map, Direction direction){
        switch (direction){
            case RIGHT -> {
                for (int y1 = y + 1; y1 < y + height; y1++){
                    map[x][y1] = MapGenerator.OPEN;
                }
            }
            case LEFT -> {
                for (int y1 = y + 1; y1 < y + height; y1++){
                    map[x + width][y1] = MapGenerator.OPEN;
                }
            }
            case UP -> {
                for (int x1 = x + 1; x1 < x + width; x1++){
                    map[x1][y] = MapGenerator.OPEN;
                }
            }
            case DOWN -> {
                for (int x1 = x + 1; x1 < x + width; x1++){
                    map[x1][y + height] = MapGenerator.OPEN;
                }
            }
        }
    }



    Point getRandomTile(Random random){
        return new Point(new Range(x + 1, x + width -1).getRandom(random), new Range(y + 1, y + height - 1).getRandom(random));
    }

    public void addToMap(int[][] map) {
        for (int x1 = x; x1 <= x + width; x1++) {
            for (int y1 = y; y1 <= y + height; y1++) {
                if (x1 == x || x1 == x + width || y1 == y || y1 == y + height) map[x1][y1] = MapGenerator.WALL;
                else map[x1][y1] = MapGenerator.OPEN;
            }
        }
    }

    public void addSafeToMap(int[][] map) {
        for (int x1 = x; x1 <= x + width; x1++) {
            for (int y1 = y; y1 <= y + height; y1++) {
                if (x1 < 0 || x1 >= map.length || y1 < 0 || y1 >= map[0].length) continue;
                if ( x1 == x || x1 == x + width || y1 == y || y1 == y + height) map[x1][y1] = MapGenerator.WALL;
                else map[x1][y1] = MapGenerator.OPEN;
            }
        }
    }
}
