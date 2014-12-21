package com.example.prashant.youtubeplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prashant on 20/12/14.
 */
public class HistoryAdapter extends ArrayAdapter<Song> {
    private final Context context;
    private final ArrayList<Song> songs;


    public HistoryAdapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.historyrow, songs);
        this.context = context;
        this.songs=songs;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.historyrow, parent, false);
        TextView songname = (TextView) rowView.findViewById(R.id.songname);
        songname.setText(songs.get(position).getName());
        return rowView;
    }


}
