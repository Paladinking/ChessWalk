package Game.levels;

import Game.assets.Image;
import Game.entities.Enemy;
import Game.levels.Tiles.EmptyTile;
import Game.levels.Tiles.Tile;
import Game.levels.Tiles.WallTile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelImageReader {


    public static Tile[][] readLevelImage(Level level) {
        Tile[][] tiles = new Tile[16][16];
        BufferedImage b;
        try {
            b = ImageIO.read(new File(level.filePath)).getSubimage(level.imageX,level.imageY,16,16);
        } catch (IOException e) {
            b = new BufferedImage(16,16,BufferedImage.TYPE_INT_RGB);
        }
        for (int i = 0; i <b.getWidth() ; i++) {
            for (int j = 0; j <b.getHeight() ; j++) {
                int hex = b.getRGB(i,j);
                if(hex==0xff000000){
                    tiles[j][i] = new WallTile(i,j, Image.WALL_FRONT);
                } else if(hex==0xffff0000){
                    tiles[j][i] = new EmptyTile();
                    Enemy e = level.getEnemy(i*50,j*50);
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
