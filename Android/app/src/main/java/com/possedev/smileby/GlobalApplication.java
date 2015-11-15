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

import java.util.List;
import java.util.UUID;

/**
 * Created by Antti on 14.10.2015.
 */
public class GlobalApplication extends Application {

    private BeaconManager beaconManager;
    private Firebase firebaseRef;
    public String userName;
    public AppSettings settings;

    public User latestEncounter;

    @Override
    public void onCreate() {
        super.onCreate();

        settings = new AppSettings(this);

        Firebase.setAndroidContext(this);

        firebaseRef = new Firebase("https://radiant-heat-4424.firebaseio.com/");

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
                            //The user that was encountered
                            latestEncounter = match.getValue(User.class);
                            //Increment the encounters by one
                            incrementEncounters(settings.getUsername(), match.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });




                //If enough encounters, show notification to open the quick message option.

                //showNotification("You entered the area", "Touch to open BLE app");
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

    public void incrementEncounters(final String user1, final String user2) {
        //find the correct pair of encounters.
        final String first = (user1.compareTo(user2) < 0) ? user1 : user2;
        final String second = (first == user1) ? user2 : user1;

        Query query = firebaseRef.child("encounters").orderByChild("user1").equalTo(first);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            // The onDataChange will be launched for each "encounter" where user1 is found.
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Encounter encounter = snapshot.getValue(Encounter.class);
                if (encounter.getUser2() == second) {
                    int newNumber = encounter.getEncounters() + 1;
                    firebaseRef.child("encounters/" + snapshot.getKey() + "/encounters").setValue(newNumber);
                    if (newNumber >= 3) {
                        openQuickMessage(user2);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void openQuickMessage(String username) {
        showNotification("New familiar stranger", "Tap to send an emotion");
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, EmotionActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[] { notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
