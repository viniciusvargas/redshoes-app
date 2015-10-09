package io.redshoes.app.newsfeed;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.redshoes.app.commons.CommonProperties;
import io.redshoes.app.commons.RestUtils;

/**
 * Created by vinicius on 07/10/15.
 */
public class NewsFeedAsyncTask extends AsyncTask<String, Integer, Double> {

    protected static final String TAG = "NewsFeedActivity";

    private NewsFeedAction action;
    private NewsFeedActivity activity;
    private String jsonResponse;

    public NewsFeedAsyncTask(NewsFeedActivity activity, NewsFeedAction action) {
        this.action = action;
        this.activity = activity;
    }

    private String processLoadNews(String username) {
        username = "teste";
        String url = CommonProperties.BACKEND_URL + action.actionContext + username;
        String jsonResponse = RestUtils.getData(url);

        return jsonResponse;
    }

    private String processShoutNews(String username, String message) {
        String url = CommonProperties.BACKEND_URL + action.actionContext;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", "teste");
            jsonObject.put("message", message);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }

        String jsonResponse = RestUtils.postData(url, jsonObject.toString());

        return jsonResponse;
    }



    @Override
    protected Double doInBackground(String... params) {
        switch (action) {
            case LOAD_NEWS:
                jsonResponse = processLoadNews(params[0]);
                break;
            case SHOUT_NEWS:
                processShoutNews(params[0], params[1]);
        }



        if (action == NewsFeedAction.LOAD_NEWS) {
            processLoadNews(params[0]);
        }
        return null;
    }

    protected void onPostExecute(Double result) {
        JSONArray jsonArray = null;
        String text = null;
        if (jsonResponse != null) {
            try {
                jsonArray = new JSONArray(jsonResponse);
                if (jsonResponse != null) {
                    activity.renderNewsfeedResponse(jsonArray);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error executing onPostExecute.");
            }
        } else {
            activity.renderJSONResponseToActivity(null);
        }

    }

    protected void onProgressUpdate(Integer... progress) {

    }

}

