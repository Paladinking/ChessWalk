package game2.essentials;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStar {

    private final static int MAX_WIDTH = 200;

    public static class Node {
        protected final Node parent;

        protected final int x, y;

        protected double fScore, gScore;

        private Node(Node parent, int x, int y) {
            this.parent = parent;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Node)) return false;
            Node node = (Node) other;
            return this.x == node.x && this.y == node.y;
        }


        public double hScore(Node goal) {
            int dx = this.x - goal.x, dy = this.y - goal.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        public double gScore() {
            return parent.gScore + 1;
        }

        public List<Node> getNeighbors() {
            return Arrays.asList(new Node(this, x + 1, y),
                    new Node(this, x - 1, y),
                    new Node(this, x, y + 1),
                    new Node(this, x, y - 1),
                    new Node(this, x + 1, y + 1),
                    new Node(this, x + 1, y - 1),
                    new Node(this, x - 1, y + 1),
                    new Node(this, x - 1, y - 1));
        }

        protected List<Point> makePath() {
            List<Point> path = new ArrayList<>();
            Node node = this;
            while (node.parent != null) {
                path.add(new Point(node.x, node.y));
                node = node.parent;
            }
            return path;
        }

        @Override
        public int hashCode() {
            return x + MAX_WIDTH * y;
        }
    }


    private AStar() {

    }

    public static List<Point> getPath(TileMap tileMap, Point startPos, Point goalPos) {
        Node start = new Node(null, startPos.x, startPos.y), goal = new Node(null, goalPos.x, goalPos.y);
        start.fScore = start.hScore(goal);
        start.gScore = 0;
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparing((node -> node.fScore)));
        openSet.add(start);
        Set<Node> closedSet = new HashSet<>();
        while (!openSet.isEmpty()) {
            Node node = openSet.remove();
            if (node.equals(goal)) {
                return node.makePath();
            }
            closedSet.add(node);
            for (Node neighbor : node.getNeighbors()) {
                if ((!tileMap.getTile(neighbor.x, neighbor.y).isOpen() && !(neighbor.equals(goal))) || closedSet.contains(neighbor)) {
                    continue;
                }
                neighbor.gScore = neighbor.gScore();
                neighbor.fScore = neighbor.gScore + neighbor.hScore(goal);
                if (!openSet.contains(neighbor)) openSet.add(neighbor);
            }
        }
        return null;
    }

}
