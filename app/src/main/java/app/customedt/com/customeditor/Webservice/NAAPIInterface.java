package app.customedt.com.customeditor.Webservice;

/**
 * Created by selvakumark on 28-10-2017.
 */

import app.customedt.com.customeditor.Pojo.MainInfo;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface NAAPIInterface {
    @GET("webservice.php?Case=testWebservice")
    Call<MainInfo> updateData(@Query("content") String aContent);

    @GET("webservice.php?Case=contentList&id=1")
    Call<MainInfo> getHtmlData();
}
