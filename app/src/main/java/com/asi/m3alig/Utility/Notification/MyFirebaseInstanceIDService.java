package com.asi.m3alig.Utility.Notification;

/**
 * Created by Luffy on 2/9/2018.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.asi.m3alig.LoginActivity;
import com.asi.m3alig.MainActivity;
import com.asi.m3alig.R;
import com.asi.m3alig.Responses.LoginResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Retrofit.ApiClient;
import com.asi.m3alig.Retrofit.ApiInterface;
import com.asi.m3alig.Utility.Constants;
import com.asi.m3alig.Utility.SQLiteHandler;
import com.asi.m3alig.Utility.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.asi.m3alig.Utility.Constants.FLAGE_CODE_SUCCSESS;
import static com.asi.m3alig.Utility.Constants.FLAG_CODE_SUCCESS_210;
import static com.asi.m3alig.Utility.Constants.M3ALG_TYPE;
import static com.asi.m3alig.Utility.Constants.getSecret;
import static com.asi.m3alig.Utility.Constants.getToken;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        send_device_token(getToken(MyFirebaseInstanceIDService.this),getSecret(MyFirebaseInstanceIDService.this),refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    public void send_device_token(String token,String secret,String device_token){

        //login doctor
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NormalResponse> call = apiService.send_token(token, secret, device_token);
        //send data and receive response
        call.enqueue(new Callback<NormalResponse>() {
            @Override
            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {

                if(response.body() != null){
                    Log.i("schedule", "not null");

                    if(response.body().getCode().equals(FLAGE_CODE_SUCCSESS)){
                        Log.i("schedule", "200");

                    }
                    else {
                        Log.i("schedule", "210");
                        //here if code not successful
                        try{
                            Toast.makeText(MyFirebaseInstanceIDService.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MyFirebaseInstanceIDService.this,getString(R.string.something_error),Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //here if body null
                    Toast.makeText(MyFirebaseInstanceIDService.this,getString(R.string.contact_services),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NormalResponse> call, Throwable t) {
                Toast.makeText(MyFirebaseInstanceIDService.this,getString(R.string.check_connection),Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.getMessage()+"   ");
            }
        });
    }
}

