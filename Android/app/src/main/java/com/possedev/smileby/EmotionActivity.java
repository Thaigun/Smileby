package com.possedev.smileby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.possedev.smileby.adapters.ImageAdapter;
import com.possedev.smileby.adapters.MessagesAdapter;
import com.possedev.smileby.helper_classes.EmotionImageView;
import com.possedev.smileby.structures.Message;

public class EmotionActivity extends AppCompatActivity {
    private String friend;
    private String chatKey;
    private AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        Intent startingIntent = getIntent();

        friend = startingIntent.getStringExtra("friend");
        chatKey = startingIntent.getStringExtra("key");
        setTitle(friend);

        settings = new AppSettings(this);

        //Build the grid view
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendMessage(view);
            }
        });

        //Build the messages list.
        ListView messagesView = (ListView) findViewById(R.id.messagesView);
        messagesView.setAdapter(new MessagesAdapter(this, friend, chatKey));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emotion, menu);
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

    private void sendMessage(View view) {
        EmotionImageView eView = (EmotionImageView) view;

        Message message = new Message(settings.getUsername(), eView.message);

        String firebaseUrl = getResources().getString(R.string.firebase_url);
        Firebase firebaseRef = new Firebase(firebaseUrl + "chats/" + chatKey + "/messages");
        firebaseRef.push().setValue(message);
    }
}
