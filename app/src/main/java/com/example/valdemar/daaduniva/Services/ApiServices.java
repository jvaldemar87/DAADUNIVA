package com.example.valdemar.daaduniva.Services;

import com.example.valdemar.daaduniva.Generals.Constantes;
import com.example.valdemar.daaduniva.Models.BaseRespond;
import com.example.valdemar.daaduniva.Models.Record;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiServices {
    private static Retrofit retrofit;
    private static String URL = Constantes.BASE_URL;

    public interface user{
        @GET("login/")
        Call<BaseRespond> login(
                @Query("email") String email,
                @Query("password") String password
        );

        @GET("register/")
        Call<BaseRespond> register(
                @Query("name") String name,
                @Query("last_name") String last_name,
                @Query("email") String email,
                @Query("password") String password
        );
    }

    public interface event{
        @GET("addRecord/")
        Call<BaseRespond> addEvent(
                @Query("event_id") int event_id,
                @Query("email") String email
        );

        @GET("events/")
        Call<List<Record>> events();
    }

    public static Retrofit getRetrofitInstance(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
