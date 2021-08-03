package game2.essentials;

import game2.actions.EntityAction;
import game2.entities.*;

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

    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.setListener(new EntityAdapter(entity));
    }

    public void setPlayer(Player player){
        this.player = player;
        player.setListener(new EntityAdapter(player));
    }

    public void quePlayerAction(EntityAction action, TileMap tileMap){
        player.queAction(action, tileMap);
    }

    public void tickAll(TileMap tileMap) {
        if (playerTurn){
            player.tick(tileMap);
        } else {
            for (Entity e : entities) e.tick(tileMap);
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

        @Override
        public void died() {

        }
    }
}
