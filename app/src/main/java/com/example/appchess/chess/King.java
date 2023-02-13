package com.example.appchess.chess;

import java.io.Serializable;

public class King extends Piece implements Serializable {
    /**
     * Constructor for King
     * @param color is the color of the king
     */
    public King(int color) {
        super(color);
    }
    /**
     * @param castle determines whether a king is able to castle or not
     * @param moved is to determine whether the king has moved
     */

    private boolean castle = true;
    private boolean moved = false;
    /**
     * Returns the value of the castle attribute for king
     * @return castle boolean
     */
    public boolean getCastle() {
        return castle;
    }
    /**
     * Checks to see if the king has moved
     * @return this.moved
     */
    public boolean hasMoved(){
        return this.moved;
    }
    /**
     * Attempts to move the king piece by checking its boundaries and restrictions
     * @param oldPos is the old position of the king before movement
     * @param newPos is the position the king is attempted to be moved
     * @return false if king is unable to move,
     */
    public boolean movePiece(Position oldPos, Position newPos, Board b) {
        int curRow = oldPos.getRow();
        int curCol = oldPos.getCol();
        int newRow = newPos.getRow();
        int newCol = newPos.getCol();
        Tile[][] threatspots = Rules.findthreats(b.getBoard(), ValidityChecks.opponent, b);

        // check if piece being taken is the opposite color of player
        if (b.getTile(newPos).hasPiece()
                && b.getTile(newPos).getPiece().getColor() == b.getTile(oldPos).getPiece().getColor()) {
            return false;
        }
        if(threatspots[newRow][newCol].getThreat()){
            return false;
        }

        if ((curRow + 1 == newRow && curCol == newCol) ||
                (curRow - 1 == newRow && curCol == newCol) ||
                (curRow == newRow && curCol + 1 == newCol) ||
                (curRow == newRow && curCol - 1 == newCol) ||
                (curRow == newRow && curCol + 1 == newCol) ||
                (curRow + 1 == newRow && curCol + 1 == newCol) ||
                (curRow - 1 == newRow && curCol - 1 == newCol) ||
                (curRow + 1 == newRow && curCol - 1 == newCol) ||
                (curRow - 1 == newRow && curCol + 1 == newCol)) {

            return movePiece();

        }
        // castle
        if(!b.getTile(oldPos).getPiece().getMoved() && newRow == curRow){
            if(newCol == (curCol-2) && !b.getTile(curRow, 3).hasPiece() && !b.getTile(curRow, 2).hasPiece() && !b.getTile(curRow, 1).hasPiece()
                    && b.getTile(curRow, 0).hasPiece()
                    && b.getTile(curRow, 0).getPiece() instanceof Rook && !b.getTile(curRow, 0).getPiece().getMoved()){
                if(b.getTile(oldPos).getPiece().getColor() == b.BLACK){
                    b.setPosition(b.posMap.get("a8"), b.posMap.get("d8"));
                }
                else{
                    b.setPosition(b.posMap.get("a1"), b.posMap.get("d1"));
                }
                return movePiece();
            }
            else if(newCol == (curCol+2) && !b.getTile(curRow, 5).hasPiece() && !b.getTile(curRow, 6).hasPiece()
                    && b.getTile(curRow, 7).hasPiece()
                    && b.getTile(curRow, 7).getPiece() instanceof Rook && !b.getTile(curRow, 7).getPiece().getMoved()){
                if(b.getTile(oldPos).getPiece().getColor() == b.BLACK){
                    b.setPosition(b.posMap.get("h8"), b.posMap.get("f8"));
                }
                else{
                    b.setPosition(b.posMap.get("h1"), b.posMap.get("f1"));
                }
                return movePiece();
            }
        }

        return false;
    }

    public void setThreatSpots(Tile[][] board, Position p, int color) {
        int row = p.getRow();
        int col = p.getCol();

        // up left middle right
        if (row + 1 <= 7 && (!board[row+1][col].hasPiece() || (board[row + 1][col].hasPiece() && board[row+1][col].getPiece().getColor() !=color))) {
            board[row + 1][col].setThreat(true);
            if (col + 1 <= 7 && (!board[row+1][col+1].hasPiece() || (board[row + 1][col+1].hasPiece() && board[row+1][col+1].getPiece().getColor() !=color))) {
                board[row + 1][col + 1].setThreat(true);
            }
            if (col - 1 >= 0 && (!board[row+1][col-1].hasPiece() || (board[row + 1][col-1].hasPiece() && board[row+1][col-1].getPiece().getColor() !=color))) {
                board[row + 1][col - 1].setThreat(true);
            }
        }
        // down left middle right
        if (row - 1 >= 0 && (!board[row-1][col].hasPiece() || (board[row - 1][col].hasPiece() && board[row-1][col].getPiece().getColor() !=color))) {
            board[row - 1][col].setThreat(true);
            if (col + 1 <= 7 && (!board[row-1][col+1].hasPiece() || (board[row - 1][col+1].hasPiece() && board[row-1][col+1].getPiece().getColor() !=color))) {
                board[row - 1][col + 1].setThreat(true);
            }
            if (col - 1 >= 0 && (!board[row-1][col-1].hasPiece() || (board[row - 1][col-1].hasPiece() && board[row-1][col-1].getPiece().getColor() !=color))) {
                board[row - 1][col - 1].setThreat(true);
            }
        }
        // left
        if (col - 1 >= 0 && (!board[row][col-1].hasPiece() || (board[row ][col-1].hasPiece() && board[row][col-1].getPiece().getColor() !=color))) {
            board[row][col - 1].setThreat(true);
        }
        // right
        if (col + 1 <= 7 && (!board[row][col+1].hasPiece() || (board[row ][col+1].hasPiece() && board[row][col+1].getPiece().getColor() !=color)))  {
            board[row][col + 1].setThreat(true);
        }

    }
    /**
     * Prints the value of the King
     * @see Board for printing implementation
     */

    public String toString() {
        if (super.getColor() == 0) {
            return "wK";
        } else {
            return "bK";
        }
    }
}
