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
   public void draw(Graphics g){
        if(hidden) return;
        int width = img.getWidth(null)*GameState.tileSize/GameState.initialSize;
        int height = img.getHeight(null)*GameState.tileSize/GameState.initialSize;
        Graphics2D g2d = (Graphics2D)g;
        float tx = GameState.globalAdjX -GameState.globalX*GameState.tileSize + adjX * GameState.tileSize / (1f*GameState.initialSize) + xCoor + GameState.offsetX+width/2f;
        float ty = GameState.globalAdjY - GameState.globalY*GameState.tileSize + adjY * GameState.tileSize / (1f*GameState.initialSize) + yCoor + GameState.offsetY+height/2f;
        g2d.translate(tx, ty);
        g2d.rotate(r);
        g2d.drawImage(img,-width/2,-height/2,width,height,null);
        g2d.rotate(-r);
        g2d.translate(-tx,-ty);
    }
    public void rotate(double r){
        this.r+=r;
    }
    public void setRotate(double r){
        this.r = r;
    }
}
