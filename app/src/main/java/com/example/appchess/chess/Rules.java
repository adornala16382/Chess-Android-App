package com.example.appchess.chess;

public class Rules {
    /**
     * Creates a threatboard, a 2D tile array with each position having a certain threat, for every piece that is of a color passed in the method
     *
     * @param board is the 2D tile array where threats are assigned
     * @param color is the color of the turn which the threats are being found for
     * @return board, the 2D tile array of threats
     */


    public static Tile[][] findthreats(Tile[][] board, int color, Board b) {
        Tile[][] threatboard = board;

        for (int i = 0; i < threatboard.length; i++) {
            for (int j = 0; j < threatboard.length; j++) {
                if (threatboard[i][j].hasPiece() && threatboard[i][j].getPiece().getColor() == color) {
                    threatboard[i][j].getPiece().setThreatSpots(threatboard, new Position(i, j),color);

                }
            }
        }
        return threatboard;

    }
    /**
     * Using the parameter board, the function finds the location of the king and determines if it is inCheck
     * by calling seeing if its position is under threat (using findthreats)
     *
     * @param board is a 2D Tile array that is to be checked for a check situation
     * @param opp_color is the color of the opponent
     * @return true if the king is found to be in check and false if not
     */

    public static Position findKing(Tile[][] board, int opp_color, Board b){
        String king_color;
        if (ValidityChecks.turn == b.WHITE) {
            king_color = "wK";
        } else {
            king_color = "bK";
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].hasPiece() && board[i][j].getPiece().toString().equals(king_color)) {
                    return new Position(i,j);

                }}}
        return new Position(0,0);
    }
    public static boolean inCheck(Tile[][] board, int opp_color, Board b) {

        Tile[][] threatboard = findthreats(board, opp_color, b);
        System.out.println("THREATBOARD");
        printBoard(threatboard);
        String king_color;
        int row;
        int col;
        if (ValidityChecks.turn == b.WHITE) {
            king_color = "wK";
        } else {
            king_color = "bK";
        }
        for (int i = 0; i < threatboard.length; i++) {
            for (int j = 0; j < threatboard[0].length; j++) {
                if (board[i][j].hasPiece() && board[i][j].getPiece().toString().equals(king_color)) {
                    row = i;
                    col = j;
                    System.out.println("CHECK CHECK "+threatboard[row][col].getThreat());
                    return threatboard[row][col].getThreat();
                }
            }
        }
        return false;

    }
    /**
     * This method determines if there is any position where the king can move or if a check situation can be blocked
     * @return true if there is no way to prevent a checkmate, return false otherwise
     */

    public static boolean checkmate(Board b) {
        Tile[][] threatboard = findthreats(b.getBoard(), ValidityChecks.opponent, b);
        Tile[][] safetyboard = findthreats(b.getBoard(), ValidityChecks.turn, b);
        System.out.println("VALIDITITY CHECKS TURN " + ValidityChecks.opponent);
        int row = 0;
        int col = 0;
        String king_color;
        if (ValidityChecks.turn == b.WHITE) {
            king_color = "wK";
        } else {
            king_color = "bK";
        }
        for (int i = 0; i < threatboard.length; i++) {
            for (int j = 0; j < threatboard[0].length; j++) {
                if (b.getTile(i, j).hasPiece() && b.getTile(i, j).getPiece().toString().equals(king_color)) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        String[][] RedGreen = new String[8][8];

        for (int i = 0; i < threatboard.length; i++) {
            for (int j = 0; j < threatboard[0].length; j++) {
                if (threatboard[i][j].getThreat() && safetyboard[i][j].getThreat()) {
                    RedGreen[i][j] = "RG";
                } else if (threatboard[i][j].getThreat()) {
                    RedGreen[i][j] = "R ";
                } else if (safetyboard[i][j].getThreat()) {
                    RedGreen[i][j] = "G ";
                } else {
                    RedGreen[i][j] = "__";
                }

            }
        }




        // Check if there are empty GREEN spots near the king that it can move to

        if (row + 1 <= 7) {
            if ((RedGreen[row + 1][col].equals("G ") || RedGreen[row + 1][col].equals("__"))
                    && (!b.getTile(row + 1, col).hasPiece()||b.getTile(row + 1, col).getPiece().getColor() != ValidityChecks.turn
            )) {

                return false;
            }
            if (col + 1 <= 7) {
                if ((RedGreen[row + 1][col + 1].equals("G ")  || RedGreen[row + 1][col + 1].equals("__"))
                        && (!b.getTile(row + 1, col + 1).hasPiece()||b.getTile(row + 1, col + 1).getPiece().getColor() != ValidityChecks.turn
                )) {

                    return false;
                }
            }
            if (col - 1 >= 0) {
                if ((RedGreen[row + 1][col - 1].equals("G ")  || RedGreen[row + 1][col - 1].equals("__"))
                        && (!b.getTile(row + 1, col - 1).hasPiece() || b.getTile(row + 1, col - 1).getPiece().getColor() != ValidityChecks.turn
                )) {

                    return false;
                }
            }
        }

        if (row - 1 >= 0) {
            if ((RedGreen[row - 1][col].equals("G ")  || RedGreen[row - 1][col].equals("__") )
                    && (!b.getTile(row - 1, col).hasPiece() || b.getTile(row - 1, col).getPiece().getColor() != ValidityChecks.turn)) {

                return false;
            }
            if (col + 1 <= 7) {
                if ((RedGreen[row - 1][col + 1].equals("G ") || RedGreen[row - 1][col + 1].equals("__"))
                        && (!b.getTile(row - 1, col + 1).hasPiece() || b.getTile(row - 1, col + 1).getPiece().getColor() != ValidityChecks.turn
                )) {

                    return false;
                }
            }
            if (col - 1 >= 0) {
                if ((RedGreen[row - 1][col - 1].equals("G ") || RedGreen[row - 1][col - 1].equals("__"))
                        && (!b.getTile(row - 1, col - 1).hasPiece() || b.getTile(row - 1, col - 1).getPiece().getColor() != ValidityChecks.turn
                )) {

                    return false;
                }
            }
        }

        if (col - 1 >= 0) {
            if ((RedGreen[row][col - 1].equals("G ") || RedGreen[row][col - 1].equals("__"))
                    && (!b.getTile(row, col - 1).hasPiece() || b.getTile(row, col - 1).getPiece().getColor() != ValidityChecks.turn
            )) {

                return false;
            }

        }

        if (col + 1 <= 7) {

            if ((RedGreen[row][col + 1].equals("G ") || RedGreen[row][col + 1].equals("__"))
                    && (!b.getTile(row, col + 1).hasPiece() || b.getTile(row, col + 1).getPiece().getColor() != ValidityChecks.turn
            )) {
                return false;
            }
        }

        Tile [][]temp = b.getBoard();
        temp[row][col].setPiece(null);
        Tile [][]safetytemp = b.getBoard();
        safetytemp[row][col].setPiece(null);
        Tile [][]phantom = findthreats(temp, ValidityChecks.opponent, b);
        Tile [][]safetythreats = findthreats(safetytemp, ValidityChecks.turn, b);
        for (int i = 0; i < phantom.length; i++) {
            for (int j = 0; j < phantom[0].length; j++) {
                if (phantom[i][j].getThreat() && safetythreats[i][j].getThreat()) {
                    RedGreen[i][j] = "RG";
                } else if (phantom[i][j].getThreat()) {
                    RedGreen[i][j] = "R ";
                } else if (safetythreats[i][j].getThreat()) {
                    RedGreen[i][j] = "G ";
                } else {
                    RedGreen[i][j] = "__";
                }

            }
        }
        for(int i =0; i<RedGreen.length;i++){
            for(int j=0;j<RedGreen[0].length;j++) {
                System.out.print(RedGreen[i][j]+ " ");
            }
            System.out.println();
        }


        for(int i =0; i<RedGreen.length;i++){
            for(int j=0;j<RedGreen[0].length;j++){
                if((RedGreen[i][j].equals("RG") ||  RedGreen[i][j].equals("G ")) && (i!=row || j!=col)){
                    Tile [] [] overlap =  b.getBoard();
                    overlap[i][j].setPiece(new Pawn(ValidityChecks.turn));
                    if(!inCheck(overlap, ValidityChecks.opponent, b)){
                        return false;
                    }

                }

            }
        }


        return true;
    }
    /**
     * A helper method used for testing
     * @param threatboard to be printed
     */
    public static void printBoard(Tile[][] threatboard) {
        System.out.println();
        for (int i = 0; i < threatboard.length; i++) {
            for (int j = 0; j < threatboard[i].length; j++) {
                if (threatboard[i][j].getThreat()) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.print(threatboard.length - i);

            System.out.println();
        }
        String[] abc = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int i = 0; i < abc.length; i++) {
            System.out.print(abc[i] + " ");
        }
        System.out.println();
    }

}
