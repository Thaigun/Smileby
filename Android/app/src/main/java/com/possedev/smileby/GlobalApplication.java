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
import com.firebase.client.ValueEventListener;
import com.possedev.smileby.structures.AppSettings;

import java.util.List;
import java.util.UUID;

/**
 * Created by Antti on 14.10.2015.
 */
public class GlobalApplication extends Application {

    private BeaconManager beaconManager;
    private Firebase firebaseRef;
    public String userName;
    public AppSettings settings = new AppSettings();

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

        firebaseRef = new Firebase("https://radiant-heat-4424.firebaseio.com/");

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                firebaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        //Log.e("Smileby", snapshot.getValue().toString());  //prints "Do you have data? You'll love Firebase."
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });

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
                beaconManager.startMonitoring(new Region("monitored region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), 16432, 59483));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
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
