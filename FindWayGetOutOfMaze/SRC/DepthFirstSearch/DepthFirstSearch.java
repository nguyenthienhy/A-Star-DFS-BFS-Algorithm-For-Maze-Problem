package DepthFirstSearch;

import Maze.MazeUI;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {

    public static boolean searchPath(int[][] maze, int x, int y,
            List<Integer> path) {
        if (maze[x][y] == MazeUI.finish) {
            path.add(x);
            path.add(y);
            return true;
        }
        if (maze[x][y] == MazeUI.start) {
            maze[x][y] = MazeUI.visitedDFS;

            int dx = -1;
            int dy = 0;
            if (x + dx >= 0 && x + dx < MazeUI.width && y + dy >= 0 && y + dy < MazeUI.height) {
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }

            dx = 1;
            dy = 0;
            if (x + dx >= 0 && x + dx < MazeUI.width && y + dy >= 0 && y + dy < MazeUI.height) {
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }

            dx = 0;
            dy = -1;
            if (x + dx >= 0 && x + dx < MazeUI.width && y + dy >= 0 && y + dy < MazeUI.height) {
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }

            dx = 0;
            dy = 1;
            if (x + dx >= 0 && x + dx < MazeUI.width && y + dy >= 0 && y + dy < MazeUI.height) {
                if (searchPath(maze, x + dx, y + dy, path)) {
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }
        }
        path = new ArrayList<>();
        return false;
    }
}
