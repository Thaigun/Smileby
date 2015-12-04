package com.possedev.smileby.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.possedev.smileby.AppSettings;
import com.possedev.smileby.R;
import com.possedev.smileby.structures.Message;

import java.util.ArrayList;

/**
 * Created by Antti on 30.11.2015.
 */
public class MessagesAdapter extends BaseAdapter {
    private Context context;
    private AppSettings settings;

    public MessagesAdapter(Context c) {
        context = c;
        settings = new AppSettings(c);
        //REMOVE THIS SAMPLE MESSAGE
        messages.add(new Message("blaa", "sample_0.jpg"));
        //REMOVE THIS SAMPLE MESSAGE
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            //TODO: This does not work
            view = new TextView(context);

            Message message = messages.get(position);
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(message.getMessage(), "drawable", context.getPackageName());
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            view = (TextView) convertView;
        }

        return view;
    }

    public ArrayList<Message> messages = new ArrayList<Message>();
}
