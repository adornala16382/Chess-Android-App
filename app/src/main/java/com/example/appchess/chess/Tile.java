package com.example.appchess.chess;

import java.io.Serializable;

public class Tile implements Serializable {
    public final int color;
    private Piece p;
    private boolean threat;
    public int height = 50;
    public int width = 50;
    /**
     * Single arg constructor for Tile
     * @param color is the color of the tile
     */
    public Tile(int color) {
        this.color = color;
    }
    /**
     * Tile constructor that creates a new copy tile given a tile object
     * @param tile is tile to copy
     */
    public Tile(Tile tile){
        this.color = tile.color;
        this.p = tile.getPiece();
    }
    /**
     * Returns the current piece on the board
     * @return p the current piece on the board
     */

    public Piece getPiece() {
        return this.p;
    }
    /**
     * Sets the threat status of the tile to a given boolean
     * @param status is the boolean value that threat is set to
     */
    public void setThreat(Boolean status){
        threat = status;
    }
    /**
     * Returns the threat attribute of the tile
     * @return this.threat of the tile
     */
    public boolean getThreat(){
        return this.threat;
    }
    /**
     * Sets the tile's attribute p to the given Piece p
     * @param p is the Piece that is on the tile
     */
    public void setPiece(Piece p) {
        this.p = p;

    }
    /**
     * Checks if the tile has a Piece on it and returns true if it does, false if not
     * @return true or false if tile has Piece
     */
    public boolean hasPiece() {
        if (this.p == null || this.p.toString().equals("0")) {
            return false;
        }
        return true;
    }
    /**
     * The toString method of the tile to print out each tile value
     * If tile does not have piece, the default color of the tile is printed
     * @see Board
     */
    public String toString() {

        if (p == null) {
            if (this.color == 0) {
                return "  ";
            }

            if (this.color == 1) {
                return "##";
            }
        } else {
            return p.toString();
        }

        return "";
    }


}
