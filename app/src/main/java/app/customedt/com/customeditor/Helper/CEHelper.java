package app.customedt.com.customeditor.Helper;

import android.app.Activity;
import android.content.Context;

import app.customedt.com.customeditor.NetworkManager.CENetworkManager;


/**
 * Created by selvakumark on 28-10-2017.
 */

public class CEHelper {

    public static boolean checkInternet(Activity aContext) {
        return new CENetworkManager().isInternetOn(aContext);

    }


    public static boolean checkInternet(Context aContext) {
        return new CENetworkManager().isInternetOn(aContext);

    }


}
