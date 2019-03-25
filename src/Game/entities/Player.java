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

    private static ArrayList<Movement> movementQue;
    public static Point destination;
    public static Entity player;
    public static boolean done;

    public static void load(){
        movementQue = new ArrayList<>();
        destination = new Point(8,8);
        int imageX = 400,imageY= 375;
        Image.put(ImageID.PLAYER_ID,new Image(imageX,imageY,Image.PLAYER_FRONT));
        Player.player = new MovingEntity();
        player.currentAction = new Stand();
        Player.player.imgId = ImageID.PLAYER_ID;
        player.x = 8;
        player.y = 8;
    }
    public static void tick(){
        if(player.currentAction instanceof Stand){
            if (movementQue.size() == 0) {
                return;
            }
            player.currentAction = movementQue.get(0);
            movementQue.remove(0);
        }
        if (player.currentAction == null){
            done = true;
            return;
        }
        if(player.currentAction instanceof Movement) {
            Image.getImage(ImageID.PLAYER_ID).setImg(Image.PLAYER_STILL[((Movement) player.currentAction).getDirection()]);
        }
        player.currentAction.preformAction();
    }
    private static void addMovement(Movement m){
        movementQue.add(m);
    }
    public static void addMovements(ArrayList<Movement> m){
        for (Movement m1: m) {
            addMovement(m1);
        }

    }
    public static void clearMovements(){
        movementQue.clear();
    }


    public static void newTurn() {
        done =false;
        if (movementQue.size()>0){
            player.currentAction=movementQue.get(0);
            movementQue.remove(0);
        }else {
            player.currentAction = new Stand();
        }
    }
}

