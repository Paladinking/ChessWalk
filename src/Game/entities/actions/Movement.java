package Game.entities.actions;

import Game.entities.Entity;
import Game.entities.Pathfinder;
import Game.entities.Player;
import Game.entities.pathfinding.AStar;
import Game.entities.pathfinding.Node;
import Game.levels.Tilemap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Movement  {
     public static final int UP = 0, DOWN = 1,LEFT = 2,RIGHT = 3, STILL = 4;

    public int deltaX,deltaY;
    public void clear(){
        deltaY = 0;
        deltaX = 0;
    }
    public int getDirection(){
        if (deltaX>0) return RIGHT;
        if (deltaX<0) return LEFT;
        if (deltaY>0) return DOWN;
        if (deltaY<0) return UP;
        return STILL;
    }
    public Movement(int deltaX, int deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }


    public static ArrayList<Movement> getMovement(int x1, int y1, int x2, int y2, Entity e){
        ArrayList<Movement> movement;
        Node finalNode = new Node(x1, y1);
        Node initialNode = new Node(x2, y2);
        AStar aStar = new AStar(16, 16, initialNode, finalNode);
        aStar.setBlocks(Tilemap.getTiles(),e);
        List<Node> path = aStar.findPath();
        movement = Pathfinder.makeToMoves(path);
        Player.destination = new Point(x2,y2);
        return movement;
    }
}