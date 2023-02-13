package com.example.appchess;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.appchess.chess.*;

public class ChessBoardAdapter extends BaseAdapter {
    Context context;
    int [] images;
    int color = 0;
    int column_count =0;
    LayoutInflater inflater;
    public ChessBoardAdapter(Context appContext, int [] images){
        this.context = appContext;
        this.images = images;
        inflater = (LayoutInflater.from(appContext));
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.imageView2);
        if((i/8 + i)%2 !=0){
            icon.setBackgroundColor(Color.rgb(0, 0, 0));
            color = 0;
        }
        else{
            icon.setBackgroundColor(Color.rgb(255, 255, 255));
            color = 1;
        }

        icon.setImageResource(images[i]); // set logo images
        return view;
    }
}
