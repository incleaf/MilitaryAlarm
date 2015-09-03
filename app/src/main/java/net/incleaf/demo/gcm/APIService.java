package net.incleaf.demo.gcm;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by incleaf on 8/22/15.
 */
public interface APIService
{
    @FormUrlEncoded
    @POST("/deviceToken")
    void postDeviceToken(@Field("tokenId")String tokenId, Callback<Response> callBack);
}
