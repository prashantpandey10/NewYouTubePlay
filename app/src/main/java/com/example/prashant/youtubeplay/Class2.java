package com.example.prashant.youtubeplay;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prashant on 21/12/14.
 */
public class Class2 extends AsyncTask<String, Void, Boolean> {

    String ytapi="http://ytapi.gitnol.com/get.php?id="+DataStore.idofvideotogetpath+"&apikey=fsgdty5k4u_hj555s_r435ffhhjh544j";


    private Context context;
    private String result;
    String title, streamurl;

    private Activity activity;
    private JSONArray jsonArray;
    private String path;
    private String result1;



        /*protected void onPreExecute() {
            dialog.setMessage("Searching Youtube...");
            dialog.show();
        }*/

    @Override
    protected Boolean doInBackground(String... params) {

        TaskHandler handler = new TaskHandler();
        result1 = handler.execute(ytapi);

        try {
            JSONObject jsonObject=new JSONObject(result1);
            JSONObject links=jsonObject.getJSONObject("link");
            jsonArray=links.getJSONArray("17");
            if(jsonArray!=null) {

                path=String.valueOf(jsonArray);
            }
            else
            {
                jsonArray=links.getJSONArray("36");
                if(jsonArray!=null)
                    path=String.valueOf(jsonArray);
                else
                {
                    jsonArray=links.getJSONArray("18");
                    if(jsonArray!=null)
                        path=String.valueOf(jsonArray);
                    else
                        path=String.valueOf(jsonArray);
                }
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
        Log.d("HERE", "path is " + path);
        DataStore.playurl=path;
    }
}

