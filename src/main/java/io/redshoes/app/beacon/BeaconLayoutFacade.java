package io.redshoes.app.beacon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import io.redshoes.app.beacon.layouts.BeaconImageAndSingleTextActivity;
import io.redshoes.app.beacon.layouts.SlidePresentation;

/**
 * Created by vinicius on 07/10/15.
 */
public class BeaconLayoutFacade {

    public static void startActivity(ActiveBeaconScanner originActivity, String jsonResponse, String type) {
        Class activityClass = getActivityByType(type);

        Intent intent = new Intent(originActivity, activityClass);
        intent.putExtra("jsonResponse", jsonResponse);
        originActivity.startActivity(intent);
        originActivity.finish();
    }

    private static Class getActivityByType(String type) {
        Class activityClass = null;
        switch(type) {
            case "INT001":
                activityClass = BeaconImageAndSingleTextActivity.class;
                break;
            case "INT002":
                activityClass = SlidePresentation.class;
                break;
            default:
                activityClass = BeaconImageAndSingleTextActivity.class;
                break;
        }

        return activityClass;
    }
}
