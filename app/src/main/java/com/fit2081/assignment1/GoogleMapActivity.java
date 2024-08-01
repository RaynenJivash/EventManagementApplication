package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.assignment1.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    private String toFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toFocus = getIntent().getExtras().getString("searchLocation", null);
//        countryToFocus = "Sydney";
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> locs;
        try {
            locs = geocoder.getFromLocationName(toFocus, 1);
        } catch (IOException e) {
            defaultMove();
//            Toast.makeText(GoogleMapActivity.this.getApplicationContext(), "Invalid country", Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(e);

        }

//         Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


//        Toast.makeText(this, "location: "+countryToFocus, Toast.LENGTH_SHORT).show();
        findCountryMoveCamera(toFocus);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                String msg;

                List<Address> latlongToAddressList = new ArrayList<>();

                try {
                    latlongToAddressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(latlongToAddressList.isEmpty())
                    msg = "Sorry! No country available at this location!";
                else{
                    android.location.Address address = latlongToAddressList.get(0);
                    String country = address.getAddressLine(0);
                    String[] countryList = country.split(",");
                    msg = "The selected country is " + countryList[countryList.length-1];

//                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
                Toast.makeText(GoogleMapActivity.this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findCountryMoveCamera(String location) {

//        Toast.makeText(this, "RUNNNNNSSSSSSSSSS", Toast.LENGTH_SHORT).show();
        //If no location provided
        if (toFocus.equals("")){
            defaultMove();
        }

        // initialise Geocode to search location using String
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());



        // getFromLocationName method works for API 33 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {




            /**
             * countryToFocus: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
            geocoder.getFromLocationName(location, 1, addresses -> {

                // if there are results, this condition would return true
//                if(addresses.isEmpty()){
//                    Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
//                }


                if (!addresses.isEmpty()) {
                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
                        Toast.makeText(this, "API Check"+toFocus, Toast.LENGTH_SHORT).show();
                        // just for reference add a new Marker
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(location)

                        );
//                        Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();

                        // set zoom level to 8.5f or any number between range of 2.0 to 21.0
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                }

                //         Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//                else{
//                    Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
//                }
            });
        }
    }


    private void defaultMove(){
        Toast.makeText(this, "Category address default to Sydney", Toast.LENGTH_SHORT).show();
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
    }
}