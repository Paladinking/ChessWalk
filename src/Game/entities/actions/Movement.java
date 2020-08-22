package Game.entities.actions;

import Game.GameState;
import Game.assetClasses.SoundManager;
import Game.entities.Entity;
import Game.levels.Tilemap;

public class Movement extends Action {
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, STILL = 4;
    private int deltaX, deltaY;

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getDirection() {
        if (deltaX > 0) return RIGHT;
        if (deltaX < 0) return LEFT;
        if (deltaY > 0) return DOWN;
        if (deltaY < 0) return UP;
        return STILL;
    }

    public Movement(int deltaX, int deltaY, Entity e) {
        this(deltaX,deltaY);
        this.addEntity(e);
    }

    public Movement(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Movement(Movement m, Entity e) {
        this(m.deltaX, m.deltaY, e);
    }


    @Override
    public void preformAction() {
        entity.getImage().adjImage(deltaX *  GameState.initialSize/ 10f, deltaY * GameState.initialSize / 10f);
        super.preformAction();
    }

    @Override
    public int tickLength() {
        return 8;
    }

    @Override
    public void beforeAction() {
        Tilemap.removeEntity(entity.getX(), entity.getY());
        entity.setX(entity.getX() + deltaX);
        entity.setY(entity.getY() + deltaY);
        entity.getImage().adjImage(- GameState.initialSize*deltaX,- GameState.initialSize*deltaY);
        Tilemap.addEntity(entity.getX(), entity.getY(), entity);
        entity.playSound(SoundManager.MOVE);
    }

    @Override
    public void afterAction() {
        Tilemap.getTile(entity.getX(), entity.getY()).pressed(entity);
        super.afterAction();
    }
}