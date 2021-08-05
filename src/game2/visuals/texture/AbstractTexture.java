package game2.visuals.texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractTexture implements Texture {

    private final int tileSize;

    protected boolean visible;
    protected AbstractTexture(int tileSize){
        this.visible = true;
        this.tileSize = tileSize;
    }

    /*public void setVisible(boolean visible){
        this.visible = visible;
    }*/

    protected abstract BufferedImage getImage();

    public void draw(Graphics2D g){
        if (!visible) return;
        Rectangle b = getBounds();
        g.drawImage(getImage(), b.x, b.y, b.width, b.height, null);
    }

    public void moveTiles(int dx, int dy){
        move(dx * tileSize, dy * tileSize);
    }

    public int getTileSize(){
        return tileSize;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}