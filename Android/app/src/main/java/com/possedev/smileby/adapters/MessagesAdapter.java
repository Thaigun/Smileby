package com.possedev.smileby.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.possedev.smileby.structures.Message;

import java.util.ArrayList;

/**
 * Created by Antti on 30.11.2015.
 */
public class MessagesAdapter extends BaseAdapter {
    private Context context;

    public MessagesAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public ArrayList<Message> messages = new ArrayList<Message>();
}
