package com.example.gullermin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitAPI {
    @POST("/users")

        //en la linea de abajo realizamos un metodo para realizar el post
    Call<DataModal> createPost(@Body DataModal dataModal);
}
