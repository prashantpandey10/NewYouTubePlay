/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;

public class MediaPlayerDemo_Audio extends Activity {

	private static final String TAG = "MediaPlayerDemo";
	private MediaPlayer mMediaPlayer;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private String path;

	private TextView tx;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		tx = new TextView(this);
		setContentView(tx);
		Bundle extras = getIntent().getExtras();
		playAudio(extras.getInt(MEDIA));
	}

	private void playAudio(Integer media) {
		try {
			switch (media) {
			case LOCAL_AUDIO:
				/**
				 * TODO: Set the path variable to a local audio file path.
				 */
				path = "http://r5---sn-q4f7dn7y.c.docs.google.com/videoplayback?sparams=dur,id,ip,ipbits,itag,mm,ms,mv,source,upn,expire&fexp=900147,907259,927622,932404,943909,943917,947209,948124,952302,952605,952901,953912,957103,957105,957201&key=yt5&source=youtube&expire=1417524307&upn=tUoX1roz6Pc&dur=0.000&id=o-AKXagJB8W6h5voPd4UBM9j3Mdz1hKSZHTCOrkcpD0Qys&ms=au&mv=u&mt=1417502542&itag=43&sver=3&ip=107.178.200.11&mm=31&ipbits=0&signature=9A1544B644647D075A9D7BECE78C72007110D3C5.6F2736C81F1C48068EC92F8376BB6B7D5A416DCE&ratebypass=yes";
				if (path == "") {
					// Tell the user to provide an audio file URL.
					Toast.makeText(MediaPlayerDemo_Audio.this, "Please edit MediaPlayer_Audio Activity, " + "and set the path variable to your audio file path." + " Your audio file must be stored on sdcard.", Toast.LENGTH_LONG).show();
					return;
				}
				mMediaPlayer = new MediaPlayer(this);
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				break;
			case RESOURCES_AUDIO:
				/**
				 * TODO: Upload a audio file to res/raw folder and provide its resid in
				 * MediaPlayer.create() method.
				 */
				//Bug need fixed
				mMediaPlayer = createMediaPlayer(this, R.raw.test_cbr);
				mMediaPlayer.start();

			}
			tx.setText("Playing audio...");

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}

	}

	public MediaPlayer createMediaPlayer(Context context, int resid) {
		try {
			AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
			MediaPlayer mp = new MediaPlayer(context);
			mp.setDataSource(afd.getFileDescriptor());
			afd.close();
			mp.prepare();
			return mp;
		} catch (IOException ex) {
			Log.d(TAG, "create failed:", ex);
			// fall through
		} catch (IllegalArgumentException ex) {
			Log.d(TAG, "create failed:", ex);
			// fall through
		} catch (SecurityException ex) {
			Log.d(TAG, "create failed:", ex);
			// fall through
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}
}
