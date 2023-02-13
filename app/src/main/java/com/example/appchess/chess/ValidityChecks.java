package com.example.appchess.chess;

public class ValidityChecks {
    public static int turn;
    public static int opponent;
    /**
     * No arg constructor
     */

    public ValidityChecks() {
    }
    /**
     * Checks whether the piece is at the position
     * @param pos is the position String
     * @return true if the board has a piece or not
     */

    public static boolean checkPiece(Position pos, Board b) {
        return (b.getTile(pos).hasPiece() && b.getTile(pos).getPiece().getColor() != ValidityChecks.opponent);
    }

    public static boolean runAllChecks(Position oldPos, Position newPos, Board b) {
        
        if (oldPos == newPos) {
            return false;
        }

        if (!checkPiece(oldPos, b)) {
            return false;
        }
        System.out.println(b.getTile(newPos).toString());
        if(b.getTile(newPos).hasPiece() && b.getTile(newPos).getPiece() instanceof King ){
            return false;
        }
        if (!b.getTile(oldPos).getPiece().movePiece(oldPos, newPos, b)) {
            return false;
        }

        if (Rules.inCheck(b.getBoard(), ValidityChecks.opponent, b)) {
            Tile[][] checkmovement = b.getBoard();
            checkmovement[newPos.getRow()][newPos.getCol()].setPiece(b.getTile(oldPos).getPiece());
            checkmovement[oldPos.getRow()][oldPos.getCol()].setPiece(null);
            System.out.println();
            if (Rules.inCheck(checkmovement, ValidityChecks.opponent, b)) {
                return false;
            }
        }
        Tile[][] tempBoard = b.getBoard();
        Position king_spot = Rules.findKing(tempBoard,ValidityChecks.opponent,b);
        tempBoard[newPos.getRow()][newPos.getCol()].setPiece(b.getTile(oldPos).getPiece());
        tempBoard[oldPos.getRow()][oldPos.getCol()].setPiece(null);
        tempBoard[king_spot.getRow()][king_spot.getCol()].setPiece(null);
        Tile [] [] checkBoard = Rules.findthreats(tempBoard,ValidityChecks.opponent,b);
        if(checkBoard[king_spot.getRow()][king_spot.getCol()].getThreat()){
            return false;
        }

        return true;
    }
}
