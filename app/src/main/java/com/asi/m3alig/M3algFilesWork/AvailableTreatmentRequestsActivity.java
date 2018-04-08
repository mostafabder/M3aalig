package com.asi.m3alig.M3algFilesWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asi.m3alig.Adapters.M3alig.DoctorOrdersRecyclerViewAdapter;
import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.Models.OrderDetails;
import com.asi.m3alig.Models.Orders;
import com.asi.m3alig.Models.Patient;
import com.asi.m3alig.Models.SingleOrder;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.DoctorVisitsOrder;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.PreferenceUtilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class AvailableTreatmentRequestsActivity extends AppCompatActivity {
    private static final int MAP_ZOOM_OUT = 10;
    private static final int MAP_ZOOM_TO = 12;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Marker sourceMarker;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    FancyButton mapButton,addressButton;


    /*ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;*/

    private ArrayList<SingleOrder> mOrders;
    private ArrayList<OrderDetails> mOrderDetails;
    private RecyclerView mDoctorOrdersRV;
    private DoctorOrdersRecyclerViewAdapter mDoctorOrdersAdapter;
    private LinearLayoutManager layoutManager;
    private LinearLayout ll_emptyOrdersText, ll_waiting;

    private ImageView ivBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(AvailableTreatmentRequestsActivity.this, PreferenceUtilities.getLanguage(AvailableTreatmentRequestsActivity.this));
        setContentView(R.layout.activity_available_treatment_requests);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMapDoctor);
        frameLayout = (FrameLayout) findViewById(R.id.frame_doctor);
        linearLayout = (LinearLayout) findViewById(R.id.map_layout_doctor);
        mapButton= (FancyButton) findViewById(R.id.map_button_doctor);
        addressButton= (FancyButton) findViewById(R.id.write_address_button);

        frameLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        setupMap(0, 0);

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrders.size() <= 0){
                    ll_emptyOrdersText.setVisibility(View.VISIBLE);
                }
                frameLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                mapButton.setBackgroundColor(getColor(R.color.blue_unhighlighted));
                addressButton.setBackgroundColor(getColor(R.color.green_highlighted));
            }


        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_emptyOrdersText.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                mapButton.setBackgroundColor(getColor(R.color.green_highlighted));
                addressButton.setBackgroundColor(getColor(R.color.blue_unhighlighted));
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
// get the listview
        //expListView = (ExpandableListView) findViewById(R.id.lvExp);

        mOrders = new ArrayList<>();
        mOrderDetails = new ArrayList<>();
        mDoctorOrdersRV = (RecyclerView) findViewById(R.id.rv_doctorOrders);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDoctorOrdersRV.setLayoutManager(layoutManager);

        ll_emptyOrdersText = (LinearLayout) findViewById(R.id.ll_emptyOrdersText);
        ll_waiting = (LinearLayout) findViewById(R.id.ll_waiting);

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
        }

        // preparing list data
        prepareListData();

        /*
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        */

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DoctorVisitsOrder> call = apiService.doctorAllOrders(getSecret(getApplicationContext()),getToken(getApplicationContext()));
        call.enqueue(new Callback<DoctorVisitsOrder>() {
            @Override
            public void onResponse(Call<DoctorVisitsOrder> call, Response<DoctorVisitsOrder> response) {

                ll_waiting.setVisibility(View.GONE);

                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        //set up arrayList
                        ArrayList<Orders> orders = (ArrayList<Orders>) response.body().getData();
                        for(int i=0; i<orders.size(); i++) {
                            String orderId = getString(R.string.oder_number) +" "+ orders.get(i).getId();
                            String orderDate = orders.get(i).getDate();
                            String orderTime = orders.get(i).getTime();
                            String whenPainStart = getString(R.string.pain_start_sence)  + " "+orders.get(i).getWhenPainStart();
                            String painPlace = getString(R.string.pain_place) +" "+ orders.get(i).getPain_position();
                            String city = getString(R.string.city) +" "+ orders.get(i).getLocation_city();
                            String street = getString(R.string.street)+" "+ orders.get(i).getLocation_street_name();
                            String address = getString(R.string.the_address)+" "+ orders.get(i).getLocation_region();
                            Patient patient = orders.get(i).getPatient();
                            OrderDetails orderDetails = new OrderDetails(orderId, whenPainStart, street, city, painPlace, address, patient);
                            SingleOrder singleOrder = new SingleOrder(orderId, orderDate, orderTime, orderDetails);
                            mOrders.add(singleOrder);
                            mOrderDetails.add(orderDetails);
                        }
                        //set up adapter
                        mDoctorOrdersAdapter = new DoctorOrdersRecyclerViewAdapter(
                                AvailableTreatmentRequestsActivity.this, mOrders, mOrderDetails);
                        mDoctorOrdersRV.setAdapter(mDoctorOrdersAdapter);
                        Log.i("size", mOrders.size()+"");
                        if(mOrders.size() > 0){
                            ll_emptyOrdersText.setVisibility(View.GONE);
                        }else {
                            ll_emptyOrdersText.setVisibility(View.VISIBLE);
                        }
                    }else {
                        //here if code not successful
                        try{
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //here if body null
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DoctorVisitsOrder> call, Throwable t) {
                ll_waiting.setVisibility(View.GONE);
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });

        /*
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("طلب رقم 23837");
        listDataHeader.add("طلب رقم 1456");
        listDataHeader.add("طلب رقم 2345");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("بعيد عنك ب 1 كيلومتر");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("بعيد عنك ب 1 كيلومتر");


        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("بعيد عنك ب 1 كيلومتر");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        */
    }

    public void nextPage(View view){}

    public void goBack(View view) {
        onBackPressed();
    }

    private void setupMap(final double latitude, final double longitude) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                if (latitude > 0 && longitude > 0) {
                    Log.d("latitudeMap", latitude + "");
                    Log.d("longitudeMap", longitude + "");
                    MarkerOptions optionFirstLocation = new MarkerOptions();
                    optionFirstLocation.position(new LatLng(latitude, longitude));
                    optionFirstLocation.icon(BitmapDescriptorFactory.defaultMarker());
                    sourceMarker = googleMap.addMarker(optionFirstLocation);
                    initCamera(latitude, longitude);
                    mMap.getUiSettings().setScrollGesturesEnabled(false);

                }
            }
        });
    }

    private void initCamera(double lat, double lng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
                lng), MAP_ZOOM_OUT));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(MAP_ZOOM_TO));
    }
}
