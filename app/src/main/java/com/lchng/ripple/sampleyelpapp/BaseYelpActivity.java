package com.lchng.ripple.sampleyelpapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseYelpActivity extends AppCompatActivity {
    private Location lastKnownLocation = null;
    
    private BroadcastReceiver networkBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkAvailable()) {
                onNetworkConnected();
            } else {
                onNetworkDisconnected();
            }
        }
    };

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d(getClass().getName(), String.format("onLocationChanged"));
            setLastKnownLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(getClass().getName(), String.format("onStatusChanged %s", s));
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d(getClass().getName(), String.format("onProviderEnabled %s", s));
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d(getClass().getName(), String.format("onProviderDisabled %s", s));
            setLastKnownLocation(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(getClass().getName(), "OnCreate fired");

        if (Build.VERSION.SDK_INT >= 23) {
            //Check for permissions
            requestForApproval();
        }
        fetchLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(getClass().getName(), "onResume fired");
        registerCheckNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getName(), "onPause fired");
        unregisterCheckNetworkReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(getClass().getName(), "onDestroy fired");
    }


    /**
     * Check for Network Connection
     */
    protected abstract void onNetworkConnected();

    protected abstract void onNetworkDisconnected();

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void registerCheckNetworkReceiver() {
        IntentFilter networkStatusFilter = new IntentFilter();
        networkStatusFilter.addAction("android.net.wifi.STATE_CHANGE");
        networkStatusFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkBroadcastReceiver, networkStatusFilter);
    }

    private void unregisterCheckNetworkReceiver() {
        unregisterReceiver(networkBroadcastReceiver);
    }

    /**
     * Check for Permissions
     * Note don't need to check INTERNET  or ACCESS_NETWORK_STATE for permission. Only core ones like FINE_LOCATION, COARSE_LOCATION
     */
    private void requestForApproval() {
        final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

        List<String> manifestPermissions = Arrays.asList(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        );

        List<String> permissionsRequiredForApproval = new ArrayList<>();

        for (String permission : manifestPermissions) {
            int p = ContextCompat.checkSelfPermission(this, permission);
            if (p != PackageManager.PERMISSION_GRANTED) {
                permissionsRequiredForApproval.add(permission);
            }
        }

        if (!permissionsRequiredForApproval.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsRequiredForApproval.toArray(new String[permissionsRequiredForApproval.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fetchLocation();
    }

    private void fetchLocation() {
        // Check permission again, if both are not granted, skip location fetching
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalConfig.LOCATION_REFRESH_TIME,
                GlobalConfig.LOCATION_REFRESH_DISTANCE, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, GlobalConfig.LOCATION_REFRESH_TIME,
                GlobalConfig.LOCATION_REFRESH_DISTANCE, locationListener);
    }

    private void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }
}
