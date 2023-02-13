package com.example.appchess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appchess.R;

public class HomePage extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();

        Button newGameBtn = (Button)findViewById(R.id.newGameBtn);
        Button watchGame = (Button) findViewById(R.id.watchGameBtn);

        //back btm


        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ChessGame.class));
            }
        });

        watchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(HomePage.this, SavedGames.class);

                newIntent.putExtra("allGames",intent.getSerializableExtra("allGames"));
                startActivity(newIntent);
            }
        });



    }
}