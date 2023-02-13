package com.example.appchess.chess;

import java.io.Serializable;

public class Position implements Serializable {
    /**
     */

    private int row, col;
    /**
     * public Position is the 2-arg constructor of Position given row, col
     * @param row is the row (x value) of position
     * @param col is the col (y value) of position
     */

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }
    /**
     * Returns the row of the position object
     * @return row attribute of Position
     */


    public int getRow(){
        return row;
    }
    /**Returns the col of the position object
     * @return col attribute of Position
     */
    public int getCol(){
        return col;
    }
}
