package rs.co.sbb.workorders.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import rs.co.sbb.workorders.activity.HomeActivity;

/**
 * Created by Predrag.Tasic on 8/25/2017.
 */

public class PermissionHelper {

    private Activity activity;

    public PermissionHelper(Activity activity){
        this.activity = activity;
    }



    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        final int REQUEST_PERMISSION_PHONE_STATE = 1;
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "Dodata privilegija!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Privilegija odbijena!", Toast.LENGTH_SHORT).show();
                }
        }


    }


    public void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Potrebne su dodatne privilegije", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, 1);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            }
        } else {
            Toast.makeText(activity, "Privilegija je vec dodeljena!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permissionName}, permissionRequestCode);
    }

}
