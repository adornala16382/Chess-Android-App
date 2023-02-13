package com.example.appchess.chess;

import com.example.appchess.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {

    public final static int WHITE = 0;
    public final static int BLACK = 1;
    private String prevMove;
    private String name;
    private Tile[][] board = {
            { new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK),
                    new Tile(WHITE), new Tile(BLACK) },
            { new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE),
                    new Tile(BLACK), new Tile(WHITE) },
            { new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK),
                    new Tile(WHITE), new Tile(BLACK) },
            { new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE),
                    new Tile(BLACK), new Tile(WHITE) },
            { new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK),
                    new Tile(WHITE), new Tile(BLACK) },
            { new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE),
                    new Tile(BLACK), new Tile(WHITE) },
            { new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK),
                    new Tile(WHITE), new Tile(BLACK) },
            { new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE), new Tile(BLACK), new Tile(WHITE),
                    new Tile(BLACK), new Tile(WHITE) }
    };

    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return name;
    }

    public Tile[][] getBoard() {
        Tile [] [] current = new Tile [8][8];
        for(int i =0; i<current.length;i++){
            for(int j=0; j<current[0].length;j++){
                current[i][j] = new Tile(board[i][j]);
            }
        }
        return current;
    }

    public void copyBoard(Tile[][] newBoard){
        for(int i =0; i<8;i++){
            for(int j=0;j<8;j++){
                board[i][j] = newBoard[i][j];
            }
        }
    }

    public void setupBoard() {
        // white pawns
        for (int i = 0; i < 8; i++) {
            board[6][i].setPiece(new Pawn(WHITE));
        }
        // black pawns
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(BLACK));
        }
        // Rooks
        board[0][0].setPiece(new Rook(BLACK));
        board[0][7].setPiece(new Rook(BLACK));
        board[7][0].setPiece(new Rook(WHITE));
        board[7][7].setPiece(new Rook(WHITE));

        // Knights
        board[0][1].setPiece(new Knight(BLACK));
        board[0][6].setPiece(new Knight(BLACK));
        board[7][1].setPiece(new Knight(WHITE));
        board[7][6].setPiece(new Knight(WHITE));

        // Bishops
        board[0][2].setPiece(new Bishop(BLACK));
        board[0][5].setPiece(new Bishop(BLACK));
        board[7][2].setPiece(new Bishop(WHITE));
        board[7][5].setPiece(new Bishop(WHITE));

        // Queens
        board[0][3].setPiece(new Queen(BLACK));
        board[7][3].setPiece(new Queen(WHITE));

        // Kings
        board[0][4].setPiece(new King(BLACK));
        board[7][4].setPiece(new King(WHITE));

        for(int i =2;i<6;i++){
            for(int j=0;j<8;j++){
                board[i][j].setPiece(null);
            }
        }

    }

    static final Map<String, Integer> chessToImage  = new HashMap<String, Integer>() {{
        put("wN", R.drawable.knight_white);
        put("bN", R.drawable.knight_black);
        put("wp", R.drawable.pawn_white);
        put("wQ", R.drawable.queen_white);
        put("bQ", R.drawable.queen_black);
        put("bp", R.drawable.pawn_black);
        put("wK", R.drawable.king_white);
        put("bK", R.drawable.king_black);
        put("wR", R.drawable.rook_white);
        put("bR", R.drawable.rook_black);
        put("wB", R.drawable.bishop_white);
        put("bB", R.drawable.bishop_black);

    }};

    static final Map<String, Position> posMap  = new HashMap<String, Position>() {{
                put("a8", new Position(0, 0)); put("b8", new Position(0, 1));
                put("c8", new Position(0, 2)); put("d8", new Position(0, 3));
                put("e8", new Position(0, 4)); put("f8", new Position(0, 5));
                put("g8", new Position(0, 6)); put("h8", new Position(0, 7));
                put("a7", new Position(1, 0)); put("b7", new Position(1, 1));
                put("c7", new Position(1, 2)); put("d7", new Position(1, 3));
                put("e7", new Position(1, 4)); put("f7", new Position(1, 5));
                put("g7", new Position(1, 6)); put("h7", new Position(1, 7));
                put("a6", new Position(2, 0)); put("b6", new Position(2, 1));
                put("c6", new Position(2, 2)); put("d6", new Position(2, 3));
                put("e6", new Position(2, 4)); put("f6", new Position(2, 5));
                put("g6", new Position(2, 6)); put("h6", new Position(2, 7));
                put("a5", new Position(3, 0)); put("b5", new Position(3, 1));
                put("c5", new Position(3, 2)); put("d5", new Position(3, 3));
                put("e5", new Position(3, 4)); put("f5", new Position(3, 5));
                put("g5", new Position(3, 6)); put("h5", new Position(3, 7));
                put("a4", new Position(4, 0)); put("b4", new Position(4, 1));
                put("c4", new Position(4, 2)); put("d4", new Position(4, 3));
                put("e4", new Position(4, 4)); put("f4", new Position(4, 5));
                put("g4", new Position(4, 6)); put("h4", new Position(4, 7));
                put("a3", new Position(5, 0)); put("b3", new Position(5, 1));
                put("c3", new Position(5, 2)); put("d3", new Position(5, 3));
                put("e3", new Position(5, 4)); put("f3", new Position(5, 5));
                put("g3", new Position(5, 6)); put("h3", new Position(5, 7));
                put("a2", new Position(6, 0)); put("b2", new Position(6, 1));
                put("c2", new Position(6, 2)); put("d2", new Position(6, 3));
                put("e2", new Position(6, 4)); put("f2", new Position(6, 5));
                put("g2", new Position(6, 6)); put("h2", new Position(6, 7));
                put("a1", new Position(7, 0)); put("b1", new Position(7, 1));
                put("c1", new Position(7, 2)); put("d1", new Position(7, 3));
                put("e1", new Position(7, 4)); put("f1", new Position(7, 5));
                put("g1", new Position(7, 6)); put("h1", new Position(7, 7));

    }};

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public void undo(){

    }

    public Tile getTile(Position pos) {
        return board[pos.getRow()][pos.getCol()];
    }

    public void setPosition(Position oldPos, Position newPos) {
        System.out.println("SETTING POSITION");
        int curRow = oldPos.getRow();
        int curCol = oldPos.getCol();
        Tile previous = board[curRow][curCol];
        int newRow = newPos.getRow();
        int newCol = newPos.getCol();
        board[newRow][newCol].setPiece(previous.getPiece());
        board[newRow][newCol].getPiece().addNumMoves();
        board[curRow][curCol].setPiece(null);

        Tile [][] newBoard = new Tile [8] [8];
        for(int i =0;i<8;i++){
            for(int j=0;j<8;j++){
                newBoard[i][j] = new Tile(board[i][j]);
             }
        }
    }

    public void setPrevMove(String newPos) {
        prevMove = newPos;
    }

    public Piece getPrevMove() {
        int r = posMap.get(prevMove).getRow();
        int c = posMap.get(prevMove).getCol();
        return getTile(r, c).getPiece();
    }

    public Piece createPiece(String pieceName, int color){
        if(pieceName.equals("R")){
            return new Rook(color);
        }
        else if(pieceName.equals("N")){
            return new Knight(color);
        }
        else if(pieceName.equals("B")){
            return new Bishop(color);
        }
        else if(pieceName.equals("Q")){
            return new Queen(color);
        }
        return null;
    }

    public Position findTile(int index){
        int sum =0;
        for(int i =0; i < board.length;i++){
            for(int j=0;j<board.length;j++) {
                if(sum == index){
                    return new Position(i,j);
                }
                sum++;

            }
        }
        return new Position(0,0);

    }

    public int [] initializeBoardImages(){
        int []  imageList = new int[64];
        int index = 0;
        for(int i =0; i < board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j].hasPiece()){

                    if(chessToImage.containsKey(board[i][j].getPiece().toString())) {
                        imageList[index] = chessToImage.get(board[i][j].getPiece().toString());
                        index++;
                    }
                    else{
                        System.out.println("NOT PRINTED "+ board[i][j].toString());
                    }
                }
                else{
                    imageList[index] = 0;
                    index++;
                }

            }
        }
        return imageList;
    }


    public void printBoard() {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print(board.length - i);

            System.out.println();
        }
        String[] abc = new String[] { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int i = 0; i < abc.length; i++) {
            System.out.print(" " + abc[i] + " ");
        }
        System.out.println();
    }
}
