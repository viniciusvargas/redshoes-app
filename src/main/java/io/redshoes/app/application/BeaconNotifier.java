package io.redshoes.app.application;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconNotifier extends Application implements BootstrapNotifier{
    private static final String TAG = ".MyApplicationName";
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        Log.d(TAG, "App started up");
        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Region region = new Region("ibeaconsRange", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);

        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        // Don't care
    }

    @Override
    public void didEnterRegion(Region arg0) {
        if (isReadyToShowBeacons()) {
            Log.d(TAG, "Got a didEnterRegion call");
            // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
            // if you want the Activity to launch every single time beacons come into view, remove this call.
            regionBootstrap.disable();
            //Intent intent = new Intent(this, MainActivity.class);
            // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
            // created when a user launches the activity manually and it gets launched from here.
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //this.startActivity(intent);
        }
    }

    @Override
    public void didExitRegion(Region arg0) {
        // Don't care
    }

    public static Context getMyApplicationContext() {
        return context;
    }

    private boolean isReadyToShowBeacons() {
        SharedPreferences sharedPref = this.getSharedPreferences("zoone_login_preferences",Context.MODE_PRIVATE);
        return sharedPref.getBoolean("isLogged", false);
    }



}