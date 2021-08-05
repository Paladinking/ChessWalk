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
        Direction dir = Direction.values()[random.nextInt(4)];
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

    Point getRandomTile(Random random){
        return new Point(new Range(x + 1, x + width -1).getRandom(random), new Range(y + 1, y + height - 1).getRandom(random));
    }

    public void addToMap(int[][] map) {
        for (int x1 = x; x1 <= x + width; x1++) {
            for (int y1 = y; y1 <= y + height; y1++) {
                if ( x1 == x || x1 == x + width || y1 == y || y1 == y + height) map[x1][y1] = MapGenerator.WALL;
                else map[x1][y1] = MapGenerator.OPEN;
            }
        }
    }
}
