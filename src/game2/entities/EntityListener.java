package game2.entities;

import java.awt.*;

public interface EntityListener {

    void passedTurn();

    void moved(Point oldPos);
}
