package io.redshoes.app.beacon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import io.redshoes.app.R;

public class ActiveBeaconScanner extends Activity implements BeaconConsumer{

    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager;
    private static boolean activityVisible;

    private void startBeaconSearch() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        //beaconManager.setForegroundBetweenScanPeriod(2000);
        beaconManager.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_active_beacon_scanner);
        startBeaconSearch();
        activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPaused();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        MyRangeNotifier myRangeNotifier = new MyRangeNotifier();
        beaconManager.setRangeNotifier(myRangeNotifier);
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("ibeaconsRange", null, null, null));
        } catch (RemoteException e) {    }
    }

    public void processBeaconResponse(String jsonResponse) {
        JSONObject jsonObject = null;
        String activityType = null;

        try {
            jsonObject = new JSONObject(jsonResponse);
            activityType = jsonObject.getString("interaction_type");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return;
        }

        BeaconLayoutFacade.startActivity(this, jsonResponse, activityType);
    }

    class MyRangeNotifier implements RangeNotifier{
        final String TAG = "RangingActivity";
        Beacon closestBeacon = null;

        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            if (isActivityVisible()) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    for (Beacon beacon : beacons) {
                        if (closestBeacon != null) {
                            if (beacon.getDistance() < closestBeacon.getDistance()) {
                                closestBeacon = beacon;
                            }
                        } else {
                            closestBeacon = beacon;
                        }
                    }

                    System.out.println("Beacon encontrado. ");
                    System.out.println("UUID: "+closestBeacon.getId1());
                    System.out.println("MAJOR: "+closestBeacon.getId2());
                    System.out.println("MINOR: "+closestBeacon.getId3());

                    new BeaconAsyncTask(ActiveBeaconScanner.this).execute();

                    /*
                    Intent intent = new Intent(ActiveBeaconScanner.this, BeaconDetails.class);
                    intent.putExtra("beacon", closestBeacon);
                    ActiveBeaconScanner.this.beaconManager.unbind(ActiveBeaconScanner.this);
                    ActiveBeaconScanner.this.startActivity(intent);
                    finish();
                    */
                }
            }

        }
    }
}

