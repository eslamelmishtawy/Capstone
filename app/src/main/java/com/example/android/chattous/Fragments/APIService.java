package com.example.android.chattous.Fragments;

import com.example.android.chattous.Notifications.MyResponse;
import com.example.android.chattous.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAe04Cvd8:APA91bEGJaTelZnxsOwBVKx_CyoAMQQuEnGad9eWriALJXW5YEVQOXzH4PbhFK0HSSjdqjKqg0vrYJ1R0cy2w9QdC3HqErpFahiTFxOF3kXvnB-WldkXeW32sh7wOIMZLs32U0WcoxQ0"

            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotifications(@Body Sender body);
}
