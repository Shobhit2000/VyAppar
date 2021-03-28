package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.gov.Adapter.Adapter_Search;
import com.example.gov.Interface.OnItemClickListner;
import com.example.gov.ModalClasses.Class_Search_Categories;
import com.example.gov.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , OnItemClickListner {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    Adapter_Search adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView l1;
    SearchView sw;
    FirebaseUser fbuser;
    FirebaseAuth auth;
    Map<String, Object> document = new HashMap<>();
    List<String> vendorId=new ArrayList<>();
    List<Class_Search_Categories> search_categories = new ArrayList<>();





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        auth = FirebaseAuth.getInstance();
        fbuser = auth.getCurrentUser();

        l1 = findViewById(R.id.ll);
        l1.setHasFixedSize(true);
        adapter = new Adapter_Search(getApplicationContext(), search_categories);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        adapter.setClickListener(this);

        l1.setAdapter(adapter);
        l1.setLayoutManager(linearLayoutManager);
        getData();




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



        // Add a marker in Sydney and move the camera
       mMap.clear();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng myLocation=new LatLng(location.getLatitude(),location.getLongitude());

                mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,10.0f));



            }
        };

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Location").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                int i = 0;
                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : snapshots) {
                    document = documentSnapshot.getData();
                    LatLng myLocation=new LatLng( Float.parseFloat(String.valueOf(document.get("Lattitude"))), Float.parseFloat(String.valueOf(document.get("Longitude"))));

                    mMap.addMarker(new MarkerOptions().position(myLocation).title("new Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,10.0f));






                }}});

        if (Build.VERSION.SDK_INT < 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                //Location lastknownlocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //mMap.clear();
                //LatLng sydney = new LatLng(lastknownlocation.getLatitude(), lastknownlocation.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }
        }
    }


    public void getData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Vendor").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                int i = 0;
                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : snapshots) {
                    document = documentSnapshot.getData();

                    if(document.get("companyName").toString()!=null&&document.get("description").toString()!=null&&document.get("ImageUrl").toString()!=null&&document.get("Category").toString()!=null&&document.get("address").toString()!=null)
                    {

                        i++;
                        Class_Search_Categories class_search_categories = new Class_Search_Categories(document.get("companyName").toString(), document.get("description").toString(), "Alandar", "4/5", document.get("ImageUrl").toString(), document.get("Category").toString(), document.get("address").toString());
                        search_categories.add(class_search_categories);

                        vendorId.add(document.get("userId").toString());
                        adapter.notifyDataSetChanged();
                    }

//                    for (Map.Entry<String, Object> entry : document.entrySet()) {
//                        Map<String, Objects> map = (Map<String, Objects>) entry.getValue();
//
//                        Log.e("Fragment_Serach2345", entry.getKey().toString()+"="+entry.getValue().toString());
//
//
//
//
////                        Class_Search_Categories class_search_categories = new Class_Search_Categories(map.get("companyName").toString(), map.get("description").toString(), "Alandar", "4/5", map.get("ImageUrl").toString(),map.get("Category").toString(),map.get("address").toString());
////
////                        adapter.notifyDataSetChanged();
//
//                    }
                }


            }
        });

    }


    @Override
    public void onClick(View view, int position) {

        Intent intent=new Intent(getApplicationContext(), VendorProfileCustomerSide.class);
        intent.putExtra("userId",vendorId.get(position));
        startActivity(intent);

    }








}