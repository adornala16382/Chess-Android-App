package com.example.appchess.chess;

import java.io.Serializable;

public class Pawn extends Piece implements Serializable {
    /**
     *
     * @param nummoved attribute for pawn object to see if a pawn can move up twice
     *
     */

    private int numMoves = 0;
    /**
     * constructor for Pawn
     * @param color is taken from super class @ Piece
     * @see Piece
     */


    public Pawn(int color) {

        super(color);

    }
    /**
     * Computes the possible moving spots of a Pawn at a position and determines whether it can move to a new spot
     *
     * @param oldPos is the old position of the piece trying to be moved
     * @param newPos is the new position of the piece is trying to move
     * @return boolean true if piece is able to move, false if cannot based on movement boundaries
     */


    public boolean movePiece(Position oldPos, Position newPos, Board b) {
        int color = super.getColor();
        int curRow = oldPos.getRow();
        int curCol = oldPos.getCol();
        int newRow = newPos.getRow();
        int newCol = newPos.getCol();
        // for white pawns
        if (color == b.WHITE) {
            // for pawns taking other pieces
            if (curRow - newRow == 1 && (newCol - curCol == 1 || newCol - curCol == -1)
                    && (b.getTile(newRow, newCol).hasPiece() && b.getTile(newRow, newCol).getPiece().getColor() == b.BLACK)) {
                return movePiece();
            }
            // en passant
            else if(curRow - newRow == 1 && (newCol - curCol == 1 || newCol - curCol == -1)
                    && (b.getTile(curRow, newCol).hasPiece() && b.getTile(curRow, newCol).getPiece().getColor() == b.BLACK
                    && b.getTile(curRow, newCol).getPiece() instanceof Pawn && b.getTile(curRow, newCol).getPiece().getNumMoves()==1 && b.getPrevMove().equals(b.getTile(curRow, newCol).getPiece()))){
                b.getTile(curRow, newCol).setPiece(null);
                return movePiece();
            }
            else if (getNumMoves()==0) {
                if (curCol == newCol && newRow + 2 == curRow
                        && (!b.getTile(newRow + 1, newCol).hasPiece())) {
                    return movePiece();
                } else if (curCol == newCol && newRow + 1 == curRow && !b.getTile(newRow, newCol).hasPiece()) {
                    return movePiece();
                }
            } else if (curCol == newCol && newRow + 1 == curRow && !b.getTile(newRow, newCol).hasPiece()) {
                return movePiece();
            }
            // for black pawns
        } else {
            // for pawn taking other pieces
            if (newRow - curRow == 1 && (newCol - curCol == 1 || newCol - curCol == -1)
                    && b.getTile(newRow, newCol).hasPiece()
                    && b.getTile(newRow, newCol).getPiece().getColor() == 0) {
                return movePiece();
            }
            if (getNumMoves()==0) {
                if (curCol == newCol && newRow == curRow + 2 && (!b.getTile(newRow, newCol).hasPiece())) {
                    return movePiece();
                } else if (curCol == newCol && newRow == curRow + 1 && !b.getTile(newRow, curCol).hasPiece()) {
                    return movePiece();
                }
            } else if (curCol == newCol && newRow == curRow + 1 && !b.getTile(newRow, curCol).hasPiece()) {
                return movePiece();
            }
        }
        return false;
    }
    /**
     * Creates a 2D tileboard of possible spots on the board that a Pawn at position p is threating to and sets the threat to true
     * @param board is the board where the threatspots are supposed to be set
     * @param p is the position the Pawn is currently at
     * @param color is the color of the piece of the Pawn
     *
     */

    public void setThreatSpots(Tile[][] board, Position p, int color, Board b) {
        int row = p.getRow();
        int col = p.getCol();
        // threats for white pawns
        if (b.getTile(row,col).getPiece().getColor() == 0) {

            if(!board[row][col].getPiece().getMoved() && !board[row-2][col].hasPiece()){
                board[row - 2][col].setThreat(true);

            }
            // left side white piece
            if (col == 0) {
                if (row - 1 >= 0) {
                    if(board[row - 1][col + 1].hasPiece() && board[row - 1][col + 1].getPiece().getColor()!=color){
                        board[row - 1][col + 1].setThreat(true);
                    }
                }

            } else if (col == 7) {
                if (row - 1 >= 0 && board[row - 1][col - 1].hasPiece() && board[row - 1][col - 1].getPiece().getColor()!=color ) {
                    board[row - 1][col - 1].setThreat(true);
                }
            } else {
                if (row - 1 >= 0) {
                    if(!board[row-1][col].hasPiece()){
                        board[row - 1][col].setThreat(true);
                    }
                    if(board[row - 1][col - 1].hasPiece() && board[row - 1][col - 1].getPiece().getColor()!=color){
                        board[row - 1][col - 1].setThreat(true);
                    }
                    if(board[row - 1][col + 1].hasPiece() && board[row - 1][col + 1].getPiece().getColor()!=color){
                        board[row - 1][col + 1].setThreat(true);
                    }
                }
            }
        }
        // threats for black pawns
        else {
            if(!board[row][col].getPiece().getMoved() && !board[row+2][col].hasPiece()){
                board[row + 2][col ].setThreat(true);
            }

            if (col == 0) {
                if (row + 1 <= 7 && board[row+1][col+1].hasPiece() && board[row + 1][col + 1].getPiece().getColor() != color) {
                    board[row + 1][col + 1].setThreat(true);
                }
            } else if (col == 7) {
                if (row + 1 <= 7 && board[row+1][col-1].hasPiece() && board[row + 1][col - 1].getPiece().getColor() != color) {
                    board[row + 1][col - 1].setThreat(true);
                }
            } else {
                if (row + 1 <= 7) {
                    if(!board[row+1][col].hasPiece()){
                        board[row + 1][col].setThreat(true);
                    }
                    if (col - 1 >= 0 && col + 1 <= 7) {
                        if(board[row + 1][col - 1].hasPiece() && board[row + 1][col - 1].getPiece().getColor() == 0){
                            board[row + 1][col - 1].setThreat(true);
                        }
                        if(board[row + 1][col + 1].hasPiece() && board[row + 1][col + 1].getPiece().getColor() == 0){
                            board[row + 1][col + 1].setThreat(true);
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        if (super.getColor() == 0) {
            return "wp";
        } else {
            return "bp";
        }
    }
}
