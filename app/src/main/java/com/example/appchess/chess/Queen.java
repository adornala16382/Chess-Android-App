package com.example.appchess.chess;

import java.io.Serializable;

public class Queen extends Piece implements Serializable {
    /**
     * constructor for Queen
     * @param color is taken from super class @ Piece
     * @see Piece
     */

    public Queen(int color) {
        super(color);
    }
    /**
     * Computes the possible moving spots of a Queen at a position and determines whether it can move to a new spot; Queen has movements of Rook and Bishop combined
     *
     * @param oldPos is the old position of the piece trying to be moved
     * @param newPos is the new position of the piece is trying to move
     * @see Rook
     * @see Bishop
     * @return boolean true if piece is able to move, false if cannot based on movement boundaries
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

        // Rook movement (vertically and sideways)
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

            return true;
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
        // Bishop Movement (diagonally)
        if (Math.abs(curRow - newRow) == Math.abs(curCol - newCol)) {
            int col_index;
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

            return true;
        }

        return false;
    }
    /**
     * Creates a 2D tileboard of possible spots on the board that a Queen at position p is threating to and sets the threat to true
     * @param board is the board where the threatspots are supposed to be set
     * @param p is the position the Queen is currently at
     * @param color is the color of the piece of the Queen
     *
     */

    public void setThreatSpots(Tile[][] board, Position p, int color) {
        int row = p.getRow();
        int col = p.getCol();

        // BISHOP MOVEMENT
        int index_col = col + 1;
        for (int i = row - 1; i >= 0; i--) {
            if (index_col > 7) {
                break;
            }
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor()!=color) {
                board[i][index_col].setThreat(true);
                break;
            }

            if(board[i][index_col].hasPiece()){
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
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor()!=color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if(board[i][index_col].hasPiece()){
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
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor()!=color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if(board[i][index_col].hasPiece()){
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
            if (board[i][index_col].hasPiece() && board[i][index_col].getPiece().getColor()!=color) {
                board[i][index_col].setThreat(true);
                break;
            }
            if(board[i][index_col].hasPiece()){
                break;
            }
            board[i][index_col].setThreat(true);
            index_col--;
        }
        /********************************************************** */
        // ROOK MOVEMENT
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
     * Tostring method of Queen that prints the correct value of Queen on the board
     */


    public String toString() {
        if (super.getColor() == 0) {
            return "wQ";
        } else {
            return "bQ";
        }
    }
}
