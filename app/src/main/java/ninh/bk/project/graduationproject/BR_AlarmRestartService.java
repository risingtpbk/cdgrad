package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Nguyen Ke Ninh on 3/20/2015.
 */
public class BR_AlarmRestartService extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent data) {

        Log.i("STTCOL","OK1");

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        Log.i("SCREEN",  ""+isScreenOn);

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
//
        Calendar cal = new GregorianCalendar();
//        cal.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY,  calendar.get(Calendar.HOUR_OF_DAY));
//
//        // start service after an hour
//        cal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
          cal.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);
//        cal.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)+100);
//        cal.set(Calendar.DATE, calendar.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        // set alarm to start service again after receiving broadcast
        cancelPrePI(context);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_AlarmRestartService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setExact(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);

        if(!isScreenOn)
        {
            Intent intentRestart = new Intent(context, SV_CheckAppRunning.class);
            context.stopService(intentRestart);
        }


        if(!isMyServiceRunning(context) && isScreenOn) {
            Intent intentRestart = new Intent(context, SV_CheckAppRunning.class);
            context.startService(intentRestart);
        }

    }

    public static boolean isMyServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (services != null) {
            for (int i = 0; i < services.size(); i++) {
                if ((SV_CheckAppRunning.class.getName()).equals(services.get(i).service.getClassName()) && services.get(i).pid != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void cancelPrePI(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_AlarmRestartService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

}
