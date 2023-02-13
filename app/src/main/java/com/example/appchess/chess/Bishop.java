package com.example.appchess.chess;

import java.io.Serializable;

public class Bishop extends Piece implements Serializable {

    /**
     * constructor for Bishop
     * @param color is taken from super class @ Piece
     * @see Piece
     */

    public Bishop(int color) {
        super(color);
    }
    /**
     * Computes the possible moving spots of a bishop at a position and determines whether it can move to a new spot
     *
     * @param oldPos is the old position of the piece trying to be moved
     * @param newPos is the new position of the piece is trying to move
     * @return boolean true if piece is able to move, false if cannot based on movement boundaries
     */


    public boolean movePiece(Position oldPos, Position newPos, Board b) {
        int curRow = oldPos.getRow();
        int curCol = oldPos.getCol();
        int newRow = newPos.getRow();
        int newCol = newPos.getCol();
        // checking if piece being taken is of the opposite color
        if (b.getTile(newPos).hasPiece()
                && b.getTile(newPos).getPiece().getColor() == b.getTile(oldPos).getPiece().getColor()) {
            return false;
        }
        if (Math.abs(curRow - newRow) == Math.abs(curCol - newCol)) {
            int col_index;
            // going down from left to right
            if (curRow < newRow && curCol < newCol) {
                col_index = curCol + 1;

                for (int i = curRow + 1; i < newRow; i++) {
                    if (b.getTile(i, col_index).hasPiece()) {
                        return false;
                    }
                    col_index += 1;
                }
            } else if (curCol < newCol) {
                col_index = curCol + 1;
                // 5,0 3,2


                for (int i = curRow - 1; i > newRow; i--) {

                    if (b.getTile(i, col_index).hasPiece()) {
                        return false;
                    }
                    col_index += 1;
                }
            } else if (curRow < newRow && curCol > newCol) {
                col_index = curCol - 1;

                for (int i = curRow + 1; i < newRow; i++) {
                    if (b.getTile(i, col_index).hasPiece()) {
                        return false;
                    }
                    col_index -= 1;
                }
            } else if (curCol > newCol) {
                col_index = curCol - 1;

                for (int i = curRow - 1; i > newRow; i--) {
                    if (b.getTile(i, col_index).hasPiece()) {
                        return false;
                    }
                    col_index -= 1;
                }

            }

            return movePiece();
        }

        return false;
    }
    /**
     * Creates a 2D tileboard of possible spots on the board that a bishop at position p is threating to and sets the threat to true
     * @param board is the board where the threatspots are supposed to be set
     * @param p is the position the Bishop is currently at
     * @param color is the color of the piece of the bishop
     *
     */

    public void setThreatSpots(Tile[][] board, Position p, int color) {
        int row = p.getRow();
        int col = p.getCol();
        int index_col = col + 1;
        // up left to right
        for (int i = row - 1; i >= 0; i--) {
            if (index_col > 7) {
                break;
            }
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor() != color) {
                board[i][index_col].setThreat(true);
                break;
            }

            if (board[i][index_col].hasPiece()) {
                break;
            }
            board[i][index_col].setThreat(true);
            index_col++;
        }
        // up right to left
        index_col = col - 1;
        for (int i = row - 1; i >= 0; i--) {
            if (index_col < 0) {
                break;
            }
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor() != color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if (board[i][index_col].hasPiece()) {
                break;
            }
            board[i][index_col].setThreat(true);
            index_col--;
        }
        // left to right down
        index_col = col + 1;
        for (int i = row + 1; i <= 7; i++) {
            if (index_col > 7) {
                break;
            }
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor() != color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if (board[i][index_col].hasPiece()) {
                break;
            }
            board[i][index_col].setThreat(true);
            index_col++;
        }

        index_col = col - 1;
        for (int i = row + 1; i <= 7; i++) {
            if (index_col < 0) {
                break;
            }
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor() != color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if (board[i][index_col].hasPiece()) {
                break;
            }
            board[i][index_col].setThreat(true);
            index_col--;
        }

    }


    public String toString() {
        if (super.getColor() == 0) {
            return "wB";
        } else {
            return "bB";
        }
    }
}
