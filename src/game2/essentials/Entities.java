package game2.essentials;

import game2.actions.EntityAction;
import game2.entities.EntitiesListener;
import game2.entities.Entity;
import game2.entities.EntityListener;
import game2.entities.Player;
import game2.entities.enemies.Enemy;
import game2.entities.enemies.Knight;
import game2.entities.enemies.Skeleton;
import game2.entities.enemies.Slime;
import game2.levels.Level;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Entities {

    private boolean playerTurn;

    private Player player;

    private int toPass;

    private final List<Entity> entities;
    private final EntitiesListener listener;


    public Entities(EntitiesListener listener) {
        this.entities = new ArrayList<>();
        this.listener = listener;
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
        return enemy;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.setListener(new EntityAdapter(entity));
    }

    public void setPlayer(Player player){
        this.player = player;
        player.setListener(new EntityAdapter(player));
    }

    public void quePlayerAction(EntityAction action){
        player.queAction(action);
    }

    public void tickAll(TileMap tileMap) {
        System.out.println(playerTurn);
        if (playerTurn){
            player.tick();
        } else {
            for (Entity e : entities) e.tick();
        }
        if (toPass == 0) {
            if (playerTurn){
                playerTurn = false;
                toPass = entities.size();
                for (Entity e: entities) e.initTurn(tileMap);
            } else {
                playerTurn = true;
                toPass = 1;
                player.initTurn(tileMap);
            }
        }
    }

    public void clear() {
        entities.clear();
    }

    public Player getPlayer() {
        return player;
    }

    private class EntityAdapter implements EntityListener {

        private final Entity entity;

        private EntityAdapter(Entity entity){
            this.entity = entity;
        }
        @Override
        public void passedTurn() {
            toPass--;
        }

        @Override
        public void moved(Point oldPos) {
            listener.entityMoved(oldPos, entity.getPos());
        }
    }
}
