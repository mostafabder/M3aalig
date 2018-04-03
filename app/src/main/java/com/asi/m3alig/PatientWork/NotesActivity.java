package com.asi.m3alig.PatientWork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.asi.m3alig.BeforLoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.Models.Visit;
import com.asi.m3alig.Models.VisitOrderPatient;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.CreateVisitResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.PreferenceUtilities;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.PATIENT_TYPE;
import static com.asi.m3alig.Utility.Constants.USER_KEY;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;

public class NotesActivity extends AppCompatActivity {
    VisitOrderPatient order;

    private ImageView ivBackArrow, ivRow1, ivRow2, ivRow3, ivRow4, ivRow5, ivRow6, ivRow7, ivRow8, ivRow9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PreferenceUtilities.setLocale(NotesActivity.this, PreferenceUtilities.getLanguage(NotesActivity.this));
        setContentView(R.layout.activity_notes);
        order=(VisitOrderPatient)getIntent().getSerializableExtra("order");
        Log.e("data-1",order.toString());

        ivBackArrow = (ImageView) findViewById(R.id.ivBackArrow);
        ivRow1 = (ImageView) findViewById(R.id.ivRow1);
        ivRow2 = (ImageView) findViewById(R.id.ivRow2);
        ivRow3 = (ImageView) findViewById(R.id.ivRow3);
        ivRow4 = (ImageView) findViewById(R.id.ivRow4);
        ivRow5 = (ImageView) findViewById(R.id.ivRow5);
        ivRow6 = (ImageView) findViewById(R.id.ivRow6);
        ivRow7 = (ImageView) findViewById(R.id.ivRow7);
        ivRow8 = (ImageView) findViewById(R.id.ivRow8);
        ivRow9 = (ImageView) findViewById(R.id.ivRow9);
        if(Locale.getDefault().getLanguage().equals("ar")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow3.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow4.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow5.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow6.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow7.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow8.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow9.setImageResource(R.drawable.main_screen_arrow_icon);
        } if(Locale.getDefault().getLanguage().equals("en")){
            ivBackArrow.setImageResource(R.drawable.main_screen_arrow_icon);
            ivRow1.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow2.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow3.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow4.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow5.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow6.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow7.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow8.setImageResource(R.drawable.main_screen_arrow_icon_en);
            ivRow9.setImageResource(R.drawable.main_screen_arrow_icon_en);
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void nextPage(View view) {
        createOrder();
    }

    public void createOrder(){
        final ProgressDialog progressDialog=new ProgressDialog(NotesActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e("TOKEN,SECRET",getToken(NotesActivity.this)+"  "+getSecret(NotesActivity.this));
        Log.e("data",order.toString());

        if(order.getLocation_floor_number().equals("")){
            order.setLocation_floor_number("floor");
        }

        Call<NormalResponse> call=apiService.createVisitOrderPatient(
                getToken(NotesActivity.this),
                getSecret(NotesActivity.this),
                order.getAge(),
                order.getGender(),
                order.getWho_need_session(),
                "as",
                order.getMove_level(),
                order.getHealth_problem(),
                order.getWhen_pain_start(),
                order.getPain_position(),
                order.getLocation_floor_number(),
                order.getLocation_street_name(),
                order.getLocation_region(),
                order.getLocation_city(),
                order.getLat(),
                order.getLng(),
                order.getType(),
                order.getTime(),
                order.getDate());
        Log.e("data2",order.toString());
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
                        Toast.makeText(NotesActivity.this,getString(R.string.succes_added),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(NotesActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else  {
                        try{
                            Toast.makeText(NotesActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(NotesActivity.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else Toast.makeText(NotesActivity.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NotesActivity.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }

}
