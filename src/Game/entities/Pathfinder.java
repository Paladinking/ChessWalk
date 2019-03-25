package Game.entities;

import Game.entities.actions.Movement;
import Game.entities.pathfinding.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathfinder {
    private int[][] visitedTiles;
    private  ArrayList<Movement> moves;
    private ArrayList<Point> trimmedPath;
    private ArrayList<Point> points;

    private boolean foundPath;
    public static void load(){

    }
    Pathfinder(){

    }
    public Pathfinder(int xs, int ys, int xw, int yw, ArrayList<Point> path){
        path.add(new Point(xs,ys));
        visitedTiles = new int[16][16];
        foundPath = false;
        pathfind(xs,ys,xw,yw,path);
    }
    public static ArrayList<Movement> getMovement(int xs,int ys,int xw,int yw){
        Pathfinder p  = new Pathfinder(xs,ys,xw,yw,new ArrayList<>());
        p.trimPath(p.points);

        return p.makeMoves(p.trimmedPath);
    }
    public static ArrayList<Movement> makeToMoves(List<Node> nodeList){
        Collections.reverse(nodeList);
        ArrayList<Movement> m = new ArrayList<>();
        for(int i = 0;i<nodeList.size()-1;i++){
            if(nodeList.get(i).getRow()>nodeList.get(i+1).getRow()){
                m.add(new Movement(-50,0));
            } else if(nodeList.get(i).getRow()<nodeList.get(i+1).getRow()){
                m.add(new Movement(50,0));
            } else if(nodeList.get(i).getCol()>nodeList.get(i+1).getCol()){
                m.add(new Movement(0,-50));
            } else if(nodeList.get(i).getCol()<nodeList.get(i+1).getCol()){
                m.add(new Movement(0,50));
            }
        }
        return m;
    }
    private ArrayList<Point> getNextTo(Point p){
        ArrayList<Point> ps = new ArrayList<Point>();
        ps.add(new Point(p.x-1,p.y));
        ps.add(new Point(p.x+1,p.y));
        ps.add(new Point(p.x,p.y+1));
        ps.add(new Point(p.x,p.y-1));
        return ps;
    }
    public ArrayList<Movement> makeMoves(ArrayList<Point> p){
        ArrayList<Movement> m = new ArrayList<>();
        for(int i = 0;i<p.size()-1;i++){
            if(p.get(i).x>p.get(i+1).x){
                m.add(new Movement(-50,0));
            } else if(p.get(i).x<p.get(i+1).x){
                m.add(new Movement(50,0));
            } else if(p.get(i).y>p.get(i+1).y){
                m.add(new Movement(0,-50));
            } else if(p.get(i).y<p.get(i+1).y){
                m.add(new Movement(0,50));
            }
        }
        return m;
    }
    private void trimPath(ArrayList<Point> path){
        for(int i=0;i<path.size();i++){
            ArrayList<Point> path21 = getNextTo(path.get(i));
            for(int k = 0;k<path21.size();k++){
                if(path.contains(path21.get(k))&&path.indexOf(path21.get(k))-i>1){
                    int aj = path.indexOf(path21.get(k));
                    for (int j = i+1;j<aj;){
                        path.remove(j);
                          aj = path.indexOf(path21.get(k));


                    }
                    trimPath(path);
                    return;
                }
            }

        }
        trimmedPath = new ArrayList<Point>(path);
    }
    private void pathfind(int xs, int ys, int xw, int yw, ArrayList<Point> path){
        /*
        if(foundPath) return;
        if (path.get(path.size()-1).x!=xs||(path.get(path.size()-1).y!=ys)) path.add(new Point(xs, ys));
        if(xs == xw&&ys==yw){
            points = path;
            foundPath = true;
        }
            visitedTiles[ys][xs] = 1;
        if (Math.abs(xw-xs)>Math.abs(yw-ys)) {
            if (xw>xs&& Tilemap.getTile(xs+1,ys).selected()&&visitedTiles[ys][xs+1]!=1) {
                pathfind(xs+1, ys, xw, yw, path);
                return;
            } else  if (xs>xw&&Tilemap.getTile(xs-1,ys).selected()&&visitedTiles[ys][xs-1]!=1) {
                pathfind(xs-1, ys, xw, yw, path);
                return;
            }
        }
        if (Math.abs(xw-xs)<Math.abs(yw-ys)) {
            if (yw>ys&&Tilemap.getTile(xs,ys+1).selected()&&visitedTiles[ys+1][xs]!=1) {

                pathfind(xs, ys+1, xw, yw, path);
                return;
            } else if (ys>yw &&Tilemap.getTile(xs,ys-1).selected()&&visitedTiles[ys-1][xs]!=1) {
                pathfind(xs, ys-1, xw, yw, path);
                return;
            }
        }
        if (xs>xw&&Tilemap.getTile(xs-1,ys).selected()&&visitedTiles[ys][xs-1]!=1) {
            pathfind(xs-1, ys, xw, yw, path);
        } else if (xw>xs&&Tilemap.getTile(xs+1,ys).selected()&&visitedTiles[ys][xs+1]!=1) {
            pathfind(xs+1, ys, xw, yw, path);
        } else  if (yw>ys&&Tilemap.getTile(xs,ys+1).selected()&&visitedTiles[ys+1][xs]!=1) {
            pathfind(xs, ys+1, xw, yw, path);
        } else if (ys>=yw&&Tilemap.getTile(xs,ys-1).selected()&&visitedTiles[ys-1][xs]!=1) {
            pathfind(xs, ys-1, xw, yw, path);
        } else if (Tilemap.getTile(xs,ys-1).selected()&&visitedTiles[ys-1][xs]!=1) {
            pathfind(xs, ys-1, xw, yw, path);
        } else if (Tilemap.getTile(xs,ys+1).selected()&&visitedTiles[ys+1][xs]!=1) {
            pathfind(xs, ys + 1, xw, yw, path);
        } else if (Tilemap.getTile(xs-1,ys).selected()&&visitedTiles[ys][xs-1]!=1) {
            pathfind(xs-1, ys, xw, yw, path);
        } else if (Tilemap.getTile(xs+1,ys).selected()&&visitedTiles[ys][xs+1]!=1) {
            pathfind(xs+1, ys, xw, yw, path);
        } else {
            path.remove(path.size()-1);

            pathfind(path.get(path.size()-1).x, path.get(path.size()-1).y, xw, yw, path);
        }
        */
    }
}
