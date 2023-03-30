/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;


/**
 *
 * @author Emmett
 */
public class Mechanics2048 {
    
    private int randomRow;
    private int randomCol;
    
    public Mechanics2048() {
        JLabel firstTile;
        JLabel secondTile;
        firstTile = Math.random() < 0.9 ? Board2048.Tile.getTile("2") : Board2048.Tile.getTile("4");
        secondTile = Math.random() < 0.9 ? Board2048.Tile.getTile("2") : Board2048.Tile.getTile("4");
        randomRow = (int)(Math.random() * 4);
        randomCol = (int)(Math.random() * 4);
        Board2048.labelsOnGrid[randomRow][randomCol] = firstTile;
        while (Board2048.Tile.getValue(Board2048.labelsOnGrid[randomRow][randomCol]) != 0) {
            randomRow = (int)(Math.random() * 4);
            randomCol = (int)(Math.random() * 4);
        }
        Board2048.labelsOnGrid[randomRow][randomCol] = secondTile;
    }
    
    public void addRandom() {
        randomRow = (int)(Math.random() * 4);
        randomCol = (int)(Math.random() * 4);
        JLabel randomTile = Math.random() < 0.9 ? Board2048.Tile.getTile("2") : Board2048.Tile.getTile("4");
        while (Board2048.Tile.getValue(Board2048.labelsOnGrid[randomRow][randomCol]) != 0) {
            randomRow = (int)(Math.random() * 4);
            randomCol = (int)(Math.random() * 4);
        }
        Board2048.labelsOnGrid[randomRow][randomCol] = randomTile;
    }
    
    public int getRow() {
        return randomRow;
    }
    
    public int getCol() {
        return randomCol;
    }
}
