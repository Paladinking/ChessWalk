package Game.entities;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.actions.Action;
import Game.entities.actions.Movement;
import Game.entities.actions.Stand;
import Game.levels.Tilemap;

import java.awt.*;
import java.util.ArrayList;


public class Player {

    private static ArrayList<Action> actionQue;
    public static Point destination;
    public static Entity player;
    public static boolean done;

    public static void load(){
        actionQue = new ArrayList<>();
        destination = new Point(1,8);
        int imageX = 50,imageY= 375;
        Image.put(ImageID.PLAYER_ID,new Image(imageX,imageY,Image.PLAYER_FRONT));
        Player.player = new MovingEntity();
        player.currentAction = new Stand(player);
        Player.player.imgId = ImageID.PLAYER_ID;
        player.x = 1;
        player.y = 8;
    }
    public static void tick(){
        if(player.currentAction instanceof Stand){
            if (actionQue.size() == 0) {
                return;
            }
            player.currentAction = actionQue.get(0);
            actionQue.remove(0);
            if(player.currentAction instanceof Movement) {
                Image.getImage(ImageID.PLAYER_ID).setImg(Image.PLAYER_STILL[((Movement) player.currentAction).getDirection()]);
                if(Tilemap.getTile(((Movement)player.currentAction).getDeltaX()+player.x,((Movement)player.currentAction).getDeltaY()+player.y).getEntity()instanceof Enemy){
                    player.currentAction = new Stand(player);
                }
                player.currentAction.addEntity(player);

            }
        }
        if (player.currentAction == null){
            done = true;
            return;
        }

        player.currentAction.preformAction();
    }
    public static void addAction(Action a){
        actionQue.add(a);
    }
    public static void addMovements(ArrayList<Movement> m){
        for (Movement m1: m) {
            addAction(m1);
        }

    }
    public static void clearMovements(){
        actionQue.clear();
    }


    public static void newTurn() {
        done =false;
        if (actionQue.size()>0){
            player.currentAction=actionQue.get(0);
            actionQue.remove(0);
            if(player.currentAction instanceof Movement) {
                Image.getImage(ImageID.PLAYER_ID).setImg(Image.PLAYER_STILL[((Movement) player.currentAction).getDirection()]);
                if(Tilemap.getTile(((Movement)player.currentAction).getDeltaX()+player.x,((Movement)player.currentAction).getDeltaY()+player.y).getEntity()instanceof Enemy){
                    player.currentAction = new Stand(player);
                }
                player.currentAction.addEntity(player);
            }
        }else {
            player.currentAction = new Stand(player);
        }
    }
}

