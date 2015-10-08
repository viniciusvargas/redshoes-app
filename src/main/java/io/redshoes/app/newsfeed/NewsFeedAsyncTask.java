package io.redshoes.app.newsfeed;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by vinicius on 07/10/15.
 */
public class NewsFeedAsyncTask extends AsyncTask<String, Integer, Double> {

    protected static final String TAG = "NewsFeedActivity";

    private static final String serviceUrl = "";
    private NewsFeedActivity activity;
    private String jsonResponse;

    public NewsFeedAsyncTask(NewsFeedActivity activity) {
        this.activity = activity;
    }

    public void postData(String request) {
        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to
        HttpPost httppost = new HttpPost(serviceUrl);
        httppost.setHeader("Content-type", "application/json");
        try {
            StringEntity entity = new StringEntity(request);
            httppost.setEntity(entity);
            // send the variable and value, in other words post, to the URL
            HttpResponse response = httpclient.execute(httppost);
            jsonResponse = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }
    }


        @Override
        protected Double doInBackground(String... params) {
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result){
            JSONObject jsonObject = null;
            String text = null;
            try {
                jsonObject = new JSONObject(jsonResponse);
                if (jsonResponse != null) {
                    activity.renderJSONResponseToActivity(jsonObject);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error executing onPostExecute.");
            }

        }

        protected void onProgressUpdate(Integer... progress){

        }

}
