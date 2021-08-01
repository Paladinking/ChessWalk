package game2.levels;


import game2.entities.enemies.Enemy;


import java.awt.image.BufferedImage;
import java.util.Random;

public class Level {

    private static final int TILE_IMAGE = 0, WALL_IMAGE = 1;

    private static final Random RANDOM = new Random();
    public static final int[] LVL1_ENEMIES = {Enemy.SLIME, Enemy.SLIME, Enemy.SLIME, Enemy.SLIME, Enemy.KNIGHT};
    private static final int[] LVL2_ENEMIES = {Enemy.SKELETON,Enemy.SKELETON,Enemy.SKELETON,Enemy.SKELETON,Enemy.KNIGHT};

    public static final String PATH = "levels/LevelImage/";

    public final BufferedImage levelImage;

    private BufferedImage[] images;

    private final int[] enemies;

    public Level(BufferedImage levelImage, BufferedImage[] images, int[] enemies){
        this.levelImage = levelImage;
        this.enemies = enemies;
        this.images = images;
    }

    public BufferedImage getWallImage(){
        return images[WALL_IMAGE];
    }

    public BufferedImage getTileImage(){
        return images[TILE_IMAGE];
    }

    public int getEnemy(){
        return enemies[RANDOM.nextInt(enemies.length)];
    }

}
