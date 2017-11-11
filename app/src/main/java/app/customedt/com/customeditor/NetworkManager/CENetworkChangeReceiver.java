package app.customedt.com.customeditor.NetworkManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import app.customedt.com.customeditor.Pojo.MainInfo;
import app.customedt.com.customeditor.Session.CESession;
import app.customedt.com.customeditor.Webservice.NAAPIClient;
import app.customedt.com.customeditor.Webservice.NAAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by selvakumark on 10-11-2017.
 */

public class CENetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        CESession aCESession = new CESession(context);
        NAAPIInterface aWebservice = NAAPIClient.getClient().create(NAAPIInterface.class);
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            Log.d("Network Available ", "Flag No 1");

            if (aCESession.CheckUpdateAvailbale()) {

                Call<MainInfo> aCall = aWebservice.updateData(aCESession.getTextContent());
                aCall.enqueue(new Callback<MainInfo>() {
                    @Override
                    public void onResponse(Call<MainInfo> call, Response<MainInfo> response) {
                        Toast.makeText(context, "Data updated sucessfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MainInfo> call, Throwable t) {
                        Toast.makeText(context, "Server Not Reachable, Please try again later", Toast.LENGTH_SHORT).show();

                    }
                });
            }


        }
    }

}
