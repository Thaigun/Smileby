package com.possedev.smileby;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.internal.DiskLruCache;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.possedev.smileby.helper_classes.Chat;

import java.util.ArrayList;

/**
 * Created by Antti on 16.11.2015.
 */
public class ChatListAdapter extends BaseAdapter {
    private Firebase userChatsRef;
    private Firebase chatRef;
    private AppSettings settings;

    public ChatListAdapter(Context context) {
        settings = new AppSettings(context);
        userChatsRef = new Firebase("https://radiant-heat-4424.firebaseio.com/users/"+settings.getUsername()+"/chats");
        userChatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat newChat = chatFromSnapshot(dataSnapshot);
                //TODO: add the newChat to the list of chats (and to the UI).
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

    private ArrayList<Chat> chats;

    private Chat chatFromSnapshot(DataSnapshot snapshot) {
        return new Chat("abc");
    }
}
