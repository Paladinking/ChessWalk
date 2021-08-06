package game2.enums;

import java.awt.*;
import java.util.Random;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction opposite() {
        return Direction.values()[(ordinal() + 2) % values().length];
    }

    public static Direction random(Random random) {
        return Direction.values()[random.nextInt(values().length)];
    }

    public Direction randomOther(Random random) {
        return Direction.values()[(ordinal() + random.nextInt(3) + 1) % values().length];
    }

    public Point add(int x, int y, int amount){
        return new Point(x + amount * (ordinal() % 2) * (ordinal() - 2), y + amount * ((ordinal() + 1) % 2) * (ordinal() - 1));
    }

    public Direction next() {
        return Direction.values()[(ordinal() + 1) % values().length];
    }

    public Direction previous() {
        return Direction.values()[(ordinal() + 3) % values().length];
    }
}
