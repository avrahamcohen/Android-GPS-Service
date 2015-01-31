package com.android.gps_example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * This Activity doing nothing :)
 * We just need it because Android
 * need to run default activity ...
 */

public class GPSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        setContentView(R.layout.activity_main);

        /* We can start the service from here,
            But the idea is not to load the Activity.
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, GPSservice.class));
    }
}
