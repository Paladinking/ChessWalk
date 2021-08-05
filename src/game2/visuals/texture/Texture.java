package game2.visuals.texture;

import java.awt.*;

public interface Texture {

    void draw(Graphics2D g);

    void move(int dx, int dy);

    Rectangle getBounds();

    int getZ();

    void tick();

}
