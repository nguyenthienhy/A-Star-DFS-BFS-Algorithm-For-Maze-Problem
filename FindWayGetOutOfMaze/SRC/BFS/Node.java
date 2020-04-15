
package BFS;


public class Node {
    
    private int x;
    private int y;
    private boolean notWall;
    private Node parent;
    
    public Node(int x, int y, boolean notWall) {
        this.x = x;
        this.y = y;
        this.notWall = notWall;
    }
    
     public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNotWall(boolean notWall) {
        this.notWall = notWall;
    }

    public boolean isNotWall() {
        return notWall;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
     public boolean isNode(Node node) {
        
        if (node == null) {
            return false;
        }
        
        if (node.getX() == x && node.getY() == y && node.isNotWall()) {
            return true;
        }
        
        return false;
    }
}
