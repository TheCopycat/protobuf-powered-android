package fr.clouddev.protobufpowered.rest;

import fr.clouddev.protobufpowered.preferences.Params;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;

/**
 * Created by CopyCat on 18/04/2016.
 */
public interface ConfigService {

    @GET("/config")
    @Headers("Content-Type: application/www-x-protobuf")
    Params.Preferences getPreferences();
}
