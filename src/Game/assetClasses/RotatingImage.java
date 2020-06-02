package Game.assetClasses;

import Game.GameState;

import java.awt.*;

public class RotatingImage extends Image {
    private double r;
    public RotatingImage(int xCoor, int yCoor, java.awt.Image i){
        super(xCoor,yCoor,i);
        r=0;
    }
    @Override
    void draw(Graphics g){
        if(hidden) return;
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(GameState.offsetX +xCoor+adjX+width/2f,GameState.offsetY+yCoor+adjY+height/2f);
        g2d.rotate(r);
        g2d.drawImage(img,-width/2,-height/2,null);
        g2d.rotate(-r);
        g2d.translate(-GameState.offsetX-xCoor-adjX-width/2f,-GameState.offsetY-yCoor-adjY-height/2f);
    }
    public void rotate(double r){
        this.r+=r;
    }
    public void setRotate(double r){
        this.r = r;
    }
}
