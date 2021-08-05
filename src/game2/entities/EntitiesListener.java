package game2.entities;

import game2.visuals.ImageData;
import game2.visuals.texture.Texture;

import java.awt.*;

public interface EntitiesListener {

    void entityMoved(Point oldPos, Point newPos);

    void createTexture(Texture texture, int lifetime);

    void entityDied(Point pos);

    void removeTexture(Texture texture);
}
