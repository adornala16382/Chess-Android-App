package com.example.appchess.chess;

import java.io.Serializable;

public class Piece implements Serializable {
    private int color;
    private int numMoves = 0;

    public Piece(int color) {
        this.color = color;
        numMoves = 0;
    }
    public boolean getMoved(){
        return numMoves > 0;
    }
    public boolean movePiece(Position oldPos, Position newPos, Board b) {
        return true;
    }

    public boolean movePiece(){
        return true;
    }

    public void addNumMoves(){
        numMoves++;
    }

    public int getNumMoves(){
        return numMoves;
    }

    public int getColor() {
        return color;
    }

    public void setThreatSpots(Tile[][] board, Position p, int color){

    }

    public String toString() {
        return "";
    }
}
