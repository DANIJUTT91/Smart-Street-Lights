package com.example.smartlight;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/on")
    Call<Void> turnOn();

    @GET("/off")
    Call<Void> turnOff();
}
