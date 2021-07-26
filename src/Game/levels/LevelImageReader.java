package Game.levels;

import Game.Board;
import Game.GameState;
import Game.entities.Enemy;
import Game.levels.Tiles.EmptyTile;
import Game.levels.Tiles.Tile;
import Game.levels.Tiles.WallTile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelImageReader {

    private static final String LEVEL_PATH = "levels/LevelImage/";


    @Deprecated
    public static Tile[][] readLevelImage(Level level) {
        Tile[][] tiles = new Tile[GameState.boardWidth/GameState.tileSize][GameState.boardHeight/GameState.tileSize];
        BufferedImage b;
        try {
            b = ImageIO.read(Board.class.getResource(level.filePath)).getSubimage(level.imageX,level.imageY,16,16);
        } catch (IOException e) {
            b = new BufferedImage(16,16,BufferedImage.TYPE_INT_RGB);
        }
        for (int i = 0; i <b.getWidth() ; i++) {
            for (int j = 0; j <b.getHeight() ; j++) {
                int hex = b.getRGB(i,j);
                if(hex==0xff000000){
                    tiles[j][i] = new WallTile();
                } else if(hex==0xffff0000){
                    tiles[j][i] = new EmptyTile();
                    Enemy e = level.getEnemy(i* GameState.tileSize,j*GameState.tileSize);
                    tiles[j][i].setEntity(e);
                    Enemy.add(e);
                } else {
                    tiles[j][i] = new EmptyTile();
                }
            }
        }
        return tiles;
    }
    public static Tile[][] generateLevel(Level level){
        Tile[][] tiles = new Tile[GameState.mapHeight][GameState.mapWidth];
        BufferedImage b;
        try {
            b = ImageIO.read(ClassLoader.getSystemResource(LEVEL_PATH + "Level1.bmp"));
        } catch (IOException e){
            b = new BufferedImage(GameState.mapWidth,GameState.mapHeight,BufferedImage.TYPE_INT_RGB);
        }
        for (int i = 0; i <b.getWidth() ; i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                int hex = b.getRGB(i, j);
                if (hex == 0xff000000) {
                    tiles[j][i] = new WallTile();
                } else if (hex == 0xffff0000) {
                    tiles[j][i] = new EmptyTile();
                    Enemy e = level.getEnemy((i) * GameState.tileSize, (j) * GameState.tileSize);
                    tiles[j][i].setEntity(e);
                    Enemy.add(e);
                } else {
                    tiles[j][i] = new EmptyTile();
                }
            }
        }

        return tiles;
    }

}
