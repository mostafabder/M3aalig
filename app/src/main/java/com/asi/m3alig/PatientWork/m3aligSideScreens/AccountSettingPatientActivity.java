package com.asi.m3alig.PatientWork.m3aligSideScreens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.ProfileUpdateResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.PreferenceUtilities;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.M3ALG_TYPE;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;
import static com.asi.m3alig.Utility.Constants.getType;
import static com.asi.m3alig.Utility.PreferenceUtilities.ARABIC_LANGUAGE;
import static com.asi.m3alig.Utility.PreferenceUtilities.ENGLISH_LANGUAGE;

public class AccountSettingPatientActivity extends AppCompatActivity {


    private SessionManager session;
    Map<String,String> params;
    EditText name_et,phone_et;
    TextView submit;

    private ImageView ivBackArrow, iv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(AccountSettingPatientActivity.this, PreferenceUtilities.getLanguage(AccountSettingPatientActivity.this));
        setContentView(R.layout.activity_account_setting_patient);
        init();

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
            iv_logout.setImageResource(R.drawable.log_out);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
            iv_logout.setImageResource(R.drawable.asset_3xhdpi);
        }

    }


    public void logout(View view){
        session = new SessionManager(AccountSettingPatientActivity.this);
        session.setLogin(false);
        Intent intent = new Intent(AccountSettingPatientActivity.this, BeforLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new SQLiteHandler(getApplicationContext()).deleteUsers();
        startActivity(intent);
        finish();
    }
    public void init(){
        name_et=(EditText)findViewById(R.id.name_profile_et);
        phone_et=(EditText)findViewById(R.id.phone_profile_et);
        submit=(TextView)findViewById(R.id.submit_profile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getType(AccountSettingPatientActivity.this).equals(M3ALG_TYPE))
                {

                }
                else
                {
                    update_patient();
                }
            }
        });
        get_patient_data();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void update_patient(){
        params=new HashMap<>();
        if(!name_et.getText().toString().trim().equals(""))
            params.put("new_name",name_et.getText().toString());
        if(!phone_et.getText().toString().trim().equals(""))
            params.put("new_phone",phone_et.getText().toString());
        final ProgressDialog progressDialog=new ProgressDialog(AccountSettingPatientActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileUpdateResponse> call=apiService.update_profile(getToken(AccountSettingPatientActivity.this),getSecret(AccountSettingPatientActivity.this),params);
        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        Toast.makeText(AccountSettingPatientActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getCode().equals("512"))
                    {
                        Toast.makeText(AccountSettingPatientActivity.this, R.string.wrong_number,Toast.LENGTH_SHORT).show();
                    }
                    else  {
                        try{
                            Toast.makeText(AccountSettingPatientActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
    public void get_patient_data(){
        params=new HashMap<>();
        final ProgressDialog progressDialog=new ProgressDialog(AccountSettingPatientActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileUpdateResponse> call=apiService.update_profile(getToken(AccountSettingPatientActivity.this),getSecret(AccountSettingPatientActivity.this),params);
        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response)
            {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
                    {
                        name_et.setText(response.body().getData().getName());
                        phone_et.setText(response.body().getData().getPhone());
                    }
                    else  {
                        try{
                            Toast.makeText(AccountSettingPatientActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AccountSettingPatientActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    public void changeLanguage(View view){
        String language = PreferenceUtilities.getLanguage(this);
        if(language.equals(ARABIC_LANGUAGE)){
            PreferenceUtilities.setLocale(this, ENGLISH_LANGUAGE);
            //recreate();
            startActivity(getIntent());
        }
        if(language.equals(ENGLISH_LANGUAGE)){
            PreferenceUtilities.setLocale(this, ARABIC_LANGUAGE);
            //recreate();
            startActivity(getIntent());
        }
    }
}
