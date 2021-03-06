package com.possedev.smileby;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.possedev.smileby.structures.Encounter;
import com.possedev.smileby.structures.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Antti on 14.10.2015.
 */
public class GlobalApplication extends Application {

    private BeaconManager beaconManager;
    private Firebase firebaseRef;
    public String userName;
    public AppSettings settings;

    @Override
    public void onCreate() {
        super.onCreate();

        settings = new AppSettings(this);

        Firebase.setAndroidContext(this);

        firebaseRef = new Firebase(getResources().getString(R.string.firebase_url));

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                //get the beacon ids: region.getProximityUUID(), getMajor(), getMinor()
                //TODO: Beacon with no major or minor value?
                Beacon firstBeacon = list.get(0);
                String beaconID = region.getProximityUUID() + "|" + firstBeacon.getMajor() + "|" + firstBeacon.getMinor();

                //find the corresponding user for the beacon if one exists
                Query ownerQuery = firebaseRef.child("users").orderByChild("beaconid").equalTo(beaconID);
                ownerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() == 1) {
                            //The matching child node
                            DataSnapshot match = snapshot.getChildren().iterator().next();
                            //Increment the encounters by one
                            incrementEncounters(match.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });
            }

            @Override
            public void onExitedRegion(Region region) {
                //showNotification("Exit title", "Exit message");
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null));
            }
        });
    }

    public void incrementEncounters(final String user2) {
        final String user1 = settings.getUsername();
        //find the correct pair of encounters.
        final String first = (user1.compareTo(user2) < 0) ? user1 : user2;
        final String second = (first == user1) ? user2 : user1;

        Query query = firebaseRef.child("encounters").orderByChild("user1").equalTo(first);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            // The onDataChange will be launched for each "encounter" where user1 is found.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //The matching child nodes
                Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
                Integer count = 0;
                while (children.hasNext()) {
                    DataSnapshot child = children.next();
                    Encounter encounter = child.getValue(Encounter.class);
                    if (encounter.getUser2().equals(second)) {
                        count++;
                        int newNumber = encounter.getEncounters() + 1;
                        firebaseRef.child("encounters/" + child.getKey() + "/encounters").setValue(newNumber);
                        //If enough encounters, show notification to open the quick message option.
                        if (newNumber >= 3) {
                            if (newNumber == 3) {
                                saveNewChat(user2);
                            }
                            openQuickMessage(user2);
                        }
                    }
                }

                // If there was no encounters for these users, make one.
                if (count == 0) {
                    Encounter encounter = new Encounter(first, second, 1);
                    firebaseRef.child("encounters").push().setValue(encounter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void saveNewChat(String userName) {
        //Make a Map (supported by Firebase) of members of the chat. The users are the keys, and values are just some placeholder booleans
        Map<String, Object> chatUsers = new HashMap<String, Object>();
        chatUsers.put(settings.getUsername(), true);
        chatUsers.put(userName, true);
        //push a new generated object to chats.
        Firebase newChatRef = firebaseRef.child("chats").push();
        //Insert one entry for the members of the chat
        newChatRef.child("members").updateChildren(chatUsers);

        // Add the chat id to both users' chats list.
        saveChatIdToBothChatMembers(settings.getUsername(), userName, newChatRef.getKey());
    }

    private void saveChatIdToBothChatMembers(String user1, String user2, String chatKey) {
        saveChatIdToUserReferenceForUser(user1, chatKey, user2);
        saveChatIdToUserReferenceForUser(user2, chatKey, user1);
    }

    private void saveChatIdToUserReferenceForUser(String userWhereSaveTo, String chatKey, String otherMember) {
        Map<String, Object> chatKeyToUser = new HashMap<String, Object>();
        chatKeyToUser.put(chatKey, otherMember);
        firebaseRef.child("users/"+userWhereSaveTo+"/chats").updateChildren(chatKeyToUser);
    }

    public void openQuickMessage(String username) {
        final String friend = username;
        Firebase tempFirebase = firebaseRef.child("chats");
        Query query = tempFirebase.orderByChild("members/" + settings.getUsername());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> chatIterator = dataSnapshot.getChildren().iterator();
                while (chatIterator.hasNext()) {
                    DataSnapshot nextChat = chatIterator.next();
                    Iterator<DataSnapshot> members = nextChat.child("members").getChildren().iterator();
                    while (members.hasNext()) {
                        String nextMember = members.next().getKey();
                        if (nextMember.equals(friend)) {
                            showNotification("You met a familiar stranger", "Tap to send an emotion to " + friend, friend, nextChat.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public void showNotification(String title, String message, String friend, String chatKey) {
        Intent notifyIntent = new Intent(this, com.possedev.smileby.EmotionActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notifyIntent.putExtra("friend", friend);
        notifyIntent.putExtra("key", chatKey);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[] { notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
