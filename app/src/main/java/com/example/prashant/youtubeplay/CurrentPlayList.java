package com.example.prashant.youtubeplay;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class CurrentPlayList extends Fragment {

    public static final String TAG="CurrentPlayList";
    public static CurrentPlayList newInstance(){
        return new CurrentPlayList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_current_play_list,container,false);
        final DatabaseOpenHelper dbhelper=DatabaseOpenHelper.getInstance(getActivity());

        ArrayList<Song> historysongs=dbhelper.getAllSongs("HISTORY");
        CurrentAdapter adapter=new CurrentAdapter(getActivity(),historysongs);
        ListView listView=(ListView)view.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
