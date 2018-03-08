package com.asi.m3alig.PatientWork;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.PatientWork.m3aligSideScreens.ContactDoctorActivty;
import com.asi.m3alig.R;
import com.asi.m3alig.Utility.MyLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChoosePlaceActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Spinner spinner;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    double lat;
    double lng;
    private static final int MAP_ZOOM_OUT = 10;
    private static final int MAP_ZOOM_TO = 12;
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private Marker sourceMarker;
    EditText floor,street,city;
    CardView cardView;
    LinearLayout linearLayout;
    ImageView mapButton,addressButton;
    VisitOrderPatient order;
    final static int locationRequestCode=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_place);
        spinner=(Spinner)findViewById(R.id.region_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(ChoosePlaceActivity.this, R.array.places, R.layout.item_spinner_location);
        spinner.setAdapter(arrayAdapter);
        order=(VisitOrderPatient)getIntent().getSerializableExtra("order");
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        cardView = (CardView) findViewById(R.id.card_view_address);
        floor=(EditText)findViewById(R.id.floor_number);
        street=(EditText)findViewById(R.id.street_name);
//        city=(EditText)findViewById(R.id.city);
        linearLayout = (LinearLayout) findViewById(R.id.map_layout);
        mapButton= (ImageView) findViewById(R.id.map_button);
        addressButton= (ImageView) findViewById(R.id.address_button);
        mapButton.setImageResource(R.drawable.map_not_clicked_icon);
        addressButton.setImageResource(R.drawable.write_address_clicked_icon);
        cardView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        setupMap(0, 0);

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cardView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                mapButton.setImageResource(R.drawable.map_not_clicked_icon);
                addressButton.setImageResource(R.drawable.write_address_clicked_icon);
            }


        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.GONE);
                mapButton.setImageResource(R.drawable.map_clicked_icon);
                addressButton.setImageResource(R.drawable.write_address_not_clicked_icon);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }



    public void nextPage(View view) throws IOException {
        if(!(mLastLocation==null||mLastLocation.getLatitude()==0))
        {
            if(validate().equals("ok"))
            {
                getAddress();
                order.setLocation_floor_number(floor.getText().toString());
                order.setLocation_street_name(street.getText().toString());
                order.setLocation_region(spinner.getSelectedItem().toString());
//                order.setLocation_city(city.getText().toString());
                Intent intent=new Intent(ChoosePlaceActivity.this, ScheduleActivity.class);
                intent.putExtra("order",order);
                Log.e("flr,st,reg,city,lat,lng",order.getLocation_floor_number()+","+order.getLocation_street_name()+","+order.getLocation_region()+","+order.getLocation_city()+","+order.getLocation_city()
                        +","+order.getLat()+","+order.getLng());
                startActivity(intent);
            }
            else Toast.makeText(ChoosePlaceActivity.this,validate(),Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(ChoosePlaceActivity.this,"(تعذر الحصول على الموقع. تأكد من تمكين الموقع على الجهاز)",Toast.LENGTH_SHORT).show();

    }

    public void goBack(View view) {
        onBackPressed();
    }


    private void setupMap(final double latitude, final double longitude) {
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }
        try {
            MapsInitializer.initialize(ChoosePlaceActivity.this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                ChoosePlaceActivity.this.googleMap = googleMap;
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                if (latitude > 0 && longitude > 0) {
                    Log.d("latitudeMap", latitude + "");
                    Log.d("longitudeMap", longitude + "");
                    MarkerOptions optionFirstLocation = new MarkerOptions();
                    optionFirstLocation.position(new LatLng(latitude, longitude));
                    optionFirstLocation.icon(BitmapDescriptorFactory.defaultMarker());
                    sourceMarker = googleMap.addMarker(optionFirstLocation);
                    initCamera(latitude, longitude);
                    googleMap.getUiSettings().setScrollGesturesEnabled(false);
                }
            }
        });
    }

    private void initCamera(double lat, double lng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
                lng), MAP_ZOOM_OUT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(MAP_ZOOM_TO));
    }
    public String  validate(){
         if(street.getText().toString().trim().equals(""))
            return getString(R.string.enter_steer);
        else if(mLastLocation==null)
            return "تاكد من تمكين الموقع علي جهازك";
//        else if(city.getText().toString().trim().equals(""))
//            return getString(R.string.enter_city);
        return "ok";
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(ChoosePlaceActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

        MyLocation.LocationResult locationResult=new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                Log.e("lat,lng",location.getLatitude()+" "+location.getLongitude());
                mLastLocation=location;
            }
        };
        MyLocation myLocation = new MyLocation(ChoosePlaceActivity.this);
        myLocation.getLocation(this, locationResult);
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(ChoosePlaceActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, ChoosePlaceActivity.this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(ChoosePlaceActivity.this,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                ChoosePlaceActivity.this.finish();
            }
            return false;
        }
        return true;
    }
    protected void startLocationUpdates() {


        if (ActivityCompat.checkSelfPermission(ChoosePlaceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChoosePlaceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }
    private void displayLocation() {


        if (ActivityCompat.checkSelfPermission(ChoosePlaceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ChoosePlaceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null)
        {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            lat = latitude;
            lng = longitude;
            // For dropping a marker at a point on the Map
            LatLng myMarker = new LatLng(lat, lng);

            googleMap.addMarker(new MarkerOptions().position(myMarker).snippet("الموقع الحالي"));
            lat = latitude;
            lng = longitude;


            //For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(myMarker).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        else
        {

            Toast.makeText(ChoosePlaceActivity.this, "(تعذر الحصول على الموقع. تأكد من تمكين الموقع على الجهاز)", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null)
        {
            mLastLocation=location;
            initCamera(location.getLatitude(),location.getLongitude());
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
        order.setLocation_city(addresses.get(0).getLocality());
        order.setLat(String.valueOf(mLastLocation.getLatitude()));
        order.setLng(String.valueOf(mLastLocation.getLongitude()));
    }
}
