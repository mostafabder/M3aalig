package com.asi.m3alig.PatientWork.m3aligSideScreens;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.asi.m3alig.R;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;

public class ContactDoctorActivty extends AppCompatActivity {
    Spinner spinner;
    EditText type_et;
    ImageView call_iv, message_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contact_doctor_activty);
        init();
    }

    public void init(){
        spinner=(Spinner)findViewById(R.id.spinner_contact);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(ContactDoctorActivty.this, R.array.items, R.layout.item_spinner_contact);
        spinner.setAdapter(arrayAdapter);
        type_et=(EditText)findViewById(R.id.message_contact_et);
        call_iv =(ImageView)findViewById(R.id.call_contact_iv);
        message_iv =(ImageView)findViewById(R.id.message_contact_iv);
    }

    public void goBack(View view) {
        onBackPressed();
    }

//    public void send_message(String type,String message){
//        final ProgressDialog progressDialog=new ProgressDialog(ContactDoctorActivty.this);
//        progressDialog.setMessage(getString(R.string.please_wait));
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<NormalResponse> call=apiService.contact_message();
//        call.enqueue(new Callback<NormalResponse>() {
//            @Override
//            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response)
//            {
//                progressDialog.dismiss();
//                if(response.body()!=null)
//                {
//                    Log.e("FLAG",response.body().getCode()+"   "+response.body().getMessage());
//                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS))
//                    {
//
//                    }
//                    else  {
//                        try{
//                            Toast.makeText(ContactDoctorActivty.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                        catch (Exception e){
//                            Toast.makeText(ContactDoctorActivty.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }
//                else Toast.makeText(ContactDoctorActivty.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();
//
//            }
//            @Override
//            public void onFailure(Call<NormalResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(ContactDoctorActivty.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
//                Log.e("ERROR",t.getMessage()+"   ");
//            }
//        });
//    }
}
