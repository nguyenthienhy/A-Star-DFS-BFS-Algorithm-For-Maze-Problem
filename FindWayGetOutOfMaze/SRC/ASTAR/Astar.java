
package Astar;

import Maze.MazeUI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Astar {

    private final int width;

    private final int height;

    private final Node[][] nodes;

    public Astar(int[][] graph) {
        this.width = graph[0].length;
        this.height = graph.length;
        nodes = new Node[width][height];
        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                if(MazeUI.maze[x][y] != MazeUI.wall)
                    nodes[x][y] = new Node(x, y, graph[x][y] == MazeUI.start);
                if (x == MazeUI.finishIndexX && y == MazeUI.finishIndexY) {
                    nodes[x][y] = new Node(x, y, graph[x][y] == MazeUI.finish);
                }
            }
        }
    }

    public Node getNode(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return nodes[x][y];
        }
        return null;
    }

    public List<Integer> findPath(int startIndexX, int startIndexY, int finishIndexX, int finishIndexY) {
        
        if (startIndexX == finishIndexX && startIndexY == finishIndexY) {
            
            return new ArrayList<>();
        }

        
        List<Node> OPEN = new LinkedList<>();
        
        List<Node> CLOSE = new LinkedList<>();

        
        OPEN.add(nodes[startIndexX][startIndexY]);

       
        while (!OPEN.isEmpty()) {
            
            Node current = findMin(OPEN);
            
            OPEN.remove(current);
            
            CLOSE.add(current);
            
            MazeUI.maze[current.getX()][current.getY()] = MazeUI.visitedAstar;
            
            if ((current.getX() == finishIndexX) && (current.getY() == finishIndexY)) {
                
                return addPath(nodes[startIndexX][startIndexY], current);
            }

            List<Node> nextNodes = getNodeNextThis(current, CLOSE);
            
            nextNodes.forEach((next) -> {
                if (!OPEN.contains(next)) {
                    
                    next.setParent(current);
                    
                    next.setH(nodes[finishIndexX][finishIndexY]);
                    
                    next.setG(current); // chi phí tự nút gốc đến nút hiện tại đang xét
                    
                    OPEN.add(next);
                } 
                else if (next.getG() > next.tempG(current)) {
                    
                    next.setParent(current);
                    
                    next.setG(current);
                }
            });            
        }
        return new ArrayList<>();
    }

    private List<Integer> addPath(Node start, Node finish) {
        List<Integer> path = new ArrayList<>();
        Node node = finish;
        boolean done = false;
        while (!done) {
            path.add(node.getX());
            path.add(node.getY());
            if(!node.isNode(nodes[MazeUI.finishIndexX][MazeUI.finishIndexY]))
                MazeUI.maze[node.getX()][node.getY()] = MazeUI.visitedAstar;
            else{
                MazeUI.maze[node.getX()][node.getY()] = MazeUI.finish;
            }
            node = node.getParent();
            if (node.isNode(start)) {
                done = true;
            }
        }
        return path;
    }

    private Node findMin(List<Node> list) { // trả về nút có chi phí nhỏ nhất đến đích
        Node min = list.get(0);
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getF() <= min.getF()) {
                min = list.get(i);
            }
        }
        return min;
    }

    private List<Node> getNodeNextThis(Node node, List<Node> CLOSE) { // tìm nút liền kề với nút hiện tại đang xét mà không có trong CLOSE
        List<Node> nextNodes = new LinkedList<>();
        int x = node.getX();
        int y = node.getY();

        Node next;

        if (x > 0) {
            next = getNode(x - 1, y);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                nextNodes.add(next);
                
            }
        }

        if (x < width) {
            next = getNode(x + 1, y);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                nextNodes.add(next);
            }
        }
        
        if (y > 0) {
            next = this.getNode(x, y - 1);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                nextNodes.add(next);
            }
        }

        if (y < height) {
            next = this.getNode(x, y + 1);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                nextNodes.add(next);
            }
        }
        return nextNodes;
    }

}
