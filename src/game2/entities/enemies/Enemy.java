package game2.entities.enemies;

import game2.entities.Entity;

public abstract class Enemy extends Entity {
    public static final int SLIME = 0, KNIGHT = 1, SKELETON = 2;

    protected Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    protected static class EnemyData {
        final int width, height, hp, speed;
        protected EnemyData(int width, int height, int hp, int speed) {
            this.width = width;
            this.height = height;
            this.hp = hp;
            this.speed = speed;
        }
    }
}
