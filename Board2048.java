/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Emmett
 */
public class Board2048 implements KeyListener {
    
    private static final Color normalText = new Color(249, 246, 242);

    public static class Tile {
    
    public static int getValue(JLabel x) {
        return Integer.parseInt(x.getText());
    }
    
    public static JLabel getTile(String name) {
        JLabel tempTile = new JLabel(name);
        switch (Integer.parseInt(name)) {
            case 0: tempTile.setBackground(new Color(204, 192, 180)); tempTile.setForeground(new Color(204, 192, 180)); break;
            case 2: tempTile.setBackground(new Color(238, 228, 218)); tempTile.setForeground(new Color(119, 110, 101)); break;
            case 4: tempTile.setBackground(new Color(237, 224, 200)); tempTile.setForeground(new Color(119, 110, 101)); break;
            case 8: tempTile.setBackground(new Color(242, 177, 121)); tempTile.setForeground(normalText); break;
            case 16: tempTile.setBackground(new Color(245, 149, 99)); tempTile.setForeground(normalText); break;
            case 32: tempTile.setBackground(new Color(246, 124, 84)); tempTile.setForeground(normalText); break;
            case 64: tempTile.setBackground(new Color(246, 94, 59)); tempTile.setForeground(normalText); break;
            case 128: tempTile.setBackground(new Color(237, 207, 114)); tempTile.setForeground(normalText); break;
            case 256: tempTile.setBackground(new Color(237, 204, 97)); tempTile.setForeground(normalText); break;
            case 512: tempTile.setBackground(new Color(237, 200, 80)); tempTile.setForeground(normalText); break;
            case 1024: tempTile.setBackground(new Color(237, 197, 63)); tempTile.setForeground(normalText); break;
            case 2048: tempTile.setBackground(new Color(237, 194, 46)); tempTile.setForeground(normalText); break;
        }
        tempTile.setHorizontalAlignment(JLabel.CENTER);
        tempTile.setOpaque(true);
        return tempTile;
    }
}
    public static final JLabel[][] labelsOnGrid = new JLabel[4][4];
    private Mechanics2048 mechanics;
    private Container cp;
    private JFrame window;
    private JPanel[][] grid;
    private JPanel gridHolder;
    private JLabel scoreLabel;
    private static int score = 0;
    
    public Board2048() {
        setUIFont (new javax.swing.plaf.FontUIResource("Futura",Font.BOLD,45));
        window = new JFrame("2048");
        gridHolder = new JPanel();
        gridHolder.setLayout(new GridLayout(4, 4, 10, 10));
        grid = new JPanel[4][4];
        scoreLabel = new JLabel("Score: " + Integer.toString(score));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        window.setVisible(true);
        window.setSize(500, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
        cp = window.getContentPane();
        cp.setLayout(new GridBagLayout());
        GridBagConstraints scoreCon = new GridBagConstraints();
        scoreCon.gridx = 0;
        scoreCon.gridy = 0;
        scoreCon.fill = GridBagConstraints.BOTH;
        scoreCon.weighty = 0.35;
        scoreCon.weightx = 1;
        GridBagConstraints gridCon = new GridBagConstraints();
        gridCon.gridx = 0;
        gridCon.gridy = 1;
        gridCon.fill = GridBagConstraints.BOTH;
        gridCon.weighty = 0.65;
        gridCon.weightx = 1;
        gridCon.insets = new Insets(15, 15, 15, 15);
        cp.setBackground(new Color(251, 248, 239));
        cp.add(scoreLabel, scoreCon);
        cp.add(gridHolder, gridCon);
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                labelsOnGrid[r][c] = Tile.getTile("0");            
        mechanics = new Mechanics2048();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = new JPanel();
                gridHolder.add(grid[r][c]);
                grid[r][c].setBackground(Color.LIGHT_GRAY);
                grid[r][c].setLayout(new CardLayout());
            }
        window.addKeyListener(this);
        gridHolder.setBackground(new Color(187, 173, 161));
        gridHolder.setBorder(BorderFactory.createLineBorder(new Color(187, 173, 161), 10));
    }
    
    public void setBoard() {
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++) {
                if (labelsOnGrid[r][c] == null)
                    labelsOnGrid[r][c] = Tile.getTile("0"); 
                grid[r][c].removeAll();
                grid[r][c].add(labelsOnGrid[r][c]);
            }
        scoreLabel.setText("Score: " + Integer.toString(score));
        cp.revalidate();
    }
    
    private boolean notKeepPlaying = true;
    
    public void keyPressed (KeyEvent e) {
        int event = e.getKeyCode();
        boolean isSpace = false;
        boolean changedBoard = false;
        boolean noHorizontalMoves = true;
        boolean noVerticalMoves = true;
        JLabel[][] tempLabels = new JLabel[4][4];
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                if (Tile.getValue(labelsOnGrid[r][c]) == 2048 && notKeepPlaying) {
                    JFrame endScreen = new JFrame();
                    endScreen.setVisible(true);
                    endScreen.setBackground(Color.WHITE);
                    endScreen.setBounds(window.getX(), window.getY(), window.getWidth(), window.getHeight());
                    endScreen.setLayout(new GridLayout(4, 1));
                    JLabel win = new JLabel("Congratulations!");
                    win.setOpaque(true);
                    win.setBackground(Color.LIGHT_GRAY);
                    win.setHorizontalAlignment(JLabel.CENTER);
                    JLabel finalScore = new JLabel("Score: " + Integer.toString(score));
                    finalScore.setOpaque(true);
                    finalScore.setBackground(Color.LIGHT_GRAY);
                    finalScore.setHorizontalAlignment(JLabel.CENTER);
                    JButton keepPlaying = new JButton("Keep Going");
                    keepPlaying.setBackground(Color.LIGHT_GRAY);
                    keepPlaying.setForeground(Color.BLACK);
                    keepPlaying.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            notKeepPlaying = false;
                            endScreen.setVisible(false);
                            endScreen.dispose();
                        }
                    });
                    JButton replay = new JButton("New Game");
                    replay.setBackground(Color.LIGHT_GRAY);
                    replay.setForeground(Color.BLACK);
                    replay.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            endScreen.setVisible(false);
                            endScreen.dispose();
                            for (int r = 0; r < labelsOnGrid.length; r++)
                                for (int c = 0; c < labelsOnGrid[r].length; c++)
                                    labelsOnGrid[r][c] = Tile.getTile("0");
                            new Mechanics2048();
                            score = 0;
                            setBoard();
                            cp.revalidate();
                        }
                    });
                    endScreen.add(win);
                    endScreen.add(finalScore);
                    endScreen.add(keepPlaying);
                    endScreen.add(replay);
                }
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                tempLabels[r][c] = Tile.getTile(Integer.toString(Tile.getValue(labelsOnGrid[r][c])));
        int t1, t2, t3, t4;
        if (event == KeyEvent.VK_LEFT) {
            for (int r = 0; r < labelsOnGrid.length; r++) {
                t1 = Tile.getValue(labelsOnGrid[r][0]);
                t2 = Tile.getValue(labelsOnGrid[r][1]);
                t3 = Tile.getValue(labelsOnGrid[r][2]);
                t4 = Tile.getValue(labelsOnGrid[r][3]);
                changeBoardLeft(t1, t2, t3, t4, r);
            }
        }       
        if (event == KeyEvent.VK_RIGHT){
            for (int r = 0; r < labelsOnGrid.length; r++) {
                t1 = Tile.getValue(labelsOnGrid[r][0]);
                t2 = Tile.getValue(labelsOnGrid[r][1]);
                t3 = Tile.getValue(labelsOnGrid[r][2]);
                t4 = Tile.getValue(labelsOnGrid[r][3]);
                changeBoardRight(t1, t2, t3, t4, r);
            }
        }
        if (event == KeyEvent.VK_UP) {
            for (int c = 0; c < labelsOnGrid[0].length; c++) {
                t1 = Tile.getValue(labelsOnGrid[0][c]);
                t2 = Tile.getValue(labelsOnGrid[1][c]);
                t3 = Tile.getValue(labelsOnGrid[2][c]);
                t4 = Tile.getValue(labelsOnGrid[3][c]);
                changeBoardUp(t1, t2, t3, t4, c);
            }
        }
        if (event == KeyEvent.VK_DOWN) {
            for (int c = 0; c < labelsOnGrid[0].length; c++) {
                t1 = Tile.getValue(labelsOnGrid[0][c]);
                t2 = Tile.getValue(labelsOnGrid[1][c]);
                t3 = Tile.getValue(labelsOnGrid[2][c]);
                t4 = Tile.getValue(labelsOnGrid[3][c]);
                changeBoardDown(t1, t2, t3, t4, c);
            }
        }
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                if (Tile.getValue(labelsOnGrid[r][c]) == 0)
                    isSpace = true;
        
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                if (Tile.getValue(tempLabels[r][c]) != Tile.getValue(labelsOnGrid[r][c]))
                    changedBoard = true;
        
        outerLoop1:
        for (int r = 0; r < labelsOnGrid.length; r++)
            for (int c = 0; c < labelsOnGrid[r].length - 1; c++)
                if (Tile.getValue(labelsOnGrid[r][c]) == Tile.getValue(labelsOnGrid[r][c+1])) {
                    noHorizontalMoves = false;
                    break outerLoop1;
                }
        
        outerLoop2:
        for (int r = 0; r < labelsOnGrid.length - 1; r++)
            for (int c = 0; c < labelsOnGrid[r].length; c++)
                if (Tile.getValue(labelsOnGrid[r][c]) == Tile.getValue(labelsOnGrid[r+1][c])) {
                    noVerticalMoves = false;
                    break outerLoop2;
                }
        
        if (isSpace && changedBoard)
            mechanics.addRandom();
        setBoard();
        
        if (!isSpace && noHorizontalMoves && noVerticalMoves) {
            JFrame endScreen = new JFrame();
            endScreen.setVisible(true);
            endScreen.setBackground(Color.WHITE);
            endScreen.setBounds(window.getX(), window.getY(), window.getWidth(), window.getHeight());
            endScreen.setLayout(new GridLayout(2, 1));
            JLabel finalScore = new JLabel("Score: " + Integer.toString(score));
            finalScore.setOpaque(true);
            finalScore.setBackground(Color.LIGHT_GRAY);
            finalScore.setHorizontalAlignment(JLabel.CENTER);
            JButton replay = new JButton("New Game");
            replay.setBackground(Color.LIGHT_GRAY);
            replay.setForeground(Color.BLACK);
            replay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    endScreen.setVisible(false);
                    endScreen.dispose();
                    for (int r = 0; r < labelsOnGrid.length; r++)
                        for (int c = 0; c < labelsOnGrid[r].length; c++)
                            labelsOnGrid[r][c] = Tile.getTile("0");
                    new Mechanics2048();
                    score = 0;
                    setBoard();
                    cp.revalidate();
                }
            });
            endScreen.add(finalScore);
            endScreen.add(replay);
        }
    }
    
    public void keyReleased (KeyEvent e) {
        
    }
    
    public void keyTyped (KeyEvent e) {
        
    }
    
    public void changeBoardLeft(int t1, int t2, int t3, int t4, int row) {
        int[] tempRow = {t1, t2, t3, t4};
        for (int i = 0; i < tempRow.length; i++)
            if (tempRow[i] == 0) {
                for (int temp = i + 1; temp < tempRow.length; temp++) {
                    if (tempRow[temp] != 0 && tempRow[i] == 0) {
                        tempRow[i] = tempRow[temp];
                        tempRow[temp] = 0;
                        i++;
                    }
                }
            }
        
        for (int i = 0; i < tempRow.length - 1; i++) {
            if (tempRow[i] == tempRow[i+1]) {
                tempRow[i] += tempRow[i+1];
                tempRow[i+1] = 0;
                score += tempRow[i];
            }
        }
        
        for (int i = 0; i < tempRow.length; i++)
            if (tempRow[i] == 0) {
                for (int temp = i + 1; temp < tempRow.length; temp++) {
                    if (tempRow[temp] != 0 && tempRow[i] == 0) {
                        tempRow[i] = tempRow[temp];
                        tempRow[temp] = 0;
                        i++;
                    }
                }
            }
        
        for (int i = 0; i < tempRow.length; i++)
            labelsOnGrid[row][i] = Tile.getTile(Integer.toString(tempRow[i]));
    }
    
    public void changeBoardRight(int t1, int t2, int t3, int t4, int row) {
        int[] tempRow = {t1, t2, t3, t4};
        for (int i = tempRow.length - 1; i >= 0; i--)
            if (tempRow[i] == 0) {
                for (int temp = i - 1; temp >= 0; temp--) {
                    if (tempRow[temp] != 0 && tempRow[i] == 0) {
                        tempRow[i] = tempRow[temp];
                        tempRow[temp] = 0;
                        i--;
                    }
                }
            }
        
        for (int i = tempRow.length - 1; i >= 1; i--) {
            if (tempRow[i] == tempRow[i-1]) {
                tempRow[i] += tempRow[i-1];
                tempRow[i-1] = 0;
                score += tempRow[i];
            }
        }
        
        for (int i = tempRow.length - 1; i >= 0; i--)
            if (tempRow[i] == 0) {
                for (int temp = i - 1; temp >= 0; temp--) {
                    if (tempRow[temp] != 0 && tempRow[i] == 0) {
                        tempRow[i] = tempRow[temp];
                        tempRow[temp] = 0;
                        i--;
                    }
                }
            }
        
        for (int i = 0; i < tempRow.length; i++)
            labelsOnGrid[row][i] = Tile.getTile(Integer.toString(tempRow[i]));
    }
    
    public void changeBoardUp(int t1, int t2, int t3, int t4, int col) {
        int[] tempCol = {t1, t2, t3, t4};
        for (int i = 0; i < tempCol.length; i++)
            if (tempCol[i] == 0) {
                for (int temp = i + 1; temp < tempCol.length; temp++) {
                    if (tempCol[temp] != 0 && tempCol[i] == 0) {
                        tempCol[i] = tempCol[temp];
                        tempCol[temp] = 0;
                        i++;
                    }
                }
            }
        
        for (int i = 0; i < tempCol.length - 1; i++) {
            if (tempCol[i] == tempCol[i+1]) {
                tempCol[i] += tempCol[i+1];
                tempCol[i+1] = 0;
                score += tempCol[i];
            }
        }
        
        for (int i = 0; i < tempCol.length; i++)
            if (tempCol[i] == 0) {
                for (int temp = i + 1; temp < tempCol.length; temp++) {
                    if (tempCol[temp] != 0 && tempCol[i] == 0) {
                        tempCol[i] = tempCol[temp];
                        tempCol[temp] = 0;
                        i++;
                    }
                }
            }
        
        for (int i = 0; i < tempCol.length; i++)
            labelsOnGrid[i][col] = Tile.getTile(Integer.toString(tempCol[i]));
    }
    
    public void changeBoardDown(int t1, int t2, int t3, int t4, int col) {
        int[] tempCol = {t1, t2, t3, t4};
        for (int i = tempCol.length - 1; i >= 0; i--)
            if (tempCol[i] == 0) {
                for (int temp = i - 1; temp >= 0; temp--) {
                    if (tempCol[temp] != 0 && tempCol[i] == 0) {
                        tempCol[i] = tempCol[temp];
                        tempCol[temp] = 0;
                        i--;
                    }
                }
            }
        
        for (int i = tempCol.length - 1; i >= 1; i--) {
            if (tempCol[i] == tempCol[i-1]) {
                tempCol[i] += tempCol[i-1];
                tempCol[i-1] = 0;
                score += tempCol[i];
            }
        }
        
        for (int i = tempCol.length - 1; i >= 0; i--)
            if (tempCol[i] == 0) {
                for (int temp = i - 1; temp >= 0; temp--) {
                    if (tempCol[temp] != 0 && tempCol[i] == 0) {
                        tempCol[i] = tempCol[temp];
                        tempCol[temp] = 0;
                        i--;
                    }
                }
            }
        
        for (int i = 0; i < tempCol.length; i++)
            labelsOnGrid[i][col] = Tile.getTile(Integer.toString(tempCol[i]));
    }
    
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    } 
}
