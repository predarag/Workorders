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
    private static final String SAP_TEAM_ID = "SAP_TEAM_ID";
    private static final String TEAM_UNIQUE_ID = "TEAM_UNIQUE_ID";
    private static final String COUNTRY_CODE = "COUNTRY_CODE";
    private static final String SAP_STORAGE_LOCATION = "SAP_STORAGE_LOCATION";

    private static final String NOTIFICATION_COTRACT = "NOTIFICATION_COTRACT";
    private static final String NOTIFICATION_PARTNER = "NOTIFICATION_PARTNER";
    private static final String NOTIFICATION_TIME = "NOTIFICATION_TIME";


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

    public static void setNotificationCotract(Context context, String notificationContract){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(NOTIFICATION_COTRACT,notificationContract);
        editor.commit();
    }

    public static String getNotificationCotract(Context context){
        return  getSharedPrederences(context).getString(NOTIFICATION_COTRACT,"");
    }

    public static void setNotificationPartner(Context context, String notificationPartner){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(NOTIFICATION_PARTNER,notificationPartner);
        editor.commit();
    }

    public static String getNotificationPartner(Context context){
        return  getSharedPrederences(context).getString(NOTIFICATION_PARTNER,"");
    }

    public static void setNotificationTime(Context context, String notificationTime){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(NOTIFICATION_PARTNER,notificationTime);
        editor.commit();
    }

    public static String getNotificationTime(Context context){
        return  getSharedPrederences(context).getString(NOTIFICATION_TIME,"");
    }

    public static void setSapTeamId(Context context, String sapTeamId){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(SAP_TEAM_ID,sapTeamId);
        editor.commit();
    }

    public static String getSapTeamId(Context context){
        return  getSharedPrederences(context).getString(SAP_TEAM_ID,"");
    }

    public static void setTeamUniqueId(Context context, String setTeamUniqueId){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(TEAM_UNIQUE_ID,setTeamUniqueId);
        editor.commit();
    }

    public static String getTeamUniqueId(Context context){
        return  getSharedPrederences(context).getString(TEAM_UNIQUE_ID,"");
    }

    public static void setCountryCode(Context context, String setCountryCode){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(COUNTRY_CODE,setCountryCode);
        editor.commit();
    }

    public static String getCountryCode(Context context){
        return  getSharedPrederences(context).getString(COUNTRY_CODE,"");
    }

    public static void setSapStorageLocation(Context context, String sapStorageLocation){
        SharedPreferences.Editor editor = getSharedPrederences(context).edit();
        editor.putString(SAP_STORAGE_LOCATION,sapStorageLocation);
        editor.commit();
    }

    public static String getSapStorageLocation(Context context){
        return  getSharedPrederences(context).getString(SAP_STORAGE_LOCATION,"");
    }

}
