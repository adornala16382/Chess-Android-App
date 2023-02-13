package com.example.appchess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appchess.chess.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.BreakIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class ChessGame extends AppCompatActivity implements Serializable {
    GridView chessBoardView;
    int [] board_images;
    boolean playing = true;
    Board b;
    Boolean justUndoed;
    ArrayList<Object []> allGames = new ArrayList<>();
    final static String ILLEGAL_MOVE_MESSAGE = "Illegal move, try again";
    final static String WHITE_MOVE = "White's move: ";
    final static String BLACK_MOVE = "Black's move: ";
    final static String WHITE_WINS = "White wins";
    final static String BLACK_WINS = "Black wins";
    final static String DRAW = "draw";
    final static String RESIGN = "resign";
    public static int curTurn;
    ArrayList<Board> curGame;

    public static ArrayList<Integer> selectedItems = new ArrayList<Integer>();

    public ArrayList<Integer> getSelectedItems(){
        return selectedItems;
    }
    public void addItem(Integer a){
        selectedItems.add(a);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_game);
        TextView turnText = (TextView) findViewById(R.id.turnText);
        chessBoardView = (GridView)findViewById(R.id.chessBoardView);
        Button resignBtn = (Button)findViewById(R.id.resignBtn);
        Button drawBtn = (Button)findViewById(R.id.drawBtn);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button undo = (Button)findViewById(R.id.undoBtn);
        Button aiBtn = (Button)findViewById(R.id.aiBtn);
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        Intent intent = new Intent(this,HomePage.class);

        curTurn = 0;
        curGame = new ArrayList<>();
        justUndoed = false;
        playing = true;
        try {
            //remove
            //writeApp(allGames);
            allGames = readApp();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        chessBoardView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        //set values to 0 and setup the board
        curTurn = 0;
        ValidityChecks.turn = 0;
        ValidityChecks.opponent = 1;
        b = new Board();
        b.setupBoard();
        curGame.add(b);
        try {
            writeGame(curGame);
            curGame = readGame();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int [] images = b.initializeBoardImages();
        ChessBoardAdapter chessAdapter = new ChessBoardAdapter(getApplicationContext(), images);
        selectedItems.clear();
        chessBoardView.setAdapter(chessAdapter);
        chessBoardView.setDrawSelectorOnTop(true);

        if(curTurn == 0 ){
            turnText.setText("White Move!");
        }
        else{
            turnText.setText("Black Move!");
        }

        chessBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int [] images2 = b.initializeBoardImages();
                chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images2));
                Integer selectedItem = (Integer) (chessBoardView.getItemAtPosition(position));
                addItem(selectedItem);
                TextView turnText = (TextView) findViewById(R.id.turnText);
                if(selectedItems.size() == 1 && playing && !b.getTile(b.findTile(selectedItems.get(0))).hasPiece()) {
                    selectedItems.clear();
                }
                else if(selectedItems.size() == 2 && playing){
                    Position oldPos = b.findTile(selectedItems.get(0));
                    Position newPos = b.findTile(selectedItems.get(1));
                    if(ValidityChecks.runAllChecks(oldPos, newPos, b)){
                        b.setPosition(oldPos,newPos);
                        justUndoed = false;
                        curGame.add(b);
                        try {
                            writeGame(curGame);
                            curGame = readGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        int [] images = b.initializeBoardImages();
                        chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                        if(curTurn ==0 ){
                            curTurn = 1;
                            ValidityChecks.turn = 1;
                            ValidityChecks.opponent = 0;
                            turnText.setText("Black's Move");
                        }
                        else{
                            curTurn =0;
                            ValidityChecks.turn = 0;
                            ValidityChecks.opponent = 1;
                            turnText.setText("White's Move");
                        }
                    }
                    else{
                        System.out.println("Yes");
                        if(curTurn == 0){
                            turnText.setText("White's Move: NOT VALID!");
                        }
                        else {
                            turnText.setText("Black's Move: NOT VALID!");
                        }
                        chessBoardView.clearChoices();
                    }
                    selectedItems.clear();
                    run();
                    System.out.println(newPos.getRow());
                    System.out.println(b.getTile(newPos).getPiece() instanceof Pawn);
                    System.out.println(curTurn);

                    if((newPos.getRow() == 0 && b.getTile(newPos).getPiece() instanceof Pawn && curTurn == b.BLACK)
                            || (newPos.getRow() == 7 && b.getTile(newPos).getPiece() instanceof Pawn && curTurn == b.WHITE)) {
                        System.out.println("YES I AM PROMOTED ");
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChessGame.this);
                        String [] piece_types = {"Queen", "Bishop", "Rook","Knight"};

                        mBuilder.setTitle("Choose a Promotion Piece").setItems(piece_types, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                String replacement = piece_types[which];
                                Piece promoted = new Piece(curTurn);
                                if(replacement.equals("Queen")){
                                    if(curTurn == 1){
                                         promoted = new Queen(0);
                                    }
                                    if(curTurn == 0){
                                        promoted = new Queen(1);
                                    }
                                    b.getTile(newPos).setPiece(promoted);
                                    int [] images = b.initializeBoardImages();
                                    chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                                }
                                if(replacement.equals("Knight")){
                                    if(curTurn == 1){
                                        promoted = new Knight(0);
                                    }
                                    if(curTurn == 0){
                                        promoted = new Knight(1);
                                    }
                                    b.getTile(newPos).setPiece(promoted);
                                    int [] images = b.initializeBoardImages();
                                    chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                                }
                                if(replacement.equals("Rook")){
                                    if(curTurn == 1){
                                        promoted = new Rook(0);
                                    }
                                    if(curTurn == 0){
                                        promoted = new Rook(1);
                                    }
                                    b.getTile(newPos).setPiece(promoted);
                                    int [] images = b.initializeBoardImages();
                                    chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                                }
                                if(replacement.equals("Bishop")){
                                    if(curTurn == 1){
                                        promoted = new Bishop(0);
                                    }
                                    if(curTurn == 0){
                                        promoted = new Bishop(1);
                                    }
                                    b.getTile(newPos).setPiece(promoted);
                                    int [] images = b.initializeBoardImages();
                                    chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                                }
                            }
                        });
                        mBuilder.show();
                    }
                }
            }});

        //Temporary functionality for resign button
        resignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing){
                    TextView turnText = findViewById(R.id.turnText);
                    if(curTurn == 0){
                        turnText.setText("Resigned Black Won!");
                    }
                    else{
                        turnText.setText("Resigned White Won!");
                    }

                    playing = false;
                }

                //startActivity(new Intent(ChessGame.this, HomePage.class));
            }
        });

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing){
                    TextView turnText = findViewById(R.id.turnText);
                    turnText.setText("Draw!");
                    playing = false;
                }

                //startActivity(new Intent(ChessGame.this, HomePage.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("allGames",allGames);
                startActivity(intent);
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(playing && justUndoed == false){
                    b = curGame.get(curGame.size()-2);
                    curGame.remove(curGame.size()-1);
                    switchTurn();
                    try {
                        writeGame(curGame);
                        curGame = readGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    justUndoed = true;
                    int [] images = b.initializeBoardImages();
                    chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                }
                else{

                }
            }
        });

        aiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playing){
                    Tile [][] safetyboard;
                    ArrayList<Position> pieces = new ArrayList<>();
                    ArrayList<Position> places = new ArrayList<>();
                    //looks at the possible places you can move
                    safetyboard = Rules.findthreats(b.getBoard(),ValidityChecks.turn, b);
                    Rules.printBoard(safetyboard);
                    //populate a list of pieces of a user
                    for(int i =0; i <8;i++){
                        for(int j=0;j<8;j++){
                            if(b.getTile(i,j).hasPiece() && b.getTile(i,j).getPiece().getColor() == curTurn){
                                pieces.add(new Position(i,j));
                            }
                            if(safetyboard[i][j].getThreat()){
                                places.add(new Position(i,j));
                            }
                        }
                    }
                    boolean made_move = false;
                    Random random = new Random();
                    System.out.println("SIZE  " +pieces.size());
                    int option =0;
                    if(!pieces.isEmpty()){
                        option=  random.nextInt(pieces.size() );
                    }
                    System.out.println(places.get(0).getRow());
                    while(!made_move){
                        if(!pieces.isEmpty()){
                            option=  random.nextInt(pieces.size() );
                            System.out.println(b.getTile(pieces.get(option)).getPiece());
                        }
                        else{
                            break;
                        }
                        for(int i =0;i<places.size();i++){
                            if(b.getTile(pieces.get(option)).getPiece().movePiece(pieces.get(option),places.get(i),b)){
                                if(places.get(i).getRow() ==0 ||places.get(i).getRow() ==7 && b.getTile(pieces.get(option)).getPiece() instanceof Pawn){
                                    Piece promoted = new Piece(curTurn);
                                    if(curTurn == 1){
                                        promoted = new Queen(0);
                                    }
                                    if(curTurn == 0){
                                        promoted = new Queen(1);
                                    }
                                    b.getTile(places.get(i)).setPiece(promoted);
                                    b.getTile(pieces.get(option)).setPiece(null);
                                }
                                else{
                                    b.setPosition(pieces.get(option),places.get(i));
                                }
                                System.out.println("yes i did it");

                                justUndoed = false;
                                curGame.add(b);
                                try {
                                    writeGame(curGame);
                                    curGame = readGame();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                int [] images = b.initializeBoardImages();
                                chessBoardView.setAdapter(new ChessBoardAdapter(getApplicationContext(), images));
                                chessBoardView.clearChoices();
                                made_move = true;
                                switchTurn();
                                break;
                            }
                        }
                        if(!made_move){
                            pieces.remove(option);
                        }
                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playing) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChessGame.this);

                    alert.setTitle("Save This Game");
                    alert.setMessage("Enter a title for this game");

                    // Set an EditText view to get user input
                    EditText input = new EditText(ChessGame.this);
                    alert.setView(input);

                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = String.valueOf(input.getText());
                            b.setName(value);
                            LocalDateTime now = LocalDateTime.now();
                            allGames.add(new Object []{value, now, curGame});
                            try {
                                writeApp(allGames);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("allGames",allGames);
                            startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();
                }
            }
        });
    }

    public void writeApp(ArrayList<Object []> allGames) throws IOException {
        FileOutputStream fos = getApplicationContext().openFileOutput("Boards.dat", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(allGames);
    }

    public ArrayList<Object []> readApp() throws IOException, ClassNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput("Boards.dat");
        ObjectInputStream is = new ObjectInputStream(fis);
        allGames = (ArrayList<Object []>)is.readObject();
        return allGames;
    }

    public void writeGame(ArrayList<Board> curGame) throws IOException {
        FileOutputStream fos = getApplicationContext().openFileOutput("curGame.dat", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(curGame);
    }

    public ArrayList<Board> readGame() throws IOException, ClassNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput("curGame.dat");
        ObjectInputStream is = new ObjectInputStream(fis);
        curGame = (ArrayList<Board>)is.readObject();
        return curGame;
    }

    public void switchTurn(){
        TextView turnText = findViewById(R.id.turnText);
        if(curTurn ==0 ){
            curTurn = 1;
            ValidityChecks.turn = 1;
            ValidityChecks.opponent = 0;
            turnText.setText("Black Move!");
        }
        else{
            curTurn =0;
            ValidityChecks.turn = 0;
            ValidityChecks.opponent = 1;
            turnText.setText("White Move!");
        }
    }
    public void run(){
        TextView turnText = findViewById(R.id.turnText);
        if (curTurn == b.WHITE) {
            ValidityChecks.turn = b.WHITE;
            ValidityChecks.opponent = b.BLACK;
            System.out.println("Check status "+ Rules.inCheck(b.getBoard(), ValidityChecks.opponent, b));
            if(Rules.inCheck(b.getBoard(), ValidityChecks.opponent, b)){
                if(Rules.checkmate(b)){
                    turnText.setText("Checkmate! Black Wins");
                    System.out.println("checkmate!");
                    System.out.println(BLACK_WINS);
                }else{
                    turnText.setText("In check! (White turn)");
                }
            }
            System.out.print(WHITE_MOVE);
        }
        else{
            ValidityChecks.turn = b.BLACK;
            ValidityChecks.opponent = b.WHITE;
            if(Rules.inCheck(b.getBoard(), ValidityChecks.opponent, b)){
                if(Rules.checkmate(b)){
                    turnText.setText("Checkmate! White Wins");
                    System.out.println("checkmate!");
                    System.out.println(WHITE_WINS);
                }
                else{
                    turnText.setText("In check! (Black turn) ");
                }
                System.out.println("In check");
            }
            System.out.print(BLACK_MOVE);
        }
    }
}
