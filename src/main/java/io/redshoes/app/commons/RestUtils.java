package io.redshoes.app.commons;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by vinicius on 08/10/15.
 */
public class RestUtils {
    public static final String TAG = "RestUtils";

    public static String postData(String serviceUrl, String request) {
        String jsonResponse = null;

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

        return jsonResponse;
    }

    public static String getData(String serviceUrl) {
        String jsonResponse = null;

        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to
        String url = serviceUrl;
        HttpGet httpGet = new HttpGet(serviceUrl);
        httpGet.setHeader("Content-type", "application/json");
        try {
            // send the variable and value, in other words post, to the URL
            HttpResponse response = httpclient.execute(httpGet);
            jsonResponse = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }

        return jsonResponse;
    }
}
