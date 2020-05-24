package com.tree.treeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class TreeAddActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private final String TAG = TreeAddActivity.class.getSimpleName();

    private EditText treeName;
    private EditText treeNickname;

    private RadioGroup watreCycle;

    private TextView treeAddressView;

    private Button write;

    private GoogleMap googleMap;

    private int cycleTime = 1;

    private double[] treeAddress;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation;

    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_add);

        treeAddress = new double[]{-34, 151};
        write = findViewById(R.id.tree_write);
        watreCycle = findViewById(R.id.water_cycles);
        treeName = findViewById(R.id.tree_name);
        treeNickname = findViewById(R.id.tree_nickname);
        treeAddressView = findViewById(R.id.tree_address);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tree_map);
        mapFragment.getMapAsync(this);

        watreCycle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.water1: {
                        cycleTime = 1;
                        break;
                    }
                    case R.id.water6: {
                        cycleTime = 6;
                        break;
                    }
                    case R.id.water12: {
                        cycleTime = 12;
                        break;
                    }
                    case R.id.water24: {
                        cycleTime = 24;
                        break;
                    }
                }
            }
        });

        write.setOnClickListener(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(treeAddress[0], treeAddress[1]);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        updateLocationUI();
        getDeviceLocation();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                markerOptions = new MarkerOptions().position(latLng);
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                treeAddress[0] = latLng.latitude;
                treeAddress[1] = latLng.longitude;
                setLatLongView();

            }
        });
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setLatLongView() {
        treeAddressView.setText("위도 : " + treeAddress[0] + " 경도 : " + treeAddress[1]);
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), 15));

                        treeAddress[0] = mLastKnownLocation.getLatitude();
                        treeAddress[1] = mLastKnownLocation.getLongitude();
                        setLatLongView();
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        Intent treeIntent = new Intent(this, MainActivity.class);
        String name = treeName.getText().toString();
        String nickname = treeNickname.getText().toString();
        treeIntent.putExtra(TreeItem.TREE_NAME, name);
        treeIntent.putExtra(TreeItem.TREE_NICKNAME, nickname);
        treeIntent.putExtra(TreeItem.TREE_CYCLE, cycleTime);
        treeIntent.putExtra(TreeItem.TREE_ADDRESS, treeAddress);
        startActivity(treeIntent);
        finish();
//        Intent treeIntent = new Intent(this, MainActivity.class);

//        treeIntent.putExtra(TreeItem.TREE_NAME, name);
//        treeIntent.putExtra(TreeItem.TREE_NICKNAME, nickname);
//        treeIntent.putExtra(TreeItem.TREE_CYCLE, cycleTime);
//        treeIntent.putExtra(TreeItem.TREE_ADDRESS, treeAddress);
//
//        Log.d(TAG, "onClick1: " + treeName.getText().toString());
//        Log.d(TAG, "onClick2: " + treeNickname.getText().toString());
//        Log.d(TAG, "onClick3: " + cycleTime);
//        Log.d(TAG, "onClick4: " + treeAddress[0]);
//        Log.d(TAG, "onClick4: " + treeAddress[1]);
//
//
//
//        googleMap.stopAnimation();
//
//        Log.d(TAG, "onClick: " + treeIntent.getStringExtra(TreeItem.TREE_NAME));
//
//        WaterCycleManager.registerAlarm(this, cycleTime, name, nickname);
//
//        startActivity(treeIntent);
//        finish();
    }
}
