package com.example.prashant.youtubeplay;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SearchView;
import android.widget.Toast;

import java.net.URLDecoder;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener{

    private FragmentTransaction ft;
    private boolean doubleBackToExitPressedOnce;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private String searchquerydata;
    private static final String TAG = "MediaPlayerDemo";
    private int mVideoWidth;
    private int mVideoHeight;
    public static MediaPlayer mMediaPlayer;
    private static SurfaceView mPreview;
    private static SurfaceHolder holder;
    private Uri path;

    private static final String MEDIA = "media";
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    private Uri path1;
    private String realpath;
    private String testpath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        ft=getFragmentManager().beginTransaction();

        ft.add(R.id.content,CurrentPlayList.newInstance()).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.playsavedlist) {

        getFragmentManager().beginTransaction().replace(R.id.content,SavedPlayList.newInstance()).commit();
        }
        if(id==R.id.currentlist)
        {
            getFragmentManager().beginTransaction().replace(R.id.content,CurrentPlayList.newInstance()).commit();
        }
        if(id==R.id.history)
        {
            getFragmentManager().beginTransaction().replace(R.id.content,History.newInstance()).commit();
        }
        if(id==R.id.savedlist)
        {
            getFragmentManager().beginTransaction().replace(R.id.content,SavedPlayList.newInstance()).commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        DataStore.searchquery=query;
        getFragmentManager().beginTransaction().replace(R.id.content,SearchResult.newInstance()).commit();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();


    }


}
