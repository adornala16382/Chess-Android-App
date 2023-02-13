package com.example.appchess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SavedGames extends AppCompatActivity {

    ListView chessView;
    public ArrayList<Object []> savedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
        chessView =(ListView) findViewById(R.id.gamesListView);
        ChessGame chessGame = new ChessGame();
        Button sortDate = (Button) findViewById(R.id.sortDateBtn);
        Button sortName = (Button) findViewById(R.id.sortNameBtn);
        savedGames =(ArrayList<Object []>) getIntent().getSerializableExtra("allGames");


        if(savedGames!=null){
            String [] titles = new String [savedGames.size()];
            for(int i =0 ; i < savedGames.size();i++){
                titles[i] =(String) savedGames.get(i)[0] +" ||  "+ savedGames.get(i)[1];
            }
            ArrayAdapter<String> arr;
            arr
                    = new ArrayAdapter<String>(
                    this,
                    R.layout.activity_textgame,
                    titles);
            chessView.setAdapter(arr);

        }


        chessView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent newIntent = new Intent(SavedGames.this, ReplayGame.class);
                newIntent.putExtra("index",position);
                newIntent.putExtra("allGames",savedGames);
                startActivity(newIntent);

            }
        });

        sortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("allGames",allGames);
                if(savedGames != null) {
                    savedGames.sort(Comparator.comparing(a -> (String) a[0]));

                    String[] titles = new String[savedGames.size()];
                    for (int i = 0; i < savedGames.size(); i++) {
                        titles[i] = (String) savedGames.get(i)[0] + " ||  " + savedGames.get(i)[1];
                    }
                    ArrayAdapter<String> arr;
                    arr
                            = new ArrayAdapter<String>(
                            SavedGames.this,
                            R.layout.activity_textgame,
                            titles);
                    chessView.setAdapter(arr);
                }
            }
        });
        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("allGames",allGames);
                if(savedGames != null) {
                    savedGames.sort(Comparator.comparing(a -> (LocalDateTime) a[1]));

                    String[] titles = new String[savedGames.size()];
                    for (int i = 0; i < savedGames.size(); i++) {
                        titles[i] = (String) savedGames.get(i)[0] + " ||  " + savedGames.get(i)[1];
                    }
                    ArrayAdapter<String> arr;
                    arr
                            = new ArrayAdapter<String>(
                            SavedGames.this,
                            R.layout.activity_textgame,
                            titles);
                    chessView.setAdapter(arr);
                }
            }
        });












    }
}