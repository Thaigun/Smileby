package com.possedev.smileby.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.possedev.smileby.AppSettings;
import com.possedev.smileby.R;
import com.possedev.smileby.helper_classes.Chat;
import com.possedev.smileby.helper_classes.ChatView;

import java.util.ArrayList;

/**
 * Created by Antti on 16.11.2015.
 */
public class ChatListAdapter extends BaseAdapter {
    private Firebase userChatsRef;
    private AppSettings settings;
    private Context mContext;

    public ChatListAdapter(Context context) {
        mContext = context;
        settings = new AppSettings(context);
        chats = new ArrayList<Chat>();

        String firebaseUrl = mContext.getResources().getString(R.string.firebase_url);
        userChatsRef = new Firebase(firebaseUrl+"users/"+settings.getUsername()+"/chats");
        userChatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat newChat = chatFromSnapshot(dataSnapshot);
                chats.add(newChat);
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
        return chats.size();
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
        ChatView chatView;
        if (convertView == null) {
            //if it is not recycled, initialize some attributes
            chatView = new ChatView(mContext);
            chatView.chat = chats.get(position);
        } else {
            chatView = (ChatView) convertView;
        }
        chatView.setText(chatView.chat.getFriend());
        return chatView;
    }

    private ArrayList<Chat> chats;

    private Chat chatFromSnapshot(DataSnapshot snapshot) {
        String friend = (String) snapshot.getValue();
        String key = snapshot.getKey();
        return new Chat(key, friend);
    }
}
