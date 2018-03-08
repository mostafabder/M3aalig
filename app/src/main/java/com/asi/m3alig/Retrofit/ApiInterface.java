package com.asi.m3alig.Retrofit;





import com.asi.m3alig.Models.DoctorCurrentVisit;
import com.asi.m3alig.Models.VisitStarted;
import com.asi.m3alig.Responses.ChangeDateOrTime;
import com.asi.m3alig.Responses.CreateVisitResponse;

import com.asi.m3alig.Responses.DoctorRateVisit;
import com.asi.m3alig.Responses.DoctorStartVisit;
import com.asi.m3alig.Responses.DoctorUpdateProfile;
import com.asi.m3alig.Responses.DoctorVisits;
import com.asi.m3alig.Responses.DoctorVisitsOrder;
import com.asi.m3alig.Responses.CurrentResponse;
import com.asi.m3alig.Responses.FinishVisitResponse;
import com.asi.m3alig.Responses.LoginResponse;
import com.asi.m3alig.Responses.NormalResponse;
import com.asi.m3alig.Responses.ProfileUpdateResponse;
import com.asi.m3alig.Responses.RateVisitResponse;
import com.asi.m3alig.Responses.StartingVisit;
import com.asi.m3alig.Responses.VisitResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("/patient/register")
    Call<LoginResponse> registerPatient(@Field("name")String name,@Field("phone")String phone,@Field("country_code")String country_code,
                                         @Field("verify_code")String verify_code,@Field("device_token")String device_token);
    @FormUrlEncoded
    @POST("/patient/register/fb")
    Call<LoginResponse> registerPatientFB(@Field("name")String name,@Field("phone")String phone,@Field("fb_id")String fb_id,@Field("device_token")String device_token);

    @FormUrlEncoded
    @POST("/patient/register/google")
    Call<LoginResponse> registerPatientGmail(@Field("name")String name,@Field("phone")String phone,@Field("google_id")String google_id,@Field("device_token")String device_token);

    @FormUrlEncoded
    @POST("/patient/login/fb")
    Call<LoginResponse> loginPateintFB(@Field("phone")String phone,@Field("fb_id")String fb_id);


    @FormUrlEncoded
    @POST("/patient/login/google")
    Call<LoginResponse>loginPatientGmail(@Field("phone")String phone,@Field("google_id")String google_id);
    @FormUrlEncoded
    @POST("/sendsms")
    Call<NormalResponse> sendsms(@Field("country_code") String country_code, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("/patient/login")
    Call<LoginResponse> verifyPatient(@Field("country_code") String country_code, @Field("phone") String phone
            , @Field("verify_code") String verify_code);

    @FormUrlEncoded
    @POST("/patient/visit-order/create")
    Call<CreateVisitResponse> createVisitOrderPatient(@Field("token") String token, @Field("secret") String secret, @Field("age") String age, @Field("gender") String gender,
                                                      @Field("who_need_session") String who_need_session, @Field("social_statue") String social_statue, @Field("move_level") String move_level,
                                                      @Field("health_problem") String health_problem, @Field("when_pain_start") String when_pain_start, @Field("pain_position") String pain_position,
                                                      @Field("location_floor_number") String location_floor_number, @Field("location_street_name") String location_street_name,
                                                      @Field("location_region") String location_region, @Field("location_city") String location_city, @Field("lat") String lat, @Field("lng") String lng,
                                                      @Field("type") String type, @Field("time") String time, @Field("date") String date);

    @FormUrlEncoded
    @POST("/patient/visit-order/cancel")
    Call<NormalResponse> cancelOrderPatient(@Field("token")String token, @Field("secret")String secret, @Field("visit_order_id")String visit_id);

    @FormUrlEncoded
    @POST("/patient/visits/next")
    Call<VisitResponse> nextVisitPatient(@Field("token") String token, @Field("secret") String secret);

    @FormUrlEncoded
    @POST("/patient/visits/previous")
    Call<VisitResponse> prevVisitPatient(@Field("token") String token, @Field("secret") String secret);

    @FormUrlEncoded
    @POST("/patient/visit/finish")
    Call<NormalResponse> cancelVisitPatient(@Field("token") String token, @Field("secret") String secret, @Field("visit_id") String visit_id);

    @FormUrlEncoded
    @POST("/patient/visit/finish")
    Call<FinishVisitResponse> finishVisitPatient(@Field("token") String token, @Field("secret") String secret, @Field("visit_id") String visit_id);

    //doctor finish visit
    @FormUrlEncoded
    @POST("/doctor/visit/finish")
    Call<FinishVisitResponse> finishVisitDoctor(@Field("secret") String secret, @Field("token") String token, @Field("visit_id") String visit_id);

    //Doctor Register
    @FormUrlEncoded
    @POST("/doctor/register")
    Call<LoginResponse> registerDoctor(
            @Field("phone") String phone, @Field("country_code") String country_code,
            @Field("name") String name, @Field("national_id") String national_id,
            @Field("national_id_expire") String national_id_expire, @Field("graduation_year") String graduation_year,
            @Field("license_number") String license_number, @Field("health_name") String health_name,
            @Field("verify_code") String verify_code, @Field("device_id")String device_id,
            @Field("device_token") String device_token, @Field("area")String area);

    //doctor facebook register
    @FormUrlEncoded
    @POST("/doctor/register/fb")
    Call<LoginResponse> doctorFacebookRegister(@Field("phone")String phone, @Field("name")String name,
                                               @Field("fb_id")String fb_id, @Field("device_token")String device_token);

    //doctor gmail register
    @FormUrlEncoded
    @POST("/doctor/register/google")
    Call<LoginResponse> doctorGmailRegister(@Field("phone")String phone, @Field("name")String name,
                                            @Field("google_id")String fb_id, @Field("device_token")String device_token);

    //Doctor Login
    @FormUrlEncoded
    @POST("/doctor/login")
    Call<LoginResponse> loginDoctor(
            @Field("phone") String phone, @Field("country_code") String country_code,
            @Field("verify_code") String verify_code);

    //doctor facebook login
    Call<LoginResponse> doctorFacebookLogin(@Field("phone")String phone, @Field("fb_id")String fb_id);

    //doctor gmail login
    Call<LoginResponse> doctorGmailLogin(@Field("phone")String phone, @Field("google_id")String google_id);

    //doctor all visits order
    @FormUrlEncoded
    @POST("/doctor/visit-order")
    Call<DoctorVisitsOrder> doctorAllOrders(@Field("secret")String secret, @Field("token")String token);

    //doctor single visit order
    @FormUrlEncoded
    @POST("/doctor/visit-order/select")
    Call<NormalResponse> doctorSingleOrder(@Field("secret")String secret, @Field("token")String token,
                                           @Field("order_id")String order_id);
    @FormUrlEncoded
    @POST("/patient/visit/rate")
    Call<NormalResponse> rateVisit(@Field("token")String token, @Field("secret")String secret,@Field("patient_rate")String patient_rate,@Field("patient_reason")String patient_reason,@Field("visit_id")String visit_id);

    @FormUrlEncoded
    @POST("/patient/messages/send")
    Call<NormalResponse>contact_message(@Field("token")String token, @Field("secret")String secret,@Field("to_type")String to_type,
                                        @Field("to_id")String to_id,@Field("from_type")String from_type,@Field("msg")String msg);
    @FormUrlEncoded
    @POST("/patient/updateProfile")
    Call<ProfileUpdateResponse>update_profile(@Field("token")String token, @Field("secret")String secret,@FieldMap Map<String,String> params);
    //doctor start visit
    @FormUrlEncoded
    @POST("/doctor/visit/start")
    Call<StartingVisit> doctorStartVisit(@Field("secret")String secret, @Field("token")String token,
                                         @Field("visit_id")String visit_id);


    //doctor rate visit
    @FormUrlEncoded
    @POST("/doctor/visit/rate")
    Call<StartingVisit> doctorRateVisit(@Field("secret")String secret, @Field("token")String token,
                                        @Field("doctor_rate")String doctor_rate, @Field("doctor_reason")String doctor_reason,
                                        @Field("visit_id")String visit_id);

    //doctor all visits
    @FormUrlEncoded
    @POST("/doctor/visits")
    Call<DoctorVisits> doctorAllVisits(@Field("secret")String secret, @Field("token")String token);

    //doctor next visit
    @FormUrlEncoded
    @POST("/doctor/visits/next")
    Call<DoctorVisits> doctorNextVisit(@Field("secret")String secret, @Field("token")String token);

    //doctor previous visit
    @FormUrlEncoded
    @POST("/doctor/visits/previous")
    Call<DoctorVisits> doctorPreviousVisit(@Field("secret")String secret, @Field("token")String token);


    //doctor change date or time
    @FormUrlEncoded
    @POST("/doctor/change-data-time")
    Call<ChangeDateOrTime> doctorChangeDateOrTime(@Field("secret")String secret, @Field("token")String token,
                                                  @Field("time")String time, @Field("date")String date,
                                                  @Field("visit_id")String visit_id);

    //doctor current state
    @FormUrlEncoded
    @POST("/doctor/current-status")
    Call<DoctorCurrentVisit> doctorCurrentState(@Field("secret")String secret, @Field("token")String token);

    @FormUrlEncoded
    @POST("/patient/change-data-time")
    Call<NormalResponse> updateNextVisit(@Field("token")String token, @Field("secret")String secret,@Field("date")String date,@Field("time")String time,@Field("visit_id")String visit_id);


    @FormUrlEncoded
    @POST("/patient/current-status")
    Call<CurrentResponse> currentState(@Field("token")String token, @Field("secret")String secret);

    //doctor report
    @FormUrlEncoded
    @POST("/doctor/visit/report/create")
    Call<StartingVisit> doctorReport(@Field("secret")String secret, @Field("token")String token,
                                     @Field("summary")String summary, @Field("medical_desc")String medical_desc,
                                     @Field("long_obj")String long_obj, @Field("short_obj")String short_obj,
                                     @Field("treatment_plan")String treatment_plan, @Field("treatment_desc")String treatment_desc,
                                     @Field("need_follow")String need_follow, @Field("number_session")String number_session,
                                     @Field("other")String other, @Field("patient_id")String patient_id,
                                     @Field("visit_id")String visit_id);


    //doctor update profile
    @FormUrlEncoded
    @POST("/doctor/updateProfile")
    Call<DoctorUpdateProfile> doctorUpdateProfile(@Field("secret")String secret, @Field("token")String token,
                                                  @Field("new_name")String new_name, @Field("new_phone")String new_phone,
                                                  @Field("new_area")String new_area,
                                                  @Field("new_license_number")String new_license_number,
                                                  @Field("new_health_name")String new_health_name);


}

