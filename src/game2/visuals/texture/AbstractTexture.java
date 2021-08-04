package game2.visuals.texture;

import game2.enums.TextureState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractTexture implements Texture {

    protected boolean visible;
    protected AbstractTexture(){
        this.visible = true;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    protected abstract BufferedImage getImage();

    public void draw(Graphics2D g){
        if (!visible) return;
        Rectangle b = getBounds();
        g.drawImage(getImage(), b.x, b.y, b.width, b.height, null);
    }

    public abstract int getZ();

    public abstract void tick();
}