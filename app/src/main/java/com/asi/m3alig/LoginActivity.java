package com.asi.m3alig;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.asi.m3alig.Responses.LoginResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Tools.Utils;
import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;
import com.mukesh.OtpView;
import com.google.firebase.iid.FirebaseInstanceId;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.EGYPT_PHONE_CODE;
import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.M3ALG_TYPE;
import static com.asi.m3alig.Utility.Constants.PATIENT_TYPE;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;
import static com.asi.m3alig.Utility.Constants.getType;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    LinearLayout linear_login,linear_register;
    CardView login_card,register_card;
    EditText name_patient_register, phone_number_register;
    EditText phone_number_login;
    ImageView fb_iv_login,gmail_iv_login,fb_iv_register,gmail_iv_register;
    LoginButton fb_login,fb_register;
    SignInButton google_login,google_register;
    OtpView otpView;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private SQLiteHandler db;
    private SessionManager session;
    String token,type;
    CallbackManager FBCallbackManager;
    boolean loggin=false; //means logging or register
    boolean checked=false,enter=false;
    GoogleApiClient mGoogleApiClient;
    final int RC_SIGN_IN=1000;
    CountryCodePicker countryCodePickerLogin,countryCodePickerRegister;
    String code_login,code_register;
    //Strings to store register fields
    private String name, idNumber, idNumberExpired, graduatedDate, licenceNumber, theJob, phoneNumber;
    //editText for doctor register fields
    private EditText et_doctor_name, et_doctor_id, et_doctor_idEXP, et_doctor_graduate, et_doctor_licence, et_doctor_job;
    //help strings for doctorValidation method
    private static final String LOGIN_VALIDATION = "login-validation";
    private static final String REGISTER_VALIDATION = "register-validation";
    final String TAG="M3alig";
    Dialog dialog;
    FancyButton submit,modify;
    TextView send_sms,seconds;
    LinearLayout send_L,seconds_L;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        type=getIntent().getStringExtra(Constants.USER_KEY);
        if (getIntent().getStringExtra(Constants.USER_KEY).equals(Constants.M3ALG_TYPE))
        {
            setContentView(R.layout.activity_login_as_moalg);
            linear_login = (LinearLayout) findViewById(R.id.linear_login);
            linear_register= (LinearLayout) findViewById(R.id.linear_register);
            login_card= (CardView) findViewById(R.id.login_card_view);
            register_card= (CardView) findViewById(R.id.register_card_view);
            mAuth = FirebaseAuth.getInstance();
            et_doctor_name = (EditText) findViewById(R.id.et_doctor_name_register);
            et_doctor_id = (EditText) findViewById(R.id.et_doctor_id_register);
            et_doctor_idEXP = (EditText) findViewById(R.id.et_doctor_idEXP_register);
            et_doctor_graduate = (EditText) findViewById(R.id.et_doctor_graduate_register);
            et_doctor_licence = (EditText) findViewById(R.id.et_doctor_licence_register);
            et_doctor_job = (EditText) findViewById(R.id.et_doctor_job_register);
            phone_number_login = (EditText) findViewById(R.id.et_phone_login);
            phone_number_register = (EditText) findViewById(R.id.et_phone_register);
        }
        else
        {
            setContentView(R.layout.activity_login);
            init();
            linear_login = (LinearLayout) findViewById(R.id.linear_login);
            linear_register= (LinearLayout) findViewById(R.id.linear_register);
            login_card= (CardView) findViewById(R.id.login_card_view);
            register_card= (CardView) findViewById(R.id.register_card_view);
        }
        countryCodePickerLogin=(CountryCodePicker)findViewById(R.id.ccp_login);
        countryCodePickerRegister=(CountryCodePicker)findViewById(R.id.ccp_register);
        code_login=countryCodePickerLogin.getSelectedCountryCode();
        code_register=countryCodePickerRegister.getSelectedCountryCode();
        Log.e("selected_Code",code_login+"   ");
        Log.e("selected_Code",code_register+"   ");
        countryCodePickerLogin.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                code_login=countryCodePickerLogin.getSelectedCountryCode();
                Log.e("selected_Code",code_login);
            }
        });
        countryCodePickerRegister.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                code_register=countryCodePickerRegister.getSelectedCountryCode();
                Log.e("selected_Code",code_register);
            }
        });
        social_init();
        token=FirebaseInstanceId.getInstance().getToken();
        Log.e("Firebase_Token",token+"   ");
    }
    public void social_init(){

        FBCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(LoginActivity.this, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, options).build();
        fb_iv_register=(ImageView)findViewById(R.id.fb_register_iv);
        fb_iv_login=(ImageView)findViewById(R.id.fb_login_iv);
        gmail_iv_register=(ImageView)findViewById(R.id.gmail_register_iv);
        gmail_iv_login=(ImageView)findViewById(R.id.gmail_login_iv);
        fb_login=(LoginButton) findViewById(R.id.btn_fb_login);
        fb_register=(LoginButton)findViewById(R.id.btn_fb_register);
        fb_login.setReadPermissions("email","public_profile");
        fb_register.setReadPermissions("email","public_profile");
        fb_login.registerCallback(FBCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e("LOGIN", "Cancel");
                Toast.makeText(getApplicationContext(), "تم الغاء تسجيل الدخول!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LOGIN", "Error");
                Toast.makeText(getApplicationContext(), "خطا فى تسجيل الدخول! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        fb_register.registerCallback(FBCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e("LOGIN", "Cancel");
                Toast.makeText(getApplicationContext(), "تم الغاء تسجيل الدخول!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LOGIN", "Error");
                Toast.makeText(getApplicationContext(), "خطا فى تسجيل الدخول! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        fb_iv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter =true;
            if(!phone_number_register.getText().toString().trim().equals(""))
            {
                if(isLoggedIn())
                    LoginManager.getInstance().logOut();
                loggin=false;
                checked=false;
                fb_register.performClick();
            }
              else Toast.makeText(getApplicationContext(),  getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();

            }
        });
        fb_iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter =true;
                if(!phone_number_login.getText().toString().trim().equals(""))
                {
                    if(isLoggedIn())
                        LoginManager.getInstance().logOut();
                    loggin=true;
                    checked=false;
                    fb_login.performClick();
                }
                else Toast.makeText(getApplicationContext(),  getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();
            }
        });
        gmail_iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter =true;
                if(!phone_number_login.getText().toString().trim().equals(""))
                {
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    {
                        FirebaseAuth.getInstance().getCurrentUser().getProviderData().equals("google.com");
                        {
                            mAuth.signOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {

                                }
                            });
                        }
                    }

                    loggin=true;
                    checked=true;
                    loginGmail();
                }
                else Toast.makeText(getApplicationContext(),  getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();


            }
        });
        gmail_iv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter =true;
                if (!phone_number_register.getText().toString().trim().equals(""))
                {
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    {
                        FirebaseAuth.getInstance().getCurrentUser().getProviderData().equals("google.com");
                        {
                            mAuth.signOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {

                                }
                            });
                        }
                    }
                    loggin=false;
                    checked=true;
                    loginGmail();
                }
                else Toast.makeText(getApplicationContext(),  getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();

            }
        });

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(enter)
                {
                    FirebaseUser user=firebaseAuth.getCurrentUser();

                    if(!checked)//mean facebook
                    {
                        if(!loggin) //mean register
                        {
                            if (
                                    phone_number_register.getText().toString().trim().equals("")) {
                                Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                            } else {
                                if (user != null) {
                                    if(type.equals(PATIENT_TYPE))
                                        registerPatientFB(user.getDisplayName(), phone_number_register.getText().toString(), user.getUid(), token);
                                    else registerDoctorFB(user.getDisplayName(), phone_number_register.getText().toString(), user.getUid(), token);
                                }
                            }
                        }
                        else //mean login
                        {
                            if (phone_number_login.getText().toString().trim().equals("")) {
                                Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                            } else {
                                if (user != null) {
                                    if(type.equals(PATIENT_TYPE))
                                        loginPatientFB( phone_number_login.getText().toString(), user.getUid());
                                    else loginDoctorFB(phone_number_login.getText().toString(), user.getUid());
                                }
                            }
                        }
                    }
                    else {
                        if(!loggin) //mean register
                        {
                            if (phone_number_register.getText().toString().trim().equals("")) {
                                Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                            } else {
                                if (user != null) {
                                    if(type.equals(PATIENT_TYPE))
                                        registerPatientGmail(user.getDisplayName(), phone_number_register.getText().toString(), user.getUid(), token);
                                    else registerDoctorGmail(user.getDisplayName(), phone_number_register.getText().toString(), user.getUid(), token);
                                }
                            }
                        }
                        else //mean login
                        {
                            if (phone_number_login.getText().toString().trim().equals("")) {
                                Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                            } else {
                                if (user != null) {
                                    if(type.equals(PATIENT_TYPE))
                                        loginPatientGmail( phone_number_login.getText().toString(), user.getUid());
                                    else loginDoctorGmail(phone_number_login.getText().toString(), user.getUid());
                                }
                            }
                        }
                    }
                }
            }
        };

    }
    public void init(){
        name_patient_register =(EditText)findViewById(R.id.et_fullname_register);
        phone_number_register =(EditText)findViewById(R.id.et_phone_register);
        phone_number_login=(EditText)findViewById(R.id.et_phone_login);
    }

    public void login(View view) {

        linear_login.setVisibility(View.GONE);
        login_card.setVisibility(View.VISIBLE);
        register_card.setVisibility(View.GONE);
        linear_register.setVisibility(View.VISIBLE);
//        shokry
//        startActivity(new Intent(LoginActivity.this,TreatmentSessionReportActivity.class));
    }

    public void hide_login(View view) {
        linear_login.setVisibility(View.VISIBLE);
        login_card.setVisibility(View.GONE);
    }

    public void hide_register(View view) {
        register_card.setVisibility(View.GONE);
        linear_register.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        linear_login.setVisibility(View.VISIBLE);
        login_card.setVisibility(View.GONE);
        register_card.setVisibility(View.VISIBLE);
        linear_register.setVisibility(View.GONE);
    }

    public void LoginNow(View view) {

        if(getIntent().getStringExtra(Constants.USER_KEY).equals(Constants.M3ALG_TYPE)) {
            if(!phone_number_login.getText().toString().trim().equals(""))
            {
                send_sms(phone_number_login.getText().toString(),code_login);
                dialog=new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_verification_login);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                initDialog();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(otpView.getOTP().length()<4)
                            Toast.makeText(LoginActivity.this,getString(R.string.enter_ver_code),Toast.LENGTH_SHORT).show();
                        else loginDoctor(otpView.getOTP());
                    }
                });
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                send_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        send_sms(phone_number_login.getText().toString(),code_login);
                        start_timer(30000,seconds,seconds_L,send_L);
                    }
                });
                dialog.show();
            }
           else Toast.makeText(getApplicationContext(),  getString(R.string.enter_ver_code), Toast.LENGTH_SHORT).show();
        }
        else {
            if (!phone_number_login.getText().toString().trim().equals("")) {
                send_sms(phone_number_login.getText().toString(), code_login);
                dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_verification_login);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                initDialog();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (otpView.getOTP().length() < 4)
                            Toast.makeText(LoginActivity.this, getString(R.string.enter_ver_code), Toast.LENGTH_SHORT).show();
                        else loginPatient(otpView.getOTP());
                    }
                });
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                send_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        send_sms(phone_number_login.getText().toString(), code_login);
                        start_timer(30000,seconds,seconds_L,send_L);
                    }
                });
                dialog.show();
            } else
                Toast.makeText(LoginActivity.this, getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();
        }
    }

    public void RegisterNow(View view) {
        if(getIntent().getStringExtra(Constants.USER_KEY).equals(Constants.M3ALG_TYPE)) {
            if (doctorValidation(REGISTER_VALIDATION).equals("ok"))
            {
                dialog=new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_verification_login);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                initDialog();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(otpView.getOTP().length()<4)
                            Toast.makeText(LoginActivity.this,getString(R.string.enter_ver_code),Toast.LENGTH_SHORT).show();
                        else registerDoctor(otpView.getOTP());
                    }
                });
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                send_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        send_sms(phone_number_register.getText().toString(),code_register);
                    }
                });
                dialog.show();
            }
            else
                Toast.makeText(getApplicationContext(), doctorValidation(REGISTER_VALIDATION), Toast.LENGTH_SHORT).show();

        } else{
            if(validate().equals("ok"))
            {
                dialog=new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_verification_login);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                initDialog();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(otpView.getOTP().length()<4)
                            Toast.makeText(LoginActivity.this,getString(R.string.enter_ver_code),Toast.LENGTH_SHORT).show();
                        else registerPatient(otpView.getOTP());
                    }
                });
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                send_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        send_sms(phone_number_register.getText().toString(),code_register);
                    }
                });
                dialog.show();
            }
            else Toast.makeText(LoginActivity.this,validate(),Toast.LENGTH_SHORT).show();
        }
    }

    public void initDialog() {
        otpView=(OtpView)dialog.findViewById(R.id.otp_view);
        submit=(FancyButton)dialog.findViewById(R.id.btn_verify);
        modify=(FancyButton)dialog.findViewById(R.id.btn_close);
        send_sms=(TextView)dialog.findViewById(R.id.tv_send_sms);
        seconds=(TextView)dialog.findViewById(R.id.tv_seconds);
        send_L=(LinearLayout)dialog.findViewById(R.id.send_layout);
        seconds_L=(LinearLayout)dialog.findViewById(R.id.seconds_layout);
        start_timer(30000,seconds,seconds_L,send_L);
    }
    public void start_timer(final long cnt, final TextView seconds,final LinearLayout sec, final LinearLayout send){
        seconds_L.setVisibility(View.VISIBLE);
        send_L.setVisibility(View.GONE);
        new CountDownTimer(cnt, 1000) {

            public void onTick(long millisUntilFinished) {
                seconds.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                sec.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
            }
        }.start();
    }
    public void registerPatient(String code){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.registerPatient(name_patient_register.getText().toString(), phone_number_register.getText().toString(),code_register,
               code,token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW4",response.body().getCode()+"   "+response.body().getMessage());
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        session.setLogin(true);
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    //register new doctor
    public void registerDoctor(String code){
        //show waiting progress
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        //register new doctor
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.registerDoctor(
                phoneNumber,code_register, name, idNumber, idNumberExpired, graduatedDate,
                licenceNumber, theJob, code, "device_id", token);
        //send data and receive response
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //here if task successful
                //dismiss the waiting progress
                progressDialog.dismiss();
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        //save this doctor to database
                        Log.e("WOW5",response.body().getCode()+"   "+response.body().getMessage());
                        db = new SQLiteHandler(LoginActivity.this);
                        session = new SessionManager(LoginActivity.this);
                        String doctorSecret = response.body().getData().getSecret();
                        String doctorToken = response.body().getData().getToken();
                        db.addUser(doctorSecret, doctorToken, M3ALG_TYPE);
                        //make application has user was login
                        session.setLogin(true);
                        //move user to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //take user type with intent
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        //here if code not successful
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //here if body null
                    Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //here if task failed
                //dismiss waiting dialog
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                //show dialog for no internet connection
                Utils.showDialog(
                        Utils.getBuilder(LoginActivity.this),
                        "لم يتم الاتصال",
                        getString(R.string.check_connection),
                        true,
                        true);
            }
        });
    }

    public void send_sms(String phone,String country_code){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Call<NormalResponse> call=apiService.sendsms(country_code, phone);
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
                        Toast.makeText(LoginActivity.this, R.string.veri_code_sent,Toast.LENGTH_SHORT).show();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void loginPatient(String code){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("Phone,Code",phone_number_login.getText().toString()+" "+code);


        Call<LoginResponse> call=apiService.verifyPatient(code_login,phone_number_login.getText().toString(), code);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW6",response.body().getCode()+"   "+response.body().getMessage());
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        Log.e("Token",response.body().getData().getToken()+"     ");
                        Log.e("secret",response.body().getData().getSecret()+ "   ");
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        Log.e("Secret",getSecret(LoginActivity.this)+"         ");
                        Log.e("Token",getToken(LoginActivity.this)+"         ");
                        Log.e("type",getType(LoginActivity.this)+"         ");
                        session.setLogin(true);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }


    //login as a doctor
    public void loginDoctor(String code){
        Log.i("schedule", "enter");
        //show waiting dialog
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        //login doctor
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.loginDoctor(phoneNumber, code_login, code);
        //send data and receive response
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //here if task successful
                //dismiss the waiting progress
                progressDialog.dismiss();
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    Log.i("schedule", "not null");
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("schedule", "200");
                        //save this doctor to database
                        db = new SQLiteHandler(LoginActivity.this);
                        session = new SessionManager(LoginActivity.this);
                        String doctorSecret = response.body().getData().getSecret();
                        String doctorToken = response.body().getData().getToken();
                        db.addUser(doctorSecret, doctorToken, M3ALG_TYPE);
                        //make application has user was login
                        session.setLogin(true);

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("secret", doctorSecret);
                        editor.putString("token", doctorToken);
                        editor.apply();

                        //move user to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //take user type with intent
                        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        Log.i("schedule", "210");
                        //here if code not successful
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //here if body null
                    Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //here if task failed
                //dismiss waiting dialog
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
                //show dialog for no internet connection
                Utils.showDialog(
                        Utils.getBuilder(LoginActivity.this),
                        "لم يتم الاتصال",
                        getString(R.string.check_connection),
                        true,
                        true);
            }
        });
    }



    public String doctorValidation(String status){
        //check status
        //if status login
        if(status.equals(LOGIN_VALIDATION)){
            //store fields
            phoneNumber = phone_number_login.getText().toString().trim();
            //check empty fields
            if (phoneNumber.equals(""))
                return getString(R.string.enter_phone);
        }
        //if status register
        if(status.equals(REGISTER_VALIDATION)) {
            //store fields
            name = et_doctor_name.getText().toString().trim();
            idNumber = et_doctor_id.getText().toString().trim();
            idNumberExpired = et_doctor_idEXP.getText().toString().trim();
            graduatedDate = et_doctor_graduate.getText().toString().trim();
            licenceNumber = et_doctor_licence.getText().toString().trim();
            theJob = et_doctor_job.getText().toString().trim();
            phoneNumber = phone_number_register.getText().toString().trim();
            //check empty fields
            if (name.equals(""))
                return getString(R.string.enter_fullanme);
            else if (idNumber.equals(""))
                return "ادخل رقم بطاقة الهوية";
            else if (idNumberExpired.equals(""))
                return "ادخل تاريخ انتهاء بطاقة الهوية";
            else if (graduatedDate.equals(""))
                return "ادخل تاريخ التخرج";
            else if (licenceNumber.equals(""))
                return "ادخل رقم رخصة مزاولة المهنة";
            else if (theJob.equals(""))
                return "ادخل المؤهل الصحي(المسمى الصحي)";
            else if (phoneNumber.equals(""))
                return getString(R.string.enter_phone);
        }
        //return ok if all fields correct
        return "ok";
    }


    public void registerPatientFB(String name,String phone,String fb_id,String token) {
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.registerPatientFB(phone, name,fb_id ,token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        session.setLogin(true);
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
    public void registerPatientGmail(String name,String phone,String id,String token){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.registerPatientGmail(phone, name,id ,token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        session.setLogin(true);
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void loginPatientFB(String phone,String fb_id) {
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("phone,fb_id",phone+ " "+fb_id);


        Call<LoginResponse> call=apiService.loginPateintFB(phone,fb_id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        Log.e("Token",response.body().getData().getToken()+"     ");
                        Log.e("secret",response.body().getData().getSecret()+ "   ");
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        Log.e("Secret",getSecret(LoginActivity.this)+"         ");
                        Log.e("Token",getToken(LoginActivity.this)+"         ");
                        Log.e("type",getType(LoginActivity.this)+"         ");
                        session.setLogin(true);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });

    }
    public void loginPatientGmail(String phone,String id) {
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("phone,fb_id",phone+ " "+id);


        Call<LoginResponse> call=apiService.loginPatientGmail(phone,id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        Log.e("Token",response.body().getData().getToken()+"     ");
                        Log.e("secret",response.body().getData().getSecret()+ "   ");
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),PATIENT_TYPE);
                        Log.e("Secret",getSecret(LoginActivity.this)+"         ");
                        Log.e("Token",getToken(LoginActivity.this)+"         ");
                        Log.e("type",getType(LoginActivity.this)+"         ");
                        session.setLogin(true);
                        intent.putExtra(Constants.USER_KEY,Constants.PATIENT_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void registerDoctorFB(String name,String phone,String fb_id,String token) {
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.doctorFacebookRegister(name, phone,fb_id ,token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW",response.body().getCode()+"   "+response.body().getMessage());
                        Log.e("Token,Secret(NEW)",response.body().getUser_credentials().getToken()+ "  "+response.body().getUser_credentials().getSecret());
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),M3ALG_TYPE);
                        session.setLogin(true);
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
    public void registerDoctorGmail(String name,String phone,String id,String token){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiService.doctorGmailRegister(name, phone,id ,token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW1",response.body().getCode()+"   "+response.body().getMessage());
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),M3ALG_TYPE);
                        session.setLogin(true);
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void loginDoctorFB(String phone,String fb_id){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("phone,fb_id",phone+ " "+fb_id);


        Call<LoginResponse> call=apiService.doctorFacebookLogin(phone,fb_id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW2",response.body().getCode()+"   "+response.body().getMessage());
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        Log.e("Token",response.body().getData().getToken()+"     ");
                        Log.e("secret",response.body().getData().getSecret()+ "   ");
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),M3ALG_TYPE);
                        Log.e("Secret",getSecret(LoginActivity.this)+"         ");
                        Log.e("Token",getToken(LoginActivity.this)+"         ");
                        Log.e("type",getType(LoginActivity.this)+"         ");
                        session.setLogin(true);
                        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void loginDoctorGmail(String phone,String id){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("phone,fb_id",phone+ " "+id);


        Call<LoginResponse> call=apiService.doctorGmailLogin(phone,id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Log.e("WOW3",response.body().getCode()+"   "+response.body().getMessage());
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        db=new SQLiteHandler(LoginActivity.this);
                        session=new SessionManager(LoginActivity.this);
                        Log.e("Token",response.body().getData().getToken()+"     ");
                        Log.e("secret",response.body().getData().getSecret()+ "   ");
                        db.addUser(response.body().getData().getSecret(),response.body().getData().getToken(),M3ALG_TYPE);
                        Log.e("Secret",getSecret(LoginActivity.this)+"         ");
                        Log.e("Token",getToken(LoginActivity.this)+"         ");
                        Log.e("type",getType(LoginActivity.this)+"         ");
                        session.setLogin(true);
                        intent.putExtra(Constants.USER_KEY,Constants.M3ALG_TYPE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  {
                        try{
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(LoginActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void loginGmail(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public String validate(){
        if(name_patient_register.getText().toString().trim().equals(""))
            return getString(R.string.enter_fullanme);
        else if(phone_number_register.getText().toString().trim().equals(""))
            return getString(R.string.enter_phone);
        return "ok";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FBCallbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                if(!loggin) //mean register
                {
                    if (phone_number_register.getText().toString().trim().equals("")) {
                        Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                    } else {
                        if (account != null) {
                            if(type.equals(PATIENT_TYPE))
                            registerPatientGmail(account.getDisplayName(), phone_number_register.getText().toString(), account.getId(), token);
                            else registerDoctorGmail(account.getDisplayName(), phone_number_register.getText().toString(), account.getId(), token);
                        }
                    }
                }
                else //mean login
                {
                    if (phone_number_login.getText().toString().trim().equals("")) {
                        Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                    } else {
                        if (account != null) {
                            if(type.equals(PATIENT_TYPE))
                                loginPatientGmail( phone_number_register.getText().toString(), account.getId());
                            else loginDoctorGmail( phone_number_register.getText().toString(), account.getId());
                        }
                    }
                }
                Log.e("name",account.getDisplayName()+" "+account.getId());

            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(phone_number_login.getText().toString().trim().equals(""))
                                Toast.makeText(LoginActivity.this, R.string.enter_phone, Toast.LENGTH_SHORT).show();
                            else{
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.e("FB_Data",user.getDisplayName()+" "+user.getEmail());
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
