package app.customedt.com.customeditor.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CESession {

    // ---Initialize Shared preference---
    protected SharedPreferences myPreference;
    protected SharedPreferences.Editor myEditor;

    public CESession(Context aCtx) {
        myPreference = PreferenceManager.getDefaultSharedPreferences(aCtx);
        myEditor = myPreference.edit();

    }

    public void putUpdateAvailable(boolean aConfigMobileNo) {
        // ---Put string---
        myEditor.putBoolean("is_update_available", aConfigMobileNo);
        myEditor.commit();
    }

    public boolean CheckUpdateAvailbale() {
        // ---Assign value---
        return myPreference.getBoolean("is_update_available", false);


    }

    public void putTextContent(String aConfigMobileNo) {
        // ---Put string---
        myEditor.putString("TextContent", aConfigMobileNo);
        myEditor.commit();
    }

    public String getTextContent() {
        // ---Assign value---
        return myPreference.getString("TextContent", "");


    }
}
