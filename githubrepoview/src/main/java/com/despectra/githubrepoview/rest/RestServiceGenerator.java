package com.despectra.githubrepoview.rest;

import com.despectra.githubrepoview.Utils;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Class for generating REST client implementation
 */
public class RestServiceGenerator {

    private RestServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, final String basicAuthorization) {
        RestAdapter.Builder restBuilder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(Utils.getDefaultGsonInstance()))
                .setLogLevel(RestAdapter.LogLevel.BASIC);
        if(basicAuthorization != null) {
            restBuilder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    String authorization = "Basic " + basicAuthorization;
                    request.addHeader("Authorization", authorization);
                    request.addHeader("Accept", "application/json");
                }
            });
        }
        RestAdapter adapter = restBuilder.build();
        return adapter.create(serviceClass);
    }
}
