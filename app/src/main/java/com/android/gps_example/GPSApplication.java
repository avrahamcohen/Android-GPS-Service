package com.android.gps_example;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/*
 * Android Application.
 * First to run.
 * Execute the GPS service.
 */

public class GPSApplication extends Application {
    public void onCreate() {
        super.onCreate();
        turnGPSOn();
        startService(new Intent(this, GPSservice.class));
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
}
