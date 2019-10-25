package com.e.note.data.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("note")
    Call<ResponseBody> createNote(
            @Field("title") String title,
            @Field("description") String description
    );

}
