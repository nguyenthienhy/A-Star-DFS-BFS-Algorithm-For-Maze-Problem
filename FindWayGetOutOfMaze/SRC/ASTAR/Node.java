
package Astar;

public class Node {
    
    private int x;

    private int y;

    private boolean notWall;

    private Node parent;

    private int g;

    private int h;

    public Node(int x, int y, boolean notWall) {
        this.x = x;
        this.y = y;
        this.notWall = notWall;
    }

    public void setG(Node parent) {
        g = (parent.getG() + Math.abs((this.getX() - parent.getX())) + Math.abs((this.getY() - parent.getY())));
    }

    public int tempG(Node parent) {
        return (parent.getG() + Math.abs((this.getX() - parent.getX())) + Math.abs((this.getY() - parent.getY())));
    }

    public void setH(Node finish) {
        h = Math.abs((this.getX() - finish.getX())) + Math.abs((this.getY() - finish.getY()));
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

    public int getF() {
        return g + h;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
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