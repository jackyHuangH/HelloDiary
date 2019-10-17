package com.zenchn.apilib.service;


import com.zenchn.apilib.base.ApiGlobeConfig;
import com.zenchn.apilib.entity.TokenEntity;
import com.zenchn.apilib.model.HttpResultModel;
import io.reactivex.Observable;
import retrofit2.http.*;

/**
 * 作    者：hzj on 2018/9/17 15:15
 * 描    述：
 * 修订记录：
 */

public interface OAuthService {

    /**
     * 作    者：hzj on 2018/9/17 15:15
     * 描    述：授权接口
     */
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<TokenEntity> login(@Field("client_id") String clientId,
                                  @Field("client_secret") String clientSecret,
                                  @Field("grant_type") String grantType,
                                  @Field("username") String username,
                                  @Field("password") String password);

    /**
     * 作    者：hzj on 2018/9/17 15:15
     * 描    述：刷新令牌接口
     */
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<TokenEntity> refreshToken(@Field("client_id") String clientId,
                                         @Field("client_secret") String clientSecret,
                                         @Field("grant_type") String grantType,
                                         @Field("refresh_token") String refreshToken);

    /**
     * 描    述：注销令牌
     */
    @GET("oauth/logout")
    Observable<HttpResultModel<Object>> logout(@Header(ApiGlobeConfig.ACCESS_TOKEN) String accessToken);

}
