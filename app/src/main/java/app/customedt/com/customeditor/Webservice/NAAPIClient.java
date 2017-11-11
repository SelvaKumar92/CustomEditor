package app.customedt.com.customeditor.Webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NAAPIClient {

    public static final String BASE_URL = "http://183.82.251.88/trisquare/admin/scripts/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
