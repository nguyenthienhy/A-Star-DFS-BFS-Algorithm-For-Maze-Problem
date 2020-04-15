package Maze;

import DepthFirstSearch.DepthFirstSearch;
import Astar.Astar;
import BFS.BFS;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MazeUI extends JPanel implements Runnable, MouseListener {

    private GenerateMaze mazeGame;
    public static final int[][] maze = new int[20][20];
    private Astar map;
    private BFS mapB;
    private List<Integer> pathDFS = new ArrayList<>();
    private List<Integer> pathAstar = new ArrayList<>();
    private List<Integer> pathBFS = new ArrayList<>();
    private int pathIndex, pathIndexStar, pathIndexBFS;
    private final JButton btn, reset, randomMaze;
    private final JComboBox<String> cbo;
    private final JCheckBox pointStart, pointFinish;
    private final JPanel pnMain;
    private Thread animator;
    public static int start = 2; // giá trị điểm xuất phát
    public static int startIndexX = 1;
    public static int startIndexY = 1;
    public static int finish = 9;
    public static int finishIndexX = 14;
    public static int finishIndexY = 17;
    public static int visitedDFS = 5;
    public static int visitedAstar = 6;
    public static int visitedBFS = 7;
    public static int wall = 1;
    public static int width = 20;
    public static int height = 20;
    private boolean runDFS = false;
    private boolean runBFS = false;
    private boolean runAstar = false;
    private int choose = 0;
    private boolean IsClicked = false;
    private Image wallImage, startImage, finishImage, visitedImage, roadImage, robotImage;

    public MazeUI() {

        setLayout(null);
        pnMain = new JPanel(null);
        pnMain.setBounds(900, 50, 350, 800);
        pnMain.setBackground(Color.red);
        btn = new JButton("Find path");
        btn.setBounds(50, 150, 90, 40);
        randomMaze = new JButton("Generate Maze");
        randomMaze.setBounds(120, 300, 120, 40);
        reset = new JButton("Reset Maze");
        reset.setBounds(150, 150, 120, 40);
        cbo = new JComboBox<>();
        cbo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        cbo.setBounds(50, 210, 200, 25);
        cbo.addItem("DFS");
        cbo.addItem("BFS");
        cbo.addItem("A*");
        pointStart = new JCheckBox("setStartPoint");
        pointStart.setMnemonic(KeyEvent.VK_C);
        pointStart.setBounds(50, 250, 110, 40);
        pointStart.setBackground(Color.red);
        pointStart.setForeground(Color.yellow);
        pointFinish = new JCheckBox("setFinishPoint");
        pointFinish.setMnemonic(KeyEvent.VK_C);
        pointFinish.setBounds(190, 250, 110, 40);
        pointFinish.setBackground(Color.red);
        pointFinish.setForeground(Color.yellow);
        pnMain.add(btn);
        pnMain.add(reset);
        pnMain.add(cbo);
        pnMain.add(pointStart);
        pnMain.add(pointFinish);
        pnMain.add(randomMaze);
        this.add(pnMain);
        this.setBackground(Color.GREEN);
        addMouseListener(this);
        initMaze();
        chooseAl();
        chooseStartFinishPoint();
        randomMazeEvent();
        ResetMaze();
        findPath();
    }

    private void initMaze() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maze[i][j] = wall;
            }
        }
        maze[startIndexX][startIndexY] = 0;
        maze[finishIndexX][finishIndexY] = 0;
        ImageIcon ii = new ImageIcon("image/Wall.png");
        wallImage = ii.getImage();
        ImageIcon ii1 = new ImageIcon("image/start.png");
        startImage = ii1.getImage();
        ImageIcon ii2 = new ImageIcon("image/finish.png");
        finishImage = ii2.getImage();
        ImageIcon ii3 = new ImageIcon("image/visited.png");
        visitedImage = ii3.getImage();
        ImageIcon ii4 = new ImageIcon("image/road.png");
        roadImage = ii4.getImage();
        ImageIcon ii5 = new ImageIcon("image/robot.png");
        robotImage = ii5.getImage();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(50, 50);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Image image;
                if (maze[i][j] == start && i == startIndexX && j == startIndexY) {
                    image = startImage;
                } else if (maze[i][j] == finish) {

                    image = finishImage;
                } else if (maze[i][j] == visitedDFS && ((i != startIndexX || j != startIndexY))) {
                    image = visitedImage;
                } else if (maze[i][j] == start && (i != startIndexX || j != startIndexY)) {

                    image = roadImage;
                } else if ((maze[i][j] == visitedAstar || maze[i][j] == visitedDFS) && (i == startIndexX && j == startIndexY)) {

                    image = startImage;
                } else if (maze[i][j] == visitedAstar && (i != startIndexX || j != startIndexY)) {

                    image = visitedImage;
                } else if (maze[i][j] == visitedBFS && (i != startIndexX || j != startIndexY)) {

                    image = visitedImage;
                } else {

                    image = wallImage;
                }

                g.drawImage(image, 40 * i, 40 * j, null);
            }
        }

        if (IsClicked == true) {
            if (runDFS == true) {
                int pathXDFS = pathDFS.get(pathIndex);
                int pathYDFS = pathDFS.get(pathIndex + 1);
                g.drawImage(robotImage, 40 * pathXDFS, 40 * pathYDFS, null);
            }
            if (runAstar == true) {
                int pathXAstar = pathAstar.get(pathIndexStar);
                int pathYAstar = pathAstar.get(pathIndexStar + 1);
                g.drawImage(robotImage, 40 * pathXAstar, 40 * pathYAstar, null);

            }
            if (runBFS == true) {
                int pathXBFS = pathBFS.get(pathIndexBFS);
                int pathYBFS = pathBFS.get(pathIndexBFS + 1);
                g.drawImage(robotImage, 40 * pathXBFS, 40 * pathYBFS, null);
            }
        }

    }

    public void chooseAl() {
        cbo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (cbo.getSelectedIndex() == 0) {
                    choose = 0;
                }
                if (cbo.getSelectedIndex() == 1) {
                    choose = 1;
                }
                if (cbo.getSelectedIndex() == 2) {
                    choose = 2;
                }

            }
        });
    }

    public void findPath() {
        btn.addActionListener((ActionEvent e) -> {
            IsClicked = true;
            btn.setEnabled(false);
            switch (choose) {
                case 0:
                    runDFS = true;
                    //long begin = System.currentTimeMillis();
                    DepthFirstSearch.searchPath(maze, startIndexX, startIndexY, pathDFS);
                    //long finish = System.currentTimeMillis();
                    //System.out.println(finish - begin);
                    if (pathDFS.size() <= 0) {
                        IsClicked = false;
                        JOptionPane.showMessageDialog(null, "Không thể ra khỏi mê cung !!!");
                    } else {
                        pathIndex = pathDFS.size() - 2;
                    }
                    break;
                case 1:
                    runBFS = true;
                    pathBFS = mapB.findPath(startIndexX, startIndexY, finishIndexX, finishIndexY);
                    System.out.println(pathBFS.size());
                    if (pathBFS.size() <= 0) {
                        IsClicked = false;
                        JOptionPane.showMessageDialog(null, "Không thể ra khỏi mê cung !!!");
                    } else {
                        pathIndexBFS = pathBFS.size();
                    }
                    break;
                case 2:
                    runAstar = true;
                    long begin = System.currentTimeMillis();
                    pathAstar = map.findPath(startIndexX, startIndexY, finishIndexX, finishIndexY);
                    long finish = System.currentTimeMillis();
                    System.out.println(finish - begin);
                    if (pathAstar.size() <= 0) {
                        IsClicked = false;
                        JOptionPane.showMessageDialog(null, "Không thể ra khỏi mê cung !!!");
                    } else {
                        pathIndexStar = pathAstar.size();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void chooseStartFinishPoint() {
        pointStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pointStart.isSelected()) {
                    btn.setEnabled(false);
                    pointFinish.setSelected(false);
                } else {
                    btn.setEnabled(true);
                }
            }
        }
        );
        pointFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pointFinish.isSelected()) {
                    btn.setEnabled(false);
                    pointStart.setSelected(false);
                } else {
                    btn.setEnabled(true);
                }
            }
        }
        );
    }

    public void ResetMaze() {
        reset.addActionListener((ActionEvent e) -> {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    maze[i][j] = wall;
                }
            }
            maze[startIndexX][startIndexY] = start;
            maze[finishIndexX][finishIndexY] = finish;
        });
    }

    public void randomMazeEvent() {
        randomMaze.addActionListener((ActionEvent e) -> {
            mazeGame = new GenerateMaze();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    maze[i][j] = mazeGame.getMaze(i, j);
                }
            }
            maze[startIndexX][startIndexY] = start;
            maze[finishIndexX][finishIndexY] = finish;
            map = new Astar(maze);
            mapB = new BFS(maze);
        });
    }

    @Override
    public void run() {

        while (true) {
            repaint();
            if (IsClicked == true) {
                if (runAstar == true) {
                    pathIndexStar -= 2;
                    if (pathIndexStar < 0) {
                        pathIndexStar = 0;
                    }
                } else if (runDFS == true) {
                    pathIndex -= 2;
                    if (pathIndex < 0) {
                        pathIndex = 0;
                    }
                } else if (runBFS == true) {
                    pathIndexBFS -= 2;
                    if (pathIndexBFS < 0) {
                        pathIndexBFS = 0;
                    }
                }
            }
            try {
                Thread.sleep(180);
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int mouseX = e.getX();
        int mouseY = e.getY();
        if (!pointStart.isSelected() && !pointFinish.isSelected()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                btn.setEnabled(true);
                IsClicked = false;
                runDFS = false;
                runBFS = false;
                runAstar = false;
                pathDFS = new ArrayList<>();
                pathAstar = new ArrayList<>();
                pathBFS = new ArrayList<>();
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (mouseX >= 50 + 40 * i && mouseX <= 90 + 40 * i
                                && mouseY >= 50 + 40 * j && mouseY <= 90 + 40 * j && maze[i][j] != maze[startIndexX][startIndexY] && maze[i][j] != maze[finishIndexX][finishIndexY]) {
                            maze[i][j] = start;
                        }
                    }
                }

                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (maze[i][j] == visitedDFS || maze[i][j] == visitedAstar || maze[i][j] == visitedBFS) {
                            maze[i][j] = start;
                        }
                    }
                }

                map = new Astar(maze);
                mapB = new BFS(maze);

            }
        }
        if (e.getButton() != MouseEvent.BUTTON1) {
            btn.setEnabled(true);
            IsClicked = false;
            runDFS = false;
            runBFS = false;
            runAstar = false;
            pathDFS = new ArrayList<>();
            pathAstar = new ArrayList<>();
            pathBFS = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (mouseX >= 50 + 40 * i && mouseX <= 90 + 40 * i
                            && mouseY >= 50 + 40 * j && mouseY <= 90 + 40 * j
                            && (i != startIndexX || j != startIndexY)
                            && (i != finishIndexX || j != finishIndexY)
                            && ((maze[i][j] == visitedDFS || maze[i][j] == visitedAstar || maze[i][j] == visitedBFS) || maze[i][j] == start)) {
                        maze[i][j] = wall;
                    }
                }
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (maze[i][j] == visitedDFS || maze[i][j] == visitedAstar || maze[i][j] == visitedBFS) {
                        maze[i][j] = start;
                    }
                }
            }

            map = new Astar(maze);
            mapB = new BFS(maze);
        } else {
            if (pointStart.isSelected()) {
                maze[startIndexX][startIndexY] = wall;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (mouseX >= 50 + 40 * i && mouseX <= 90 + 40 * i
                                && mouseY >= 50 + 40 * j && mouseY <= 90 + 40 * j) {
                            startIndexX = i;
                            startIndexY = j;
                        }

                    }
                }
                maze[startIndexX][startIndexY] = start;
            }
            if (pointFinish.isSelected()) {
                maze[finishIndexX][finishIndexY] = wall;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (mouseX >= 50 + 40 * i && mouseX <= 90 + 40 * i
                                && mouseY >= 50 + 40 * j && mouseY <= 90 + 40 * j) {
                            finishIndexX = i;
                            finishIndexY = j;
                        }

                    }
                }
                maze[finishIndexX][finishIndexY] = finish;
            }
            map = new Astar(maze);
            mapB = new BFS(maze);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
