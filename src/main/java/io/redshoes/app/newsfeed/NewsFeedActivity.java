package io.redshoes.app.newsfeed;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.redshoes.app.R;
import io.redshoes.app.beacon.ActiveBeaconScanner;
import io.redshoes.app.navigationdrawer.NavigationDrawer;

public class NewsFeedActivity extends AppCompatActivity {

    protected static final String TAG = "NewsFeedActivity";
    private String username;

    private Drawer result = null;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.beacon_scanner)
    android.support.design.widget.FloatingActionButton myBeaconScanner;

    @InjectView(R.id.cardList)
    RecyclerView recList;

    @InjectView(R.id.myNfTvShout)
    TextView myNfTvShout;

    @InjectView(R.id.myNfEtShout)
    EditText myNfEtShout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.inject(this);

        //RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        final KeyListener edtTxtMessageKeyListener = myNfEtShout.getKeyListener();
        myNfEtShout.setCursorVisible(false);
        myNfEtShout.setKeyListener(null);
        myNfEtShout.setCursorVisible(true);
        myNfEtShout.setKeyListener(edtTxtMessageKeyListener);

        result = NavigationDrawer.createNavigationDrawer(this, toolbar);

        myBeaconScanner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NewsFeedActivity.this, ActiveBeaconScanner.class);
                NewsFeedActivity.this.startActivity(intent);
                finish();
            }
        });

        myNfTvShout.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                new NewsFeedAsyncTask(NewsFeedActivity.this, NewsFeedAction.SHOUT_NEWS).execute(username, myNfEtShout.getText().toString());
            }
        }));

        try {
            new NewsFeedAsyncTask(this, NewsFeedAction.LOAD_NEWS).execute(username);
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "An error ocurred, please try again.", Snackbar.LENGTH_LONG).show();
        }

        recList.setLayoutManager(llm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void renderJSONResponseToActivity(JSONObject jsonResponse) {

    }

    public void renderNewsfeedResponse(final JSONArray jsonArray) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<NewsInfo> newsInfoList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        NewsInfo newsInfo = new NewsInfo();
                        newsInfo.text = jsonArray.getJSONObject(i).getString("message");
                        newsInfoList.add(newsInfoList.size(), newsInfo);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }

                }

                NewsFeedAdapter adapter = new NewsFeedAdapter(newsInfoList);
                recList.setAdapter(adapter);
            }
        });

    }

}
