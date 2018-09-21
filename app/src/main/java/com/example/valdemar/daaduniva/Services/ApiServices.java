package com.example.valdemar.daaduniva.Services;

import com.example.valdemar.daaduniva.Generals.Constantes;
import com.example.valdemar.daaduniva.Models.Record;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ApiServices {
    private static Retrofit retrofit;
    private static String URL = Constantes.BASE_URL;

    public interface user{
        @POST("login")
        Call<Integer> login(
                @Body RequestBody body
        );

        @POST("register/")
        Call<Integer> register(
                @Body RequestBody body
        );
    }

    public interface event{
        @POST("addRecord/")
        Call<Integer> addEvent(
                @Body RequestBody body
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
