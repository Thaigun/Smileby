package com.possedev.smileby.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.possedev.smileby.R;
import com.possedev.smileby.helper_classes.EmotionImageView;
import com.possedev.smileby.helper_classes.EmotionThumbnail;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Antti on 16.11.2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    //References to our images
    private ArrayList<EmotionThumbnail> thumbnails = new ArrayList<>();

    public ImageAdapter(Context c) {
        mContext = c;

        thumbnails.add(new EmotionThumbnail(R.drawable.sample_0, "sample_0"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_1, "sample_1"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_2, "sample_2"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_3, "sample_3"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_4, "sample_4"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_5, "sample_5"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_6, "sample_6"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_7, "sample_7"));
        thumbnails.add(new EmotionThumbnail(R.drawable.sample_8, "sample_8"));
    }

    @Override
    public int getCount() {
        return thumbnails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //if it is not recycled, get the view for that position
            return (EmotionImageView)thumbnails.get(position).getView(mContext);
        } else {
            return (EmotionImageView)convertView;
        }
    }

}
