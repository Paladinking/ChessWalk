package game2.entities;

import game2.visuals.texture.Texture;

import java.awt.Point;

public interface EntityListener {

    void passedTurn();

    void moved(Point oldPos);

    void died();

    void createTexture(Texture texture, int lifeTime);

    void removeTexture(Texture texture);
}
