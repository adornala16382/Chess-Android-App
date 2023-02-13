package com.example.appchess.chess;

import java.io.Serializable;

public class Rook extends Piece implements Serializable {
    /**
     * Single argument constructor for Rook
     * @param color references color of the Rook
     */
    public Rook(int color) {
        super(color);
    }
    /**
     * @param castle is true if the rook has not moved
     */

    private boolean castle = true;
    /**
     * Returns the value of attribute castle for Rook
     * @return castle value
     */

    public boolean getCastle() {
        return castle;
    }
    /**
     * Attempts to move Rook from oldPos to newPos
     * @param oldPos references old position of the rook
     * @param newPos references new position of the rook trying to be moved to
     * @return true if rook is able to move, else false
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

        if (curRow == newRow) {
            if (curCol < newCol) {
                for (int i = curCol + 1; i < newCol; i++) {
                    if (b.getTile(curRow, i).hasPiece()) {
                        return false;
                    }
                }

            } else {
                for (int i = curCol - 1; i > newCol; i--) {
                    if (b.getTile(curRow, i).hasPiece()) {
                        return false;
                    }
                }

            }

            return movePiece();
        }
        if (curCol == newCol) {
            if (curRow < newRow) {
                for (int i = curRow + 1; i < newRow; i++) {
                    if (b.getTile(i, curCol).hasPiece()) {
                        return false;
                    }
                }

            } else {
                for (int i = curRow - 1; i > newRow; i--) {
                    if (b.getTile(i, curCol).hasPiece()) {
                        return false;
                    }
                }

            }
            return true;
        }

        return false;
    }
    /**
     * Returns the threat spots of the board based on the position of a rook
     * @param board references the board given to determine the threat spots of rook
     * @param p references position of the rook given
     * @param color references the color of the rook
     */
    public void setThreatSpots(Tile[][] board, Position p, int color) {
        int row = p.getRow();
        int col = p.getCol();
        // vertical spots (up)
        for (int i = row-1; i >= 0; i--) {
            if (board[i][col].hasPiece() && board[i][col].getPiece().getColor() !=color) {
                board[i][col].setThreat(true);
                break;
            }
            if(board[i][col].hasPiece()){
                break;
            }

            board[i][col].setThreat(true);


        }
        // vertical spots (down)
        for (int i = row+1; i <= 7; i++) {
            if (board[i][col].hasPiece() && board[i][col].getPiece().getColor() !=color) {
                board[i][col].setThreat(true);
                break;
            }
            if(board[i][col].hasPiece()){
                break;
            }

            board[i][col].setThreat(true);
        }

        // horizontal spots (left)
        for (int i = col-1; i >= 0; i--) {
            if (board[row][i].hasPiece() && board[row][i].getPiece().getColor() !=color) {
                board[row][i].setThreat(true);
                break;
            }
            if(board[row][i].hasPiece()){
                break;
            }
            board[row][i].setThreat(true);
        }
        // horizontal spots(right)
        for (int i = col+1; i <= 7; i++) {

            if (board[row][i].hasPiece() && board[row][i].getPiece().getColor() !=color) {
                board[row][i].setThreat(true);
                break;
            }
            if(board[row][i].hasPiece()){
                break;
            }
            board[row][i].setThreat(true);
        }
    }
    /**
     * Prints the correct value of teh rook
     * @see Board for printing implementation
     */

    public String toString() {
        if (super.getColor() == 0) {
            return "wR";
        } else {
            return "bR";
        }
    }
}
