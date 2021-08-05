package game2.entities;

import game2.visuals.ImageData;

import java.awt.*;

public interface EntitiesListener {

    void entityMoved(Point oldPos, Point newPos);

    void createTexture(ImageData blood, Point pos);

    void entityDied(Point pos);
}
