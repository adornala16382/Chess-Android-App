package com.example.appchess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.appchess.chess.*;

import java.util.ArrayList;

public class ReplayGame extends AppCompatActivity {

    public int index = 0;
    GridView chessView ;
    public int size =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        index =0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_game);
        ArrayList<Object []> items = (ArrayList<Object []>) getIntent().getSerializableExtra("allGames");
        Button next = (Button) findViewById(R.id.nextMoveBtn);
        Button back = (Button) findViewById(R.id.backBtn2);
        Object [] temp =(Object []) items.get((int)getIntent().getSerializableExtra("index"));
        ArrayList<Board> boards = (ArrayList<Board>) temp[2];
        size = boards.size();
        if(index<size){
            Board b = (Board) boards.get(index);

            b.printBoard();
            int [] images = b.initializeBoardImages();
            chessView = (GridView) findViewById(R.id.chessBoardView2);
            ChessBoardAdapter chessAdapter = new ChessBoardAdapter(getApplicationContext(), images);
            chessView.setAdapter(chessAdapter);

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index+1< size){
                    index++;
                    Board b = (Board) boards.get(index);
                    int [] images = b.initializeBoardImages();
                    ChessBoardAdapter chessAdapter = new ChessBoardAdapter(getApplicationContext(), images);
                    chessView.setAdapter(chessAdapter);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("allGames",allGames);
                startActivity(new Intent(ReplayGame.this,HomePage.class));
            }
        });




    }
}