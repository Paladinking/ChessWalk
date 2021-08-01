package game2.entities;

import game2.entities.enemies.Enemy;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.levels.Level;

import java.util.*;

public class Entities {

    private final List<Entity> entities;

    public Entities() {
        this.entities = new ArrayList<>();
    }

    public Enemy generateEnemy(Level level, int x, int y) {
        int id = level.getEnemy();
        Enemy enemy;
        switch (id) {
            case Enemy.SLIME:
                enemy = new Slime(x, y);
                break;
            case Enemy.KNIGHT:
                enemy = new Knight(x, y);
                break;
            case Enemy.SKELETON:
                enemy = new Skeleton(x, y);
                break;
            default:
                enemy = null;
        }
        if (enemy == null) return null;
        createEntity(enemy);
        return enemy;
    }

    public void createEntity(Entity entity) {
        entities.add(entity);
    }

    public void tickAll() {
        for (Entity e : entities) e.tick();
    }

    public void clear() {
        entities.clear();
    }
}
