package game2.visuals.texture;

import java.awt.*;

public class HpBar implements Texture {

    private float hpFraction;

    private final Rectangle bounds;

    public HpBar(float hpFraction, int tileSize, int x, int y) {
        this.hpFraction = hpFraction;
        this.bounds = new Rectangle(x, y, tileSize, 5);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.setColor(Color.red);
        g.fill(bounds);
        g.setColor(Color.green);
        g.fillRect(bounds.x, bounds.y, (int) (bounds.width * hpFraction), bounds.height);
    }

    @Override
    public void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public int getZ() {
        return 2;
    }

    @Override
    public void tick() {

    }

    public void setHpFraction(float hpFraction) {
        this.hpFraction = hpFraction;
    }
}
