package rs.co.sbb.workorders.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Predrag.Tasic on 8/9/2017.
 */

public class SaveSharedPreference {

    private static final String USER = "USER";
    private static final String TOKEN = "TOKEN";

    private static SharedPreferences getSharedPrederences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, String user){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(USER, user);
        editor.commit();
    }

    public static void setToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }

    public static String getUser(Context context){
        return getSharedPrederences(context).getString(USER,"");
    }

    public static String getToken(Context context){
        return getSharedPrederences(context).getString(TOKEN,"");
    }
}
