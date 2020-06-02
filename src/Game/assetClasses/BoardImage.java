package Game.assetClasses;

import Game.GameState;

import java.awt.*;

public class BoardImage extends Image {
    public BoardImage(int xCoor, int yCoor, java.awt.Image i) {
        super(xCoor+GameState.offsetX, yCoor-GameState.tileSize+GameState.offsetY, i);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) xCoor, (int) (yCoor + GameState.tileSize),GameState.boardWidth,GameState.boardHeight, null);
    }

}