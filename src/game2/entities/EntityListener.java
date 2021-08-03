package game2.entities;

import java.awt.Point;

public interface EntityListener {

    void passedTurn();

    void moved(Point oldPos);

    void died();
}
