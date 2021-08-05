package game2.essentials;

import game2.actions.EntityAction;
import game2.entities.*;
import game2.levels.Level;
import game2.visuals.ImageData;

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

    public void setPlayer(Player player, Level level){
        this.player = player;
        player.setListener(new EntityAdapter(player){
            @Override
            public void moved(Point oldPos){
                super.moved(oldPos);
                level.updateLighting();
            }
        });
    }

    public void quePlayerAction(EntityAction action, Level tileMap){
        player.queAction(action, tileMap);
    }

    public void tickAll(Level tileMap) {
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
                if (toPass == 0){
                    playerTurn = true;
                    toPass = 1;
                    player.initTurn(tileMap);
                }
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
            entities.remove(entity);
            listener.entityDied(entity.getPos());
        }

        @Override
        public void createTexture(ImageData blood) {
            listener.createTexture(blood, entity.getPos());
        }
    }
}
