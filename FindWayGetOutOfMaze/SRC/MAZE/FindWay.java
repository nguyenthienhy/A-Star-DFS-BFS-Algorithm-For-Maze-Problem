
package Maze;

import javax.swing.JFrame;


public class FindWay extends JFrame{
    
    public FindWay(){
        add(new MazeUI());
        setSize(1280 , 940);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    public static void main(String[] args) {
        FindWay maze = new FindWay();
        maze.setVisible(true);
    }
}
