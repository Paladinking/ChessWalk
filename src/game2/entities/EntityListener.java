package game2.entities;

import game2.visuals.ImageData;

import java.awt.Point;

public interface EntityListener {

    void passedTurn();

    void moved(Point oldPos);

    void died();

    void createTexture(ImageData blood);
}
