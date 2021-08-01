package game2.essentials;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStar {

    private final static int MAX_WIDTH = 200;

    private static class Node {
        private final Node parent;

        private final int x, y;

        private double fScore, gScore;

        private Node(Node parent, int x, int y) {
            this.parent = parent;
            this.x = x;
            this.y = y;
            this.fScore = 0;
            this.gScore = Integer.MAX_VALUE;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Node)) return false;
            Node node = (Node) other;
            return this.x == node.x && this.y == node.y;
        }

        private double hScore(Node goal) {
            int dx = this.x - goal.x, dy = this.y - goal.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        private Node[] getNeighbors() {
            return new Node[]{
                    new Node(this, x + 1, y),
                    new Node(this, x - 1, y),
                    new Node(this, x, y + 1),
                    new Node(this, x, y - 1),
                    new Node(this, x + 1, y + 1),
                    new Node(this, x + 1, y - 1),
                    new Node(this, x - 1, y + 1),
                    new Node(this, x - 1, y - 1)
            };
        }

        private List<Point> makePath(){
            List<Point> path = new ArrayList<>();
            Node node = this;
            while (node.parent != null){
                path.add(new Point(node.x - node.parent.x, node.y - node.parent.y));
                node = node.parent;
            }
            return path;
        }

        @Override
        public int hashCode() {
            return x + MAX_WIDTH * y;
        }
    }

    public static List<Point> getPath(TileMap tileMap, Point startPos, Point goalPos){
        Node start = new Node(null, startPos.x, startPos.y), goal = new Node(null, goalPos.x, goalPos.y);
        start.fScore = start.hScore(goal);
        start.gScore = 0;
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparing((node -> node.fScore)));
        openSet.add(start);
        Set<Node> closedSet = new HashSet<>();
        while (!openSet.isEmpty()){
            Node node = openSet.remove();
            if (node.equals(goal)){
                return node.makePath();
            }
            closedSet.add(node);
            for (Node neighbor : node.getNeighbors()){
                if (!tileMap.getTile(neighbor.x, neighbor.y).isOpen() || closedSet.contains(neighbor)){
                    continue;
                }
                neighbor.gScore = node.gScore + 1;
                neighbor.fScore = neighbor.gScore + neighbor.hScore(goal);
                if(!openSet.contains(neighbor)) openSet.add(neighbor);
            }
        }
        return null;
    }


}
