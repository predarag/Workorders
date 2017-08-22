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
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String SESSION_TOKEN = "SESSION_TOKEN";


    public static SharedPreferences getSharedPrederences(Context context){
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

    public static void setFirstname(Context context, String firstname){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(FIRSTNAME,firstname);
        editor.commit();
    }

    public static String getFIRSTNAME(Context context) {
        return getSharedPrederences(context).getString(FIRSTNAME,"");
    }

    public static void setLastname(Context context, String lastname){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(LASTNAME,lastname);
        editor.commit();
    }

    public static String getLASTNAME(Context context) {
        return getSharedPrederences(context).getString(LASTNAME,"");
    }

    public static void setEmail(Context context, String email){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(EMAIL,email);
        editor.commit();
    }

    public static String getEMAIL(Context context) {
        return getSharedPrederences(context).getString(EMAIL,"");
    }

    public static void setPhone(Context context, String phone){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(PHONE,phone);
        editor.commit();
    }

    public static String getPHONE(Context context) {
        return getSharedPrederences(context).getString(PHONE,"");
    }

    public static void setSessionToken(Context context, String sessionToken){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(SESSION_TOKEN,sessionToken);
        editor.commit();
    }

    public static String getSessionToken(Context context){
        return  getSharedPrederences(context).getString(SESSION_TOKEN,"");
    }

}
