package com.example.prashant.youtubeplay;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;

import io.vov.vitamio.MediaPlayer;


public class SearchResult extends Fragment {
    public static final String TAG="SearchResult";
    private TextView searchtext;
    private ListView listview;
    private int currentposition;
    private String songname;
    private String result1;
    private DatabaseOpenHelper dbhelper;
    private Uri path;
    private Uri path1;

    public static SearchResult newInstance(){
        return new SearchResult();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_search_result,container,false);
        searchtext=(TextView)view.findViewById(R.id.textview);
        listview=(ListView)view.findViewById(R.id.list);
        dbhelper=DatabaseOpenHelper.getInstance(getActivity());
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        searchtext.setText(DataStore.searchquery);
        new Class1(getActivity()).execute();

        super.onViewCreated(view, savedInstanceState);
    }

    class Class1 extends AsyncTask<String, Void, Boolean> {

        String url1="https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=AIzaSyDuulG65_84My_ySO4WzoKlJ0Hv080eE5I&maxResults=20&q=";

        String q=DataStore.searchquery.replaceAll(" ","%20");
        String request=url1+""+q;
        ArrayList<Song> searchedsongs=new ArrayList<Song>();


        private Context context;
        private String result;
        String title, streamurl;

        private Activity activity;

        public Class1(Activity activity) {

            this.activity = activity;
            context = activity;
            //      dialog = new ProgressDialog(context);
        }


        /*protected void onPreExecute() {
            dialog.setMessage("Searching Youtube...");
            dialog.show();
        }*/

        @Override
        protected Boolean doInBackground(String... params) {

            TaskHandler handler = new TaskHandler();
            result = handler.execute(request);


            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(result);
                JSONArray jsonArrays=jsonObject.getJSONArray("items");
                for(int i=0;i<19;i++)
                {

                    JSONObject jsonObject1=jsonArrays.getJSONObject(i);
                    JSONObject id=jsonObject1.getJSONObject("id");
                    String videoid=id.getString("videoId");
                    JSONObject snippet=jsonObject1.getJSONObject("snippet");
                    String title=snippet.getString("title");
                    Song s=new Song(videoid,title);
                    searchedsongs.add(s);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
                return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            SearchCustomAdapter adapter=new SearchCustomAdapter(getActivity(),searchedsongs);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentposition=position;
                    Log.d("HERE","clickked");
                    Log.d("HERE","id "+searchedsongs.get(position).getId());
                    DataStore.idofvideotogetpath=searchedsongs.get(position).getId();
                    Log.d("HERE","song is "+searchedsongs.get(position).getName());
                    try{
                    dbhelper.addSong(searchedsongs.get(position),"HISTORY");
                    dbhelper.addSong(searchedsongs.get(position),"CP");
                    Class2 c=new Class2();
                    c.execute();




                    }catch (NullPointerException e)
                    {
                        Log.d("HERE ","dbhelper "+dbhelper);

                    }

                }
            });
        }
    }





}
