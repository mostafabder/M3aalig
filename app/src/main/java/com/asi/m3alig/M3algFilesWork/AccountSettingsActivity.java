package com.asi.m3alig.M3algFilesWork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.asi.m3alig.Adapters.M3alig.DoctorOrdersRecyclerViewAdapter;
import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.Models.OrderDetails;
import com.asi.m3alig.Models.Orders;
import com.asi.m3alig.Models.SingleOrder;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.DoctorUpdateProfile;
import com.asi.m3alig.Responses.DoctorVisitsOrder;

import com.asi.m3alig.PatientWork.m3aligSideScreens.ContactDoctorActivty;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Responses.ProfileUpdateResponse;

import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.PreferenceUtilities;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;
import com.hbb20.CountryCodePicker;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.FLAG_CODE_SUCCESS_512;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

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

public class AccountSettingsActivity extends AppCompatActivity {


    private ArrayAdapter doctorWorkAreaAdapter;
    private Spinner doctorWorkAreaSpinner;
    private String doctorWorkArea;
    private int areaIndex;

    private EditText et_editPhone, et_editName, et_editLicenseNumber, et_editHealthName;
    private TextView tv_doctorWorkArea;
    private CountryCodePicker countryCodePicker;
    private Button bt_saveChanges;
    private TextWatcher textWatcher;

    private String area = "";
    Boolean start;

    private ImageView ivBackArrow, imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(AccountSettingsActivity.this, PreferenceUtilities.getLanguage(AccountSettingsActivity.this));
        setContentView(R.layout.activity_account_settings);

        //false mean data came without change button
        start=false;

        doctorWorkAreaSpinner = (Spinner) findViewById(R.id.sp_doctorWorkArea);
        doctorWorkAreaAdapter = ArrayAdapter.createFromResource(this,
                R.array.work_areas, R.layout.support_simple_spinner_dropdown_item);
        doctorWorkAreaAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doctorWorkAreaSpinner.setAdapter(doctorWorkAreaAdapter);
        doctorWorkAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doctorWorkArea = (String) doctorWorkAreaAdapter.getItem(i);
                bt_saveChanges.setEnabled(true);
                bt_saveChanges.setBackgroundResource(R.drawable.light_save_changes);

                ArrayList<String> areas = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.work_areas)));

                if(doctorWorkArea.equals(getResources().getStringArray(R.array.work_areas)[0]) ||
                        doctorWorkArea.equals(tv_doctorWorkArea.getText().toString().trim())  ||
                        (areas.indexOf(doctorWorkAreaAdapter.getItem(i)) == areaIndex)){
                    doctorWorkArea = null;
                    bt_saveChanges.setEnabled(false);
                    bt_saveChanges.setBackgroundResource(R.drawable.dark_save_changes);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_editName = (EditText) findViewById(R.id.et_editName);
        et_editPhone = (EditText) findViewById(R.id.et_editPhone);
        et_editLicenseNumber = (EditText) findViewById(R.id.et_editLicenseNumber);
        et_editHealthName = (EditText) findViewById(R.id.et_editHealthName);

        tv_doctorWorkArea = (TextView) findViewById(R.id.tv_doctorWorkArea);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePicker);

        bt_saveChanges = (Button) findViewById(R.id.bt_saveChanges);

        getDoctorDetails(null,null,null,null,null);

        et_editPhone.addTextChangedListener(setTextWatcher());
        et_editName.addTextChangedListener(setTextWatcher());
        et_editLicenseNumber.addTextChangedListener(setTextWatcher());
        et_editHealthName.addTextChangedListener(setTextWatcher());

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
            imageButton.setImageResource(R.drawable.log_out);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
            imageButton.setImageResource(R.drawable.asset_3xhdpi);
        }

    }

    public void saveChanges(View view){
        start=true;
        getDoctorDetails(et_editName.getText().toString().trim(),
                et_editPhone.getText().toString().trim(), doctorWorkArea,
                et_editLicenseNumber.getText().toString().trim(), et_editHealthName.getText().toString().trim());
    }

    private void getDoctorDetails(String new_name, String new_phone, String new_area,
                                  String new_license_number, String new_health_name){
        if(new_name==null || new_name.equals(et_editName.getHint().toString().trim())){new_name = "";}
        if(new_phone==(null) || new_name.equals(et_editPhone.getHint().toString().trim())){new_phone = "";}
        if(new_area==(null) || new_name.equals(tv_doctorWorkArea.getText().toString().trim())){new_area = "";}
        if(new_license_number==(null) || new_name.equals(et_editLicenseNumber.getHint().toString().trim())){new_license_number = "";}
        if(new_health_name==(null) || new_name.equals(et_editHealthName.getHint().toString().trim())){new_health_name = "";}

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DoctorUpdateProfile> call = apiService.doctorUpdateProfile(
                getSecret(getApplicationContext()), getToken(getApplicationContext()),
                new_name, new_phone, new_area, new_license_number, new_health_name);
        call.enqueue(new Callback<DoctorUpdateProfile>() {
            @Override
            public void onResponse(Call<DoctorUpdateProfile> call, Response<DoctorUpdateProfile> response) {

                //here if task successful
                //check the respond body is null or not
                //if body not null
                if(response.body() != null){
                    //check if response code is successful or not
                    //if code successful
                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        if(start)
                        {
                            startActivity(getIntent());
                            finish();
                        }
                        else{
                        area = response.body().getData().getArea();
                        ArrayList<String> areas = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.work_areas)));
                        areaIndex = areas.indexOf(area);
                        if (areaIndex == -1){
                            areas = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.areas)));
                            areaIndex = areas.indexOf(area);
                        }
                        doctorWorkAreaSpinner.setSelection(areaIndex);

                        Log.i("area", area);
                        Log.i("areaIndex", areaIndex+"");

                        setHintsToEditText(response.body().getData().getName(), response.body().getData().getPhone(),
                                response.body().getData().getArea(), response.body().getData().getLicense_number(),
                                response.body().getData().getHealth_name());
                        }
                    }
                    else if(response.body().getCode().equals(FLAG_CODE_SUCCESS_512)){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_number), Toast.LENGTH_SHORT).show();
                    }
                    else {

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
            public void onFailure(Call<DoctorUpdateProfile> call, Throwable t) {
                //here if task failed
                Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

    private void setHintsToEditText(String name, String phone, String area, String license, String health){
        et_editPhone.setHint(phone);
        et_editName.setHint(name);
        et_editLicenseNumber.setHint(license);
        et_editHealthName.setHint(health);
        tv_doctorWorkArea.setText(area);

        et_editName.setText(null);
        et_editPhone.setText(null);
        et_editLicenseNumber.setText(null);
        et_editHealthName.setText(null);
    }

    private TextWatcher setTextWatcher(){
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    bt_saveChanges.setEnabled(true);
                    bt_saveChanges.setBackgroundResource(R.drawable.light_save_changes);
                } else {
                    bt_saveChanges.setEnabled(false);
                    bt_saveChanges.setBackgroundResource(R.drawable.dark_save_changes);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

    public void logout(View view){
        Intent intent = new Intent(AccountSettingsActivity.this, BeforLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new SQLiteHandler(getApplicationContext()).deleteUsers();
        new SessionManager(AccountSettingsActivity.this).setLogin(false);
        startActivity(intent);
        finish();
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
