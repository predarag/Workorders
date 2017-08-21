package rs.co.sbb.workorders.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.LoginActivity;
import rs.co.sbb.workorders.activity.ProfileActivity;
import rs.co.sbb.workorders.activity.WizardActivity;
import rs.co.sbb.workorders.entity.User;

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


    public static void setUserPreference(Context context,User user){

        SaveSharedPreference.setUser(context,user.getUsername());
        SaveSharedPreference.setFirstname(context,user.getFirstName());
        SaveSharedPreference.setLastname(context,user.getLastName());
        SaveSharedPreference.setEmail(context,user.getEmail());
        SaveSharedPreference.setPhone(context,user.getPhoneNumber());
    }

    public static void deleteUserPreference(Context context){
        SaveSharedPreference.setUser(context,null);
        SaveSharedPreference.setFirstname(context,null);
        SaveSharedPreference.setLastname(context,null);
        SaveSharedPreference.setEmail(context,null);
        SaveSharedPreference.setPhone(context,null);
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

    public static void showDialogQ(final Context context, String title, String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(title);
        dlgAlert.setTitle(message);

        dlgAlert.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context,HomeActivity.class);
                        context.startActivity(i);
                    }
                });

        dlgAlert.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

    }



}
