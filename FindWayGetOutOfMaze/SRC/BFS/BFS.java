
package BFS;

import Maze.MazeUI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BFS {
    private final int width;

    private final int height;

    private final Node[][] nodes;
    
    public static int costB;

    public BFS(int[][] graph) {
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

        
        List<Node> QUEUE = new LinkedList<>(); // khởi tạo 1 hàng đợi
        List<Node> CLOSE = new LinkedList<>();
        
        QUEUE.add(nodes[startIndexX][startIndexY]);

       
        while (!QUEUE.isEmpty()) {
            
            Node current = QUEUE.get(0);
            
            QUEUE.remove(current);
            
            CLOSE.add(current);
            
            if ((current.getX() == finishIndexX) && (current.getY() == finishIndexY)) {
                
                return addPath(nodes[startIndexX][startIndexY], current);//trả lại path đường đi
            }

            getNodeNextThis(current , CLOSE , QUEUE);//thêm vào hàng đợi các đỉnh kề với đỉnh hiện tại
             
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
                MazeUI.maze[node.getX()][node.getY()] = MazeUI.visitedBFS;
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
    
    
    private void getNodeNextThis(Node node , List<Node> CLOSE , List<Node> QUEUE) { // tìm nút liền kề với nút hiện tại đang xét mà không có trong CLOSE
        int x = node.getX();
        int y = node.getY();

        Node next;
        
        if (y > 0) {
            next = this.getNode(x, y - 1);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                QUEUE.add(next);
                MazeUI.maze[next.getX()][next.getY()] = MazeUI.visitedBFS;
                costB++;
                next.setParent(node);
            }
        }
        if (y < height) {
            next = this.getNode(x, y + 1);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                QUEUE.add(next);
                MazeUI.maze[next.getX()][next.getY()] = MazeUI.visitedBFS;
                costB++;
                next.setParent(node);
            }
        }
        
        if (x > 0) {
            next = getNode(x - 1, y);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                QUEUE.add(next);
                MazeUI.maze[next.getX()][next.getY()] = MazeUI.visitedBFS;
                costB++;
                next.setParent(node);
            }
        }
        
        if (x < width) {
            next = getNode(x + 1, y);
            if (next != null && next.isNotWall() && !CLOSE.contains(next)) {
                QUEUE.add(next);
                MazeUI.maze[next.getX()][next.getY()] = MazeUI.visitedBFS;
                costB++;
                next.setParent(node);
            }
        }
          
    }

}
