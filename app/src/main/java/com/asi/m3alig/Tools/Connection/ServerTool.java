package com.asi.m3alig.Tools.Connection;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.asi.m3alig.Models.TestModel;
import com.asi.m3alig.Tools.loadingDialog;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class ServerTool {


    public interface ApiInterface {


        @GET(URLS.URL_GET_ALL_FAQ)
        Call<List<TestModel>> getFAQ();


    }


    public static void getFAQ(final Context context, final APICallBack apiCallBack) {
        final Dialog dialogsLoading = new loadingDialog().showDialog(context);

        final RetrofitTool retrofitTool = new RetrofitTool();
        retrofitTool.getAPIBuilder(URLS.URL_BASE).getFAQ().enqueue(new RetrofitTool.APICallBack<List<TestModel>>() {
            @Override
            public void onSuccess(List<TestModel> response) {
                apiCallBack.onSuccess(response);
                if (dialogsLoading != null) {
                    dialogsLoading.dismiss();
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                apiCallBack.onFailed(statusCode, responseBody);
                if (dialogsLoading != null) {
                    dialogsLoading.dismiss();
                }

            }
        });
    }



    private static <M> void makeRequest(final Context context, boolean showDialog, Call call, final APICallBack apiCallBack, final RetrofitTool retrofitTool) {
        final Dialog dialogsLoading = new loadingDialog().showDialog(context);
        call.enqueue(new RetrofitTool.APICallBack<M>() {
            @Override
            public void onSuccess(M response) {
                apiCallBack.onSuccess(response);

                //Hide loading
                if (dialogsLoading != null) {
                    dialogsLoading.dismiss();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                //Hide loading
                handleGeneralFailure(context, statusCode, responseBody, retrofitTool);
                apiCallBack.onFailed(statusCode, responseBody);
                if (dialogsLoading != null) {
                    dialogsLoading.dismiss();
                }
            }
        });
    }

    private static void handleGeneralFailure(Context context, int statusCode, ResponseBody responseBody, RetrofitTool retrofitTool) {
        Retrofit retrofit = retrofitTool.getRetrofit(URLS.URL_BASE);
        Log.d("statusCode", statusCode + "");

//        if (responseBody != null) {
//            Converter<ResponseBody, ErrorModel> errorConverter =
//                    retrofit.responseBodyConverter(ErrorModel.class, new Annotation[0]);
//            ErrorModel errorModel = null;
//            try {
//                errorModel = errorConverter.convert(responseBody);
//                Log.d("errorModel", errorModel.getId() + "");
//                switch (errorModel.getId()) {
//                    case 1:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 2:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        logOut(context);
//                        break;
//                    case 3:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 4:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 5:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 6:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 7:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 8:
//                        customToastView((Activity) context, errorModel.getMessage());
//
//                        break;
//                    case 9:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 10:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 11:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 12:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 13:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 14:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 15:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 16:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 17:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 18:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 19:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 20:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 27:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 500:
//                        customToastView((Activity) context, errorModel.getMessage());
//                    case 400:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 401:
//                        customToastView((Activity) context, errorModel.getMessage());
////                            logOut(context);
//                        break;
//                    case 403:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 404:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//                    case 422:
//                        customToastView((Activity) context, errorModel.getMessage());
//                        break;
//
//                    default:
//                        break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return;
//        }

//        }
    }

    public static interface APICallBack<T> {
        void onSuccess(T response);

        void onFailed(int statusCode, ResponseBody responseBody);

    }

}
