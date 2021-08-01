package game2.visuals;

import game2.entities.Entity;
import game2.entities.Player;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Images {

    private static final String IMAGE_PATH = "images/", ENEMIES = IMAGE_PATH + "Enemies/",
            LEVELS = "levels/LevelImage/", PLAYER = IMAGE_PATH + "Player/";
    public BufferedImage level1, wall1, tile1;

    private Map<Class<? extends Entity>, BufferedImage> images;

    private boolean loaded;

    public Images() {
        this.loaded = false;
        this.images = new HashMap<>();
    }

    public void load() throws IOException {
        if (loaded) return;
        images.put(Slime.class, ImageIO.read(ClassLoader.getSystemResource(ENEMIES + "Slime.png")));
        images.put(Skeleton.class, ImageIO.read(ClassLoader.getSystemResource(ENEMIES + "Skeleton.png")));
        images.put(Knight.class, ImageIO.read(ClassLoader.getSystemResource(ENEMIES + "Knight.png")));
        images.put(Player.class, ImageIO.read(ClassLoader.getSystemResource(PLAYER + "PlayerFront.png")));
        wall1 = ImageIO.read(ClassLoader.getSystemResource(IMAGE_PATH + "Walls/WallFront.png"));
        tile1 = ImageIO.read(ClassLoader.getSystemResource(IMAGE_PATH + "Tile.png"));
        level1 = ImageIO.read(ClassLoader.getSystemResource(LEVELS + "Level1.bmp"));
        this.loaded = true;
    }

    public BufferedImage getImage(Class<? extends Entity> c){
        return images.get(c);
    }
}
