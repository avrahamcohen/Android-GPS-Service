package com.android.gps_example;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/*
 * This Service showing GPS location
 * in the notification area ..
 */

public class GPSservice extends Service implements LocationListener {

    private final String TAG = "GPS-Service";
    private final int NOTIFICATION_ID=10643668;

    private NotificationCompat.Builder mNbuilder=null;
    private Handler mHandler = new Handler();

    private double latitude = 0;
    private double longitude = 0;

    public GPSservice() {
        Log.i(TAG, "Starting Service !");
    }

    @Override
    public void onCreate () {
        super.onCreate();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
            Log.i(TAG, "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());

        mNbuilder = new NotificationCompat.Builder(this);
        mNbuilder.setSmallIcon(R.drawable.ic_launcher);
        mNbuilder.setContentTitle("GPS Service");

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else {
            latitude = 0;
            longitude = 0;
        }

        mNbuilder.setContentText("Latitude: " + latitude + ", Longitude: " + longitude);
        mNbuilder.setPriority(Notification.PRIORITY_MAX);
        mNbuilder.setAutoCancel(true);

        Intent notificationIntent = new Intent(this, GPSActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNbuilder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, mNbuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Stopping Service !");
    }

    private void updateGPSLocation() {
        mNbuilder.setContentText("Latitude: " + latitude + ", Longitude: " + longitude);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, mNbuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        updateGPSLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
