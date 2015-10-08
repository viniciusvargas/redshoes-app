package io.redshoes.app.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import io.redshoes.app.R;

/**
 * Created by vinicius on 07/10/15.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<NewsInfo> newsInfoList;
    Context context;

    private static final String imageURI = "http://192.168.88.254:8080/redshoes/static/images";

    public NewsFeedAdapter(List<NewsInfo> newsInfoList) {
        this.newsInfoList = newsInfoList;
    }

    @Override
    public int getItemCount() {
        return newsInfoList.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        NewsInfo newsInfo = newsInfoList.get(i);
        Picasso.with(context).load(newsInfo.imageUrl).into(newsViewHolder.vImage);
        newsViewHolder.vText.setText(newsInfo.text);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_news_feed, viewGroup, false);

        return new NewsViewHolder(itemView);
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImage;
        protected TextView vText;

        public NewsViewHolder(View v) {
            super(v);

            vImage = (ImageView) v.findViewById(R.id.myNfImage);
            vText = (TextView) v.findViewById(R.id.myNfText);
        }
    }

}


