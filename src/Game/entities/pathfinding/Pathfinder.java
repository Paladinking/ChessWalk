package Game.entities.pathfinding;

import Game.GameState;
import Game.entities.Entity;
import Game.entities.Player;
import Game.entities.actions.Movement;
import Game.levels.Tilemap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathfinder {





    private static ArrayList<Movement> makeToMoves(List<Node> nodeList){
        Collections.reverse(nodeList);
        ArrayList<Movement> m = new ArrayList<>();
        for(int i = 0;i<nodeList.size()-1;i++){
            if(nodeList.get(i).getRow()>nodeList.get(i+1).getRow()){
                m.add(new Movement(-1,0));
            } else if(nodeList.get(i).getRow()<nodeList.get(i+1).getRow()){
                m.add(new Movement(1,0));
            } else if(nodeList.get(i).getCol()>nodeList.get(i+1).getCol()){
                m.add(new Movement(0,-1));
            } else if(nodeList.get(i).getCol()<nodeList.get(i+1).getCol()){
                m.add(new Movement(0,1));
            }
        }
        return m;
    }
    public static ArrayList<Movement> getMovement(int x1, int y1, int x2, int y2, Entity e){
        ArrayList<Movement> movement;
        Node finalNode = new Node(x1, y1);
        Node initialNode = new Node(x2, y2);
        AStar aStar = new AStar(GameState.boardWidth/GameState.tileSize, GameState.boardHeight/GameState.tileSize, initialNode, finalNode);
        aStar.setBlocks(Tilemap.getTiles(),e);
        List<Node> path = aStar.findPath();
        movement = Pathfinder.makeToMoves(path);
        if(e instanceof Player)((Player)e).destination = new Point(x2,y2);
        return movement;
    }

}
