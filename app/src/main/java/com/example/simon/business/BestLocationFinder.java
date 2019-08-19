package com.example.simon.business;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.simon.smokesignals.R;

import java.util.List;

public class BestLocationFinder {

    LocationManager locationManager;
    Context context;

    public BestLocationFinder(Context _context)
    {
        context = _context;
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context, R.string.missing_permission, Toast.LENGTH_LONG).show();
            return null;
        }
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
}
