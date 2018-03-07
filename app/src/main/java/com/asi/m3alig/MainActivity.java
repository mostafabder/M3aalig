package com.asi.m3alig;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.M3algFilesWork.AccountSettingsActivity;
import com.asi.m3alig.M3algFilesWork.AvailableTreatmentRequestsActivity;
import com.asi.m3alig.M3algFilesWork.DoctorTreatmentSessionsScheduleActivity;
import com.asi.m3alig.M3algFilesWork.HelpCenterActivity;
import com.asi.m3alig.M3algFilesWork.OfferHelpActivity;
import com.asi.m3alig.M3algFilesWork.PrivacyActivity;

import com.asi.m3alig.M3algFilesWork.RateTreatmentDoctorSessionActivity;
import com.asi.m3alig.Models.DoctorCurrentVisit;
import com.asi.m3alig.Models.DoctorSingleVisit;

import com.asi.m3alig.Models.Visit;

import com.asi.m3alig.PatientWork.OrderM3algNowActivity;
import com.asi.m3alig.NavDrawWork.DrawerListAdapter;
import com.asi.m3alig.NavDrawWork.NavItem;
import com.asi.m3alig.PatientWork.RateTreatmentSessionActivity;
import com.asi.m3alig.PatientWork.m3aligSideScreens.ContactDoctorActivty;
import com.asi.m3alig.PatientWork.m3aligSideScreens.MesseageCenterActivity;
import com.asi.m3alig.PatientWork.m3aligSideScreens.MyHealthSummaryActivity;
import com.asi.m3alig.PatientWork.m3aligSideScreens.TreatmentGuidelinesActivity;
import com.asi.m3alig.PatientWork.m3aligSideScreens.TreatmentSessionsScheduleActivity;

import java.util.ArrayList;


import com.asi.m3alig.Responses.StartingVisit;

import com.asi.m3alig.Responses.CurrentResponse;
import com.asi.m3alig.Responses.FinishVisitResponse;
import com.asi.m3alig.Responses.NormalResponse;

import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;
import com.asi.m3alig.Utility.inviteFriendViewDialog;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;
import static com.asi.m3alig.Utility.Constants.getType;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS_205;
import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS_210;
import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS_215;


public class MainActivity extends AppCompatActivity {

    ListView mDrawerList;
    LinearLayout mDrawerPane,notificationLayout,mainPatientLayout, youtubeLinkLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private FancyButton bt_availableOrders;
    private TextView tv_currentState;
    private String id, pateint_id;


    ImageView ivOpenMenu, messageIcon,notificationIcon,orderM3alig_iv;
    LinearLayout options,current_state;
    int notFlag  ;
    TextView tretmentSessionTableButton,current_state_msg,current_state_event;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    int state=0;
    Visit currentVisit;
    final static int locationRequestCode=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.e("TYPE",getType(getApplicationContext()));
        if (getType(MainActivity.this).equals(Constants.M3ALG_TYPE)) {
            setContentView(R.layout.activity_mian_maleg);
            mNavItems.add(new NavItem("الصفحة الرئيسية", R.drawable.homeicon));
            mNavItems.add(new NavItem("جدول جلسات العلاج", R.drawable.healthy_summery_icon));
            mNavItems.add(new NavItem("مركز الرسائل", R.drawable.message_icon));
            mNavItems.add(new NavItem("تقديم استشارات طبية", R.drawable.offer_help_icon));
            //mNavItems.add(new NavItem("إعدادات الحساب", R.drawable.account_settings_icon));
            mNavItems.add(new NavItem("دعوة الأصدقاء", R.drawable.invite_friend_icon));
            mNavItems.add(new NavItem("سياسه الخصوصيه \n وشروط الاستخدام", R.drawable.privacy_icon));
            mNavItems.add(new NavItem("مركز المساعده والدعم", R.drawable.help_center_icon));
            mNavItems.add(new NavItem("تعرف علينا", R.drawable.discover_us_icon));
            mNavItems.add(new NavItem("تسجيل الخروج", R.drawable.ic_logout));

            bt_availableOrders = (FancyButton) findViewById(R.id.bt_availableOrders);
            bt_availableOrders.setVisibility(View.GONE);
            tv_currentState = (TextView) findViewById(R.id.tv_currentState);
            getCurrentState();

        } else {
            setContentView(R.layout.activity_main);
            current_state_msg=(TextView)findViewById(R.id.current_state_text);
            current_state_event=(TextView)findViewById(R.id.current_state_event);
            options=(LinearLayout)findViewById(R.id.layout_hidden_main);
            current_state=(LinearLayout)findViewById(R.id.current_state_layout);
            orderM3alig_iv=(ImageView)findViewById(R.id.iv_order_m3alig);
            current_state_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(state==1)
                    {
                        cancelOrder(currentVisit.getId());
                    }
                    else if(state==2)
                    {
                        cancelVisit(currentVisit.getId());
                    }
                    else if(state==3)
                    {
                        finishRateVisit(currentVisit.getId());
                    }
                }
            });
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            locationRequestCode);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            currentStatePatient();
            mNavItems.add(new NavItem("الصفحة الرئيسية", R.drawable.homeicon));
            mNavItems.add(new NavItem("ارشادات العلاج والتمارين", R.drawable.advice_icon));
            mNavItems.add(new NavItem("ملخص حالتي الصحية", R.drawable.healthy_summery_icon));


            mNavItems.add(new NavItem("مركز الرسائل", R.drawable.message_icon));
            mNavItems.add(new NavItem("جدول جلسات العلاج", R.drawable.table_icon));
            mNavItems.add(new NavItem("التواصل مع اخصائي", R.drawable.contact_icon));


            mNavItems.add(new NavItem("العروض والخصومات", R.drawable.offers));
            mNavItems.add(new NavItem("إعدادات الحساب", R.drawable.account_settings_icon));
            mNavItems.add(new NavItem("سياسه الخصوصيه \n وشروط الاستخدام", R.drawable.privacy_icon));


            mNavItems.add(new NavItem("مركز المساعده والدعم", R.drawable.help_center_icon));
            mNavItems.add(new NavItem("تعرف علينا", R.drawable.discover_us_icon));
            mNavItems.add(new NavItem("تسجيل الخروج", R.drawable.ic_logout));
        }

        if (!getType(MainActivity.this).equals(Constants.M3ALG_TYPE)) {
            messageIcon = (ImageView)findViewById(R.id.messeage_icon_top);
            tretmentSessionTableButton = (TextView) findViewById(R.id.tretment_session_table_button);
            notificationLayout = (LinearLayout)findViewById(R.id.notification_linear);
            mainPatientLayout = (LinearLayout)findViewById(R.id.main_patioent_layout);
            youtubeLinkLayout = (LinearLayout)findViewById(R.id.youtube_link);
            notificationIcon = (ImageView)findViewById(R.id.notification_image);
            notFlag = 0;
            notificationLayout.setVisibility(View.GONE);


            youtubeLinkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchYoutubeVideo(MainActivity.this,"P_Rl0pFssL0");
                }
            });

            mainPatientLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notFlag == 1){
                        notificationLayout.setVisibility(View.GONE);
                        notFlag = 0;
                        mainPatientLayout.setBackgroundColor(getResources().getColor(R.color.transparent));

                    }
                }
            });
            notificationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if(notFlag == 0){
                     notificationLayout.setVisibility(View.VISIBLE);
                     notFlag = 1;
                     mainPatientLayout.setBackgroundColor(getResources().getColor(R.color.tiny_appcolor));
                 }else {
                     notificationLayout.setVisibility(View.GONE);
                     notFlag = 0;
                     mainPatientLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                 }

                }
            });

            messageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, MesseageCenterActivity.class));

                }
            });
            tretmentSessionTableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, TreatmentSessionsScheduleActivity.class));

                }
            });

        }
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (LinearLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // open menu with hamburger icon
        ivOpenMenu = (ImageView) findViewById(R.id.ivOpenMenu);
        ivOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Device_Token",token);
    }



    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
    private void selectItemFromDrawer(int position) {
//        Fragment fragment = new PreferencesFragment();
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.mainContent, fragment)
//                .commit();

        if (getType(MainActivity.this).equals(Constants.M3ALG_TYPE)) {
            // position 2 تقديم استشارة طبية
            if (position == 0) {
                finish();
                startActivity(getIntent());
            } else if (position == 1) {
                startActivity(new Intent(MainActivity.this, DoctorTreatmentSessionsScheduleActivity.class));
            } else if (position == 2) {
                startActivity(new Intent(MainActivity.this, MesseageCenterActivity.class));
            } else if (position == 3) {
                startActivity(new Intent(MainActivity.this, OfferHelpActivity.class));
            } /*else if (position == 3) {
                startActivity(new Intent(MainActivity.this, AccountSettingsActivity.class));
            } */else if (position == 4) {
                inviteFriendViewDialog alert = new inviteFriendViewDialog();
                alert.showDialog(MainActivity.this, "OTP has been sent to your Mail ");
            } else if (position == 5) {
                startActivity(new Intent(MainActivity.this, PrivacyActivity.class));
            } else if (position == 6) {
                startActivity(new Intent(MainActivity.this, HelpCenterActivity.class));
            } else if (position == 7) {
            } else if (position == 8) {
                SessionManager session = new SessionManager(MainActivity.this);
                session.setLogin(false);
                Intent intent = new Intent(MainActivity.this, BeforLoginActivity.class);
                new SQLiteHandler(getApplicationContext()).deleteUsers();
                startActivity(intent);
                finish();
            }
        } else {
            // position 2 تقديم استشارة طبية
            if (position == 0) {
                finish();
                startActivity(getIntent());
            } else if (position == 1) {
                startActivity(new Intent(MainActivity.this, TreatmentGuidelinesActivity.class));
            } else if (position == 2) {
                startActivity(new Intent(MainActivity.this, MyHealthSummaryActivity.class));
            } else if (position == 3) {
                startActivity(new Intent(MainActivity.this, MesseageCenterActivity.class));
            } else if (position == 4) {
                startActivity(new Intent(MainActivity.this, TreatmentSessionsScheduleActivity.class));

            } else if (position == 5) {
                startActivity(new Intent(MainActivity.this, ContactDoctorActivty.class));

            } else if (position == 6) {

            }
            else if (position == 7) {
                startActivity(new Intent(MainActivity.this, AccountSettingsActivity.class));
            }
            else if (position == 8) {
                startActivity(new Intent(MainActivity.this, PrivacyActivity.class));
            } else if (position == 9) {
                startActivity(new Intent(MainActivity.this, HelpCenterActivity.class));

            } else if (position == 10) {
                startActivity(new Intent(MainActivity.this, HelpCenterActivity.class));

            } else if (position == 11) {
                new SessionManager(MainActivity.this).setLogin(false);
                new SQLiteHandler(getApplicationContext()).deleteUsers();
                Intent intent=new Intent(MainActivity.this,BeforLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).getmTitle());

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

//
//    // Called when invalidateOptionsMenu() is invoked
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

    private void getCurrentState(){

        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.e("TOKEN,SECRET",getToken(this)+"  "+getSecret(this));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DoctorCurrentVisit> call = apiService.doctorCurrentState(getSecret(getApplicationContext()), getToken(getApplicationContext()));
        call.enqueue(new Callback<DoctorCurrentVisit>() {
            @Override
            public void onResponse(Call<DoctorCurrentVisit> call, Response<DoctorCurrentVisit> response) {
                Log.i("schedule", "enter2");
                progressDialog.dismiss();
                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("schedule", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("schedule", "200");
                        DoctorSingleVisit visit = response.body().getData();
                        String youHaveVisit = response.body().getMessage();
                        tv_currentState.setText(youHaveVisit);
                        bt_availableOrders.setText("ابدأ الجلسة");
                        bt_availableOrders.setVisibility(View.VISIBLE);
                        id = visit.getId();
                        bt_availableOrders.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startVisit();
                                bt_availableOrders.setClickable(false);
                                bt_availableOrders.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //do nothing
                                    }
                                });
                            }
                        });
                    }else if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS_205)){
                        Log.i("schedule", "205");
                        String nothing = response.body().getMessage();
                        tv_currentState.setText(nothing);
                        bt_availableOrders.setVisibility(View.VISIBLE);
                        bt_availableOrders.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                availableOrders();
                            }
                        });
                    }else if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS_210)){
                        Log.i("schedule", "210");
                        String visitIsStarted = response.body().getMessage();
                        tv_currentState.setText(visitIsStarted+"\n\n انتظر حتي انتهاء الجلسة");
                    }else if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS_215)){
                        Log.i("schedule", "215");
                        String visitEnd = "الجلسة انتهت \n يرجى تقييم المريض و اعطاء التقرير اللازم";
                        DoctorSingleVisit visit = response.body().getData();
                        id = visit.getId();
                        pateint_id = visit.getPatient().getId();
                        bt_availableOrders.setVisibility(View.VISIBLE);
                        bt_availableOrders.setText("تقييم المريض");
                        bt_availableOrders.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, RateTreatmentDoctorSessionActivity.class);
                                intent.putExtra("visit_id", id);
                                intent.putExtra("patient_id", pateint_id);
                                startActivity(intent);
                            }
                        });
                        tv_currentState.setText(visitEnd);
                    }
                    else{
                        Log.i("schedule", "403");
                        //here if code not successful
                        try{
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.i("schedule", "is null");
                    //here if body null
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DoctorCurrentVisit> call, Throwable t) {
                progressDialog.dismiss();
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                Log.i("schedule", "out");
            }
        });
    }


    private void startVisit(){

        Log.i("sv", "enter");

        //show waiting progress
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<StartingVisit> call = apiService.doctorStartVisit(getSecret(getApplicationContext()), getToken(getApplicationContext()), id);
        call.enqueue(new Callback<StartingVisit>() {
            @Override
            public void onResponse(Call<StartingVisit> call, Response<StartingVisit> response) {
                //here if task successful
                //dismiss the waiting progress
                progressDialog.dismiss();
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("sv", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("sv", "200");
                    }else {
                        Log.i("sv", "403");
                        //here if code not successful
                        try{
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), getString(R.string.something_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.i("sv", "is null");
                    //here if body null
                    Toast.makeText(getApplicationContext(), getString(R.string.contact_services), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StartingVisit> call, Throwable t) {
                //dismiss the waiting progress
                progressDialog.dismiss();
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                Log.i("sv", "out");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void inviteFriend(View view) {
        if (!getType(MainActivity.this).equals(Constants.M3ALG_TYPE)) {
            inviteFriendViewDialog alert = new inviteFriendViewDialog();
            alert.showDialog(MainActivity.this, "OTP has been sent to your Mail ");
        }
    }

    public void orderM3alg(View view) {
        startActivity(new Intent(MainActivity.this, OrderM3algNowActivity.class));
    }

    public void availableOrders() {
        startActivity(new Intent(MainActivity.this, AvailableTreatmentRequestsActivity.class));
    }

    public void currentStatePatient(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(this.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(this)+"  "+getSecret(this));

        Call<CurrentResponse> call=apiService.currentState(getToken(this),getSecret(this));
        call.enqueue(new Callback<CurrentResponse>() {
            @Override
            public void onResponse(Call<CurrentResponse> call, Response<CurrentResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG1",response.body().getCode()+"   "+response.body().getMessage());
                    String code=response.body().getCode();
                    if(code.equals(FLAGE_CODE_SUCCSESS)||code.equals("205")||code.equals("210")||code.equals("202")) //all means success in this service
                    {
                        if(code.equals(FLAGE_CODE_SUCCSESS)) // order accepted , patient is waiting doctor
                        {
                            orderM3alig_iv.setVisibility(View.GONE);
                            current_state.setVisibility(View.VISIBLE);
                            options.setVisibility(View.VISIBLE);
                            current_state_msg.setText(response.body().getMessage());
                            current_state_event.setText("اضفط لالغاء الجلسة");
                            state=2;
                            currentVisit=response.body().getData();
                        }
                        else if(code.equals("205")) //no current state, u can create a new order for visit
                        {
                            orderM3alig_iv.setVisibility(View.VISIBLE);
                            current_state.setVisibility(View.GONE);
                            options.setVisibility(View.VISIBLE);
                            state=0;
                        }
                        else if(code.equals("210")) //doctor has been came,patient should finish then rate this visit
                        {
                            orderM3alig_iv.setVisibility(View.GONE);
                            current_state.setVisibility(View.VISIBLE);
                            options.setVisibility(View.GONE);
                            current_state_msg.setText(response.body().getMessage());
                            current_state_event.setText("اضفط للانهاء و التقييم");
                            state=3;
                            currentVisit=response.body().getData();
                        }
                        else if(code.equals("202"))
                        {
                            orderM3alig_iv.setVisibility(View.GONE);
                            current_state.setVisibility(View.VISIBLE);
                            options.setVisibility(View.VISIBLE);
                            current_state_msg.setText(response.body().getMessage());
                            current_state_event.setText("اضفط لالغاء الطلب");
                            state=1;
                            currentVisit=response.body().getData();
                        }
                    }
                    else  {
                        try{
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(MainActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<CurrentResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    private void cancelVisit(String id) {
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(MainActivity.this)+"  "+getSecret(MainActivity.this));
        Log.e("id",currentVisit.getId()+"   ");

        Call<NormalResponse> call=apiService.cancelVisitPatient(getToken(MainActivity.this),getSecret(MainActivity.this), id);
        call.enqueue(new Callback<NormalResponse>() {
            @Override
            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        finish();
                        startActivity(getIntent());
                    }
                    else  {
                        try{
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(MainActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    private void cancelOrder(String visit_id) {
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(MainActivity.this)+"  "+getSecret(MainActivity.this));
        Log.e("id",currentVisit.getId()+"   ");

        Call<NormalResponse> call=apiService.cancelOrderPatient(getToken(MainActivity.this),getSecret(MainActivity.this), visit_id);
        call.enqueue(new Callback<NormalResponse>() {
            @Override
            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        finish();
                        startActivity(getIntent());
                    }
                    else  {
                        try{
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(MainActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
    private void finishRateVisit(String visit_id){
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(MainActivity.this)+"  "+getSecret(MainActivity.this));
        Log.e("id",currentVisit.getId()+"   ");

        Call<FinishVisitResponse> call=apiService.finishVisitPatient(getToken(MainActivity.this),getSecret(MainActivity.this), visit_id);
        call.enqueue(new Callback<FinishVisitResponse>() {
            @Override
            public void onResponse(Call<FinishVisitResponse> call, Response<FinishVisitResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Intent intent=new Intent(MainActivity.this, RateTreatmentSessionActivity.class);
                        intent.putExtra("visit",currentVisit);
                        startActivity(intent);
                    }
                    else  {
                        try{
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else Toast.makeText(MainActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<FinishVisitResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });

    }
}
