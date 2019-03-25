package Game.levels;

import Game.entities.Enemy;
import Game.entities.Knight;
import Game.entities.Slime;

public class Level {
    private static int[] LVL1_ENEMIES = {Enemy.SLIME,Enemy.SLIME,Enemy.SLIME,Enemy.SLIME,Enemy.KNIGHT};

    private static final Level LEVEL_1 = new Level(".idea/assets/Levels/LevelImage/Level1-4.bmp",0,0, LVL1_ENEMIES);
    private static final Level LEVEL_2 = new Level(".idea/assets/Levels/LevelImage/Level1-4.bmp",16,0,new int[5]);
    private static final Level LEVEL_3 = new Level(".idea/assets/Levels/LevelImage/Level1-4.bmp",32,0, new int[5]);
    private static final Level LEVEL_4 = new Level(".idea/assets/Levels/LevelImage/Level1-4.bmp",48,0, new int[5]);

    public static final Level[] LEVELS = {LEVEL_1,LEVEL_2,LEVEL_3,LEVEL_4};
    final String filePath;
    final int imageX,imageY;

    private final int[] enemies;

    private Level(String filePath, int imageX, int imageY, int[] enemies){
        this.filePath = filePath;
        this.imageX = imageX;
        this.imageY = imageY;
        this.enemies = enemies;
    }
    Enemy getEnemy(int x,int y){
        int r = (int)(Math.random()*5);
        return getEnemy(enemies[r],x,y);
    }
    private static Enemy getEnemy(int i,int x,int y){
        switch (i){
            case Enemy.SLIME:
                return new Slime(x,y);
            case Enemy.KNIGHT:
                return new Knight(x,y);
            default:
                return new Enemy();
        }
    }

}
