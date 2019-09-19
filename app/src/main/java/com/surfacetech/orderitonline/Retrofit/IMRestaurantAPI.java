package com.surfacetech.orderitonline.Retrofit;

import com.surfacetech.orderitonline.Model.FoodModel;
import com.surfacetech.orderitonline.Model.MenuModel;
import com.surfacetech.orderitonline.Model.RestaurantModel;
import com.surfacetech.orderitonline.Model.UpdateUserModel;
import com.surfacetech.orderitonline.Model.UserModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMRestaurantAPI {
    @GET("user")
    Observable<UserModel> getUser(@Query("key") String apiKey,
                                  @Query("fbid") String fbid);
    @GET("restaurant")
    Observable<RestaurantModel> getRestaurant(@Query("key") String apikey);

    @GET("menu")
    Observable<MenuModel> getCategories(@Query("key") String apiKey,
                                        @Query("restaurantId") int restaurantId);
    @GET("food")
    Observable<FoodModel> getFoodOfMenu(@Query("key") String apiKey,
                                        @Query("menuId") int menuId);


    @POST("user")
    @FormUrlEncoded
    Observable<UpdateUserModel> updateUserInfo(@Field("key") String apiKey,
                                               @Field("userPhone") String userPhone,
                                               @Field("userName") String userName,
                                               @Field("userAddress") String userAddress,
                                               @Field("fbid") String fbid);
}
