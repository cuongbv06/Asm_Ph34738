package com.sonans.lab1_and102;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_service {

    String DOMAIN = "http://192.168.63.87:3000";
    @GET("/api/list")
    Call<List<OtoModel>> getOto();

    @POST("/api/add-car")
    Call<Void> addCar(@Body OtoModel car);

    @DELETE("/api/delete-car/{id}")
    Call<Void> deleteCar(@Path("id") String carId);

    @PUT("/api/update-car/{id}")
    Call<Void> updateCar(@Path("id") String carId, @Body OtoModel car);

}
