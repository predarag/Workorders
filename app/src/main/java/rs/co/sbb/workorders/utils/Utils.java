package rs.co.sbb.workorders.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.LoginActivity;
import rs.co.sbb.workorders.activity.enums.EStatusCode;
import rs.co.sbb.workorders.entity.LoginResponse;
import rs.co.sbb.workorders.entity.NotificationTokenRequest;
import rs.co.sbb.workorders.entity.Response;
import rs.co.sbb.workorders.entity.User;
import rs.co.sbb.workorders.ws.ExternalAuthService;
import rs.co.sbb.workorders.ws.impl.ExternalAuthServiceImpl;

/**
 * Created by Predrag.Tasic on 8/11/2017.
 */

public class Utils {

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }


    public static void setUserPreference(Context context,User user, String sessionToken){
        SaveSharedPreference.setUser(context,user.getUsername());
        SaveSharedPreference.setFirstname(context,user.getFirstName());
        SaveSharedPreference.setLastname(context,user.getLastName());
        SaveSharedPreference.setEmail(context,user.getEmail());
        SaveSharedPreference.setPhone(context,user.getPhoneNumber());
        SaveSharedPreference.setSessionToken(context,sessionToken);
    }


    public static void deleteUserPreference(Context context){
        SaveSharedPreference.setUser(context,null);
        SaveSharedPreference.setFirstname(context,null);
        SaveSharedPreference.setLastname(context,null);
        SaveSharedPreference.setEmail(context,null);
        SaveSharedPreference.setPhone(context,null);
        SaveSharedPreference.setSessionToken(context,null);
    }

    public static void showDialog(Context context, String title, String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(title);
        dlgAlert.setTitle(message);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    public static void showDialogQ(Context context, String title, String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(title);
        dlgAlert.setTitle(message);
        dlgAlert.setPositiveButton("Cancel", null);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dlgAlert.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
    }


    public static void setNotificationToken(String username, String token){

        ExternalAuthServiceImpl service = new ExternalAuthServiceImpl();
        Log.i("TOKEN","USAO SA: "+username+" "+token);
        if(token != null) {

            NotificationTokenRequest request = new NotificationTokenRequest();
            request.setUsername(username);
            request.setToken(token);

            Call<Response> call = service.setNotificationToken(request);

            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (null != response && !response.isSuccessful() && response.errorBody() != null) {
                        Log.i("TOKEN", response.code() + "");
                        Log.i("TOKEN", response.errorBody() + "");
                    } else {
                        Response notificationResponse = response.body();

                        if (null != notificationResponse.getStatus() && notificationResponse.getStatus().equals(EStatusCode.OK.value())) {
                            Log.i("TOKEN", "Notification token successfuly sent to server..");
                        } else {
                            Log.i("TOKEN", "Token sending error: " + notificationResponse.getStatusMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("TOKEN", t.getMessage() == null ? "Null error" : t.getMessage());
                }
            });
        }
        else{
            Log.i("TOKEN", "Token is null...");
        }



    }


}
