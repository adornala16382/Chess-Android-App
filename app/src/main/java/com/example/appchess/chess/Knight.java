package com.example.appchess.chess;

import java.io.Serializable;

public class Knight extends Piece implements Serializable {
    /**
     * Single argument constructor for knight
     * @param color references the Knight color
     */
    public Knight(int color) {
        super(color);
    }
    /**
     * Attempts to move the knight piece from oldPos to newPos
     * @param oldPos references the old position of the knight
     * @param newPos refernces the new position of the knight
     *
     * @returns false if knight is unable to move, true if it is
     */

    public boolean movePiece(Position oldPos, Position newPos, Board b) {
        int curRow = oldPos.getRow();
        int curCol = oldPos.getCol();
        int newRow = newPos.getRow();
        int newCol = newPos.getCol();

        // check if piece being taken is the opposite color of player
        if (b.getTile(newPos).hasPiece()
                && b.getTile(newPos).getPiece().getColor() == b.getTile(oldPos).getPiece().getColor()) {
            return false;
        }

        // vertical movement
        if ((curRow + 2 == newRow && (curCol + 1 == newCol || curCol - 1 == newCol))
                || (curRow - 2 == newRow && (curCol + 1 == newCol || curCol - 1 == newCol))) {
            return movePiece();
        }
        // horizontal movement
        if ((curRow + 1 == newRow && (curCol + 2 == newCol || curCol - 2 == newCol))
                || (curRow - 1 == newRow && (curCol + 2 == newCol || curCol - 2 == newCol))) {
            return movePiece();
        }

        return false;
    }
    /**
     * Determines the threat spots of a given Knight
     * @param board references the board that the threatspots are being set on
     * @param p is the position of the given Knight
     * @param color references the color of the knight
     */
    public void setThreatSpots(Tile[][] board, Position p, int color) {
        int row = p.getRow();
        int col = p.getCol();
        if (row - 2 >= 0) {
            if (col - 1 >= 0 && (!board[row-2][col-1].hasPiece() || (board[row - 2][col-1].hasPiece() && board[row-2][col-1].getPiece().getColor() !=color))) {
                board[row - 2][col - 1].setThreat(true);
            }
            if (col + 1 <= 7 && (!board[row-2][col+1].hasPiece() || (board[row - 2][col+1].hasPiece() && board[row-2][col+1].getPiece().getColor() !=color))) {
                board[row - 2][col + 1].setThreat(true);
            }

        }

        if (row + 2 <= 7) {
            if (col - 1 >= 0 && (!board[row+2][col-1].hasPiece() || (board[row + 2][col-1].hasPiece() && board[row+2][col-1].getPiece().getColor() !=color))) {
                board[row + 2][col - 1].setThreat(true);
            }
            if (col + 1 <= 7 && (!board[row+2][col+1].hasPiece() || (board[row + 2][col+1].hasPiece() && board[row+2][col+1].getPiece().getColor() !=color))) {
                board[row + 2][col + 1].setThreat(true);

            }

        }
        if (row - 1 >= 0) {
            if (col - 2 >= 0 && (!board[row-1][col-2].hasPiece() || (board[row - 1][col-2].hasPiece() && board[row-1][col-2].getPiece().getColor() !=color))) {
                board[row - 1][col - 2].setThreat(true);
            }
            if (col + 2 <= 7 && (!board[row-1][col+2].hasPiece() || (board[row - 1][col+2].hasPiece() && board[row-1][col+2].getPiece().getColor() !=color))) {
                board[row - 1][col + 2].setThreat(true);
            }

        }
        if (row + 1 <= 7) {
            if (col - 2 >= 0 && (!board[row+1][col-2].hasPiece() || (board[row + 1][col-2].hasPiece() && board[row+1][col-2].getPiece().getColor() !=color))) {
                board[row + 1][col - 2].setThreat(true);
            }
            if(col + 2 <= 7  && (!board[row+1][col+2].hasPiece() || (board[row + 1][col+2].hasPiece() && board[row+1][col+2].getPiece().getColor() !=color))){
                board[row + 1][col + 2].setThreat(true);
            }

        }

    }
    /**
     * The toString method of Knight that prints the correct Knight on the board
     * @see Board
     */

    public String toString() {
        if (super.getColor() == 0) {
            return "wN";
        } else {
            return "bN";
        }
    }
}
