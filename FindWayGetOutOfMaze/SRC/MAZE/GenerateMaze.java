package Maze;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenerateMaze{

    private final int width = 20;
    private final int height = 20;
    private int maze[][] = new int[20][20];
    
    public GenerateMaze(){
        generateMaze();
        convert();
    }

    public int[][] generateMaze() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maze[i][j] = MazeUI.wall;
            }
        }

        Random rand = new Random();

        int r = rand.nextInt(width);
        while (r % 2 == 0) {
            r = rand.nextInt(width);
        }

        int c = rand.nextInt(height);
        while (c % 2 == 0) {
            c = rand.nextInt(height);
        }
        
        recursion(r, c);

        return maze;
    }

    public void recursion(int r, int c) {

        Integer[] randDirs = generateRandomDirections();

        for (Integer randDir : randDirs) {
            switch (randDir) {
                case 1:

                    if (r - 2 <= 0) {
                        continue;
                    }
                    if (maze[r - 2][c] != MazeUI.start) {
                        maze[r - 2][c] = MazeUI.start;
                        maze[r - 1][c] = MazeUI.start;
                        recursion(r - 2, c);
                    }
                    break;
                case 2:

                    if (c + 2 >= height - 1) {
                        continue;
                    }
                    if (maze[r][c + 2] !=  MazeUI.start) {
                        maze[r][c + 2] =  MazeUI.start;
                        maze[r][c + 1] =  MazeUI.start;
                        recursion(r, c + 2);
                    }
                    break;
                case 3:

                    if (r + 2 >= width - 1) {
                        continue;
                    }
                    if (maze[r + 2][c] !=  MazeUI.start) {
                        maze[r + 2][c] =  MazeUI.start;
                        maze[r + 1][c] =  MazeUI.start;
                        recursion(r + 2, c);
                    }
                    break;
                case 4:

                    if (c - 2 <= 0) {
                        continue;
                    }
                    if (maze[r][c - 2] !=  MazeUI.start) {
                        maze[r][c - 2] =  MazeUI.start;
                        maze[r][c - 1] =  MazeUI.start;
                        recursion(r, c - 2);
                    }
                    break;
            }
        }

    }

    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            randoms.add(i + 1);
        }
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

    public int[][] getMaze() {
        return maze;
    }
    public int getMaze(int i , int j) {
        return maze[i][j];
    }
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setMaze(int number , int i , int j) {
        maze[i][j] = number;
    }
    
    public void printMaze(){
        for(int i = 0 ;i < width ; i ++){
            for(int j = 0 ; j < height ; j ++){
                System.out.printf("" + maze[i][j]);
            }
            System.out.printf("\n");
        }
    }
    
    public void convert(){
         int k;
         for(int i = 0 ;i < width ; i ++){
            k = height - 1;
            for(int j = 0 ; j < height ; j ++){
                maze[i][j] = maze[j][i];
                k --;
            } 
        }
    }
}
