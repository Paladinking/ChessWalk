package Game.entities;

import Game.assets.Image;
import Game.assets.ImageID;
import Game.entities.actions.Movement;
import Game.levels.Tilemap;

import java.awt.*;
import java.util.ArrayList;


public class Player {
    private static int  xCoor, yCoor,xMoved, yMoved;

    private static ArrayList<Movement> movementQue;
    private static Movement currentMovement;
    public static Point destination;
    public static Entity player;
    public static boolean done;
    private static int testGit;

    public static void load(){
        movementQue = new ArrayList<>();
        currentMovement = new Movement(0,0);
        xCoor = 8;
        yCoor = 8;
        destination = new Point(xCoor,yCoor);
        int imageX = 400,imageY= 375;
        Image.put(ImageID.PLAYER_ID,new Image(imageX,imageY,Image.PLAYER_FRONT));
    }
    public static void tick(){

        if (currentMovement.getDirection() == Movement.STILL){
            if (movementQue.size() == 0) {
                //Image.remove(ImageID.SELECTED_SQUARE_ID);
                return;
            }
            currentMovement = movementQue.get(0);
            movementQue.remove(0);

        }
        Image.getImage(ImageID.PLAYER_ID).setImg(Image.PLAYER_STILL[currentMovement.getDirection()]);
        int dX = 0,dY = 0;
        switch (currentMovement.getDirection()){
            case Movement.UP:
                dY = -5;
                currentMovement.deltaY+=5;
                break;
            case Movement.DOWN:
                dY = 5;
                currentMovement.deltaY-=5;
                break;
            case Movement.LEFT:
                dX = -5;
                currentMovement.deltaX+=5;
                break;
            case Movement.RIGHT:
                dX = 5;
                currentMovement.deltaX-=5;
                break;
        }
        yMoved += dY;
        xMoved += dX;
        if(yMoved==-50){
            yCoor--;
            yMoved = 0;
            Tilemap.getTile(xCoor,yCoor).pressed(player);
            done = true;
        } else if(yMoved == 50){
            yCoor ++;
            yMoved = 0;
            Tilemap.getTile(xCoor,yCoor).pressed(player);
            done = true;
        } else if(xMoved ==-50){
            xCoor --;
            xMoved =0;
            Tilemap.getTile(xCoor,yCoor).pressed(player);
            done = true;
        } else if(xMoved == 50){
            xCoor++;
            xMoved =0;
            Tilemap.getTile(xCoor,yCoor).pressed(player);
            done = true;
        }

        Image.getImage(ImageID.PLAYER_ID).moveImage(dX,dY);
        if (currentMovement.getDirection() == Movement.STILL) {
            if (movementQue.size() == 0) {
                Image.remove(ImageID.SELECTED_SQUARE_ID);
            }
        }
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
        if (xMoved==0&&yMoved==0) {
            currentMovement.clear();
            Player.destination = new Point(xCoor,yCoor);
        } else if(yMoved==0){
            currentMovement.deltaX = (xMoved/Math.abs(xMoved))*(50-Math.abs(xMoved));
            Player.destination= new Point(xCoor+(xMoved/Math.abs(xMoved)),yCoor);
        } else if (xMoved == 0){
            currentMovement.deltaY = (yMoved/Math.abs(yMoved))*(50-Math.abs(yMoved));
            Player.destination= new Point(xCoor,yCoor+(yMoved/Math.abs(yMoved)));
        }
        movementQue.clear();


    }


}

