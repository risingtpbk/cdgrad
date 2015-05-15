package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Nguyen Ke Ninh on 4/20/2015.
 */
public class BR_CountData extends BroadcastReceiver{
    DBHelper mydb;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent data) {
        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.

        Log.i("DATACOUNT", "rhrthrt");
        mydb = new DBHelper(context);
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
//
        Calendar cal = new GregorianCalendar();
//        cal.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY,  calendar.get(Calendar.HOUR_OF_DAY));
//
//        // start service after an hour
//        cal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 5);
//        cal.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)+100);
//        cal.set(Calendar.DATE, calendar.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        // set alarm to start service again after receiving broadcast
//        cancelPrePI(context);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_CountData.class);
        intent.putExtra("ACTION","DONT_PLUS");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        am.setExact(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);

        if(data.hasExtra("ACTION"))
        updateDatathisSession(pm);
        else
            mydb.updateDataAfterBoot();
    }

    private void updateDatathisSession(PackageManager pm) {

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
        // loop through the list of installed packages and see if the selected
        // app is in the list
        for (ApplicationInfo packageInfo : packages) {
//
            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
               !pm.getLaunchIntentForPackage(packageInfo.packageName).equals("")&&
                    (hasperInternetSent(pm, packageInfo.packageName))
                    )
            {
                // get the UID for the selected app
                int UID = packageInfo.uid;
                String package_name = packageInfo.packageName;

                // internet usage for particular app(sent and received)
                double thisreceived = (double) TrafficStats.getUidRxBytes(UID)/1024;
                double thissent = (double) TrafficStats.getUidTxBytes(UID)/1024;
//
                mydb.updateData(package_name, thissent, thisreceived);
                mydb.close();
            }
        }
    }

    private void cancelPrePI(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_CountData.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private boolean hasperInternetSent(PackageManager pm, String packagename) {
        try {
            PackageInfo packageInfo1 = pm.getPackageInfo(packagename, PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = packageInfo1.requestedPermissions;

            if(requestedPermissions != null) {
                for (int k = 0; k < requestedPermissions.length; k++) {
                    if(requestedPermissions[k].contains("android.permission.INTERNET"))
                        return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
