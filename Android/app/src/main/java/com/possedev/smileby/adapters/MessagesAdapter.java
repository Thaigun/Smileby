package com.possedev.smileby.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.possedev.smileby.AppSettings;
import com.possedev.smileby.R;
import com.possedev.smileby.structures.Message;

import java.util.ArrayList;

/**
 * Created by Antti on 30.11.2015.
 */
public class MessagesAdapter extends BaseAdapter {
    private Context mContext;
    private AppSettings settings;
    private Firebase firebaseRef;
    private String friend;
    private String key;

    public MessagesAdapter(Context c, String friendStr, String keyStr) {
        mContext = c;
        settings = new AppSettings(c);
        friend = friendStr;
        key = keyStr;
        
        String firebaseUrl = mContext.getResources().getString(R.string.firebase_url);
        firebaseRef = new Firebase(firebaseUrl + "chats/" + key + "/messages");
        firebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message newMessage = dataSnapshot.getValue(Message.class);
                messages.add(newMessage);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
            view = new TextView(mContext);

            Message message = messages.get(position);
            Resources resources = mContext.getResources();
            final int resourceId = resources.getIdentifier(message.getMessage(), "drawable", mContext.getPackageName());

            view.setCompoundDrawablesWithIntrinsicBounds(resourceId, 0,0,0);
        } else {
            view = (TextView) convertView;
        }

        return view;
    }

    public ArrayList<Message> messages = new ArrayList<Message>();
}
