package com.possedev.smileby.helper_classes;

import android.content.Context;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by makip on 6.12.2015.
 */
public class EmotionThumbnail {
    private Integer resourceKey;
    private String messageStr;

    public EmotionThumbnail(Integer resource, String message) {
        resourceKey = resource;
        messageStr = message;
    }

    public EmotionImageView getView(Context c) {
        EmotionImageView view = new EmotionImageView(c, messageStr);

        view.setLayoutParams(new GridView.LayoutParams(200, 200)); //TODO: How to scale to the screen? In layout .xml, there was something.
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setPadding(8, 8, 8, 8);
        view.setImageResource(resourceKey);
        return view;
    }

}
