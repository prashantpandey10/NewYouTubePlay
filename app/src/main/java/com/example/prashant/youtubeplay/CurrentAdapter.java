package com.example.prashant.youtubeplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prashant on 21/12/14.
 */
public class CurrentAdapter  extends ArrayAdapter<Song>  {
    private final Context context;
    private final ArrayList<Song> songs;


    public CurrentAdapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.currentrow, songs);
        this.context = context;
        this.songs=songs;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.currentrow, parent, false);
        TextView songname = (TextView) rowView.findViewById(R.id.songname);
        songname.setText(songs.get(position).getName());
        return rowView;
    }


}
