package com.possedev.smileby.helper_classes;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by makip on 6.12.2015.
 */
public class EmotionImageView extends ImageView {
    public String message;

    public EmotionImageView(Context context) {
        super(context);
    }

    public EmotionImageView(Context context, String msg) {
        super(context);
        message = msg;
    }
}
