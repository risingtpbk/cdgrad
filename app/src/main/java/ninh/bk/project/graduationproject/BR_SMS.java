package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Nguyen Ke Ninh on 4/25/2015.
 */
public class BR_SMS extends BroadcastReceiver {
    DBHelper mydb;
    static int countcol = 0;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent data) {
        mydb = new DBHelper(context);
        final PackageManager pm = context.getPackageManager();

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
//
        Calendar cal = new GregorianCalendar();
//        cal.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY,  calendar.get(Calendar.HOUR_OF_DAY));
//
//        // start service after an hour
//        cal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 2);
//        cal.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)+100);
//        cal.set(Calendar.DATE, calendar.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        // set alarm to start service again after receiving broadcast

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_SMS.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        am.setExact(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);

        Log.i("SMSBROAD","dfonj");

        //------------------------------------------------------

        long time = System.currentTimeMillis();

        Uri mSmsinboxQueryUri = Uri.parse("content://sms/sent");
        String[] args = {String.valueOf(time)};

        Cursor cursor1 = context.getContentResolver().query(mSmsinboxQueryUri,
                new String[] { "address","date_sent", "body"}, "date_sent < ?",args, null);
        Log.i("Count123", ""+cursor1.getCount());
        String[] columns = new String[] { "address", "date_sent", "body"};

        if(countcol==0) countcol = cursor1.getCount();

        Log.e("COUNTINTENT", ""+countcol);

        if (cursor1.getCount() > countcol) {

            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService( Context.ACTIVITY_SERVICE );
            List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();

            ArrayList app = new ArrayList();
            for(int k = 0; k < procInfos.size(); k++)
            {
                String packagename = procInfos.get(k).pkgList[0];
                if (pm.getLaunchIntentForPackage(packagename) != null &&
                    !pm.getLaunchIntentForPackage(packagename).equals("")&&
                     hasperSMSSent(pm,packagename)&&
                     !app.contains(mydb.getAppName(packagename))
                        )
                {
                    app.add(mydb.getAppName(packagename));
                }
            }

            countcol = cursor1.getCount();
            String count = Integer.toString(cursor1.getCount());
            Log.e("Count",""+countcol);

            cursor1.moveToFirst();
//            while (cursor1.isAfterLast() == false) {
//                if (cursor1.isAfterLast()) {
                    String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));

                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                    long dates = (long) Float.parseFloat(cursor1.getString(cursor1.getColumnIndex(columns[1])));
                    long dates = System.currentTimeMillis();
                    Date date2 = new Date(dates);
                    String formattedDate1 = formatter.format(date2);

            Log.i("DATEDATE", formattedDate1 + date2 + "/" +dates);
//                log.append("2,Date Sent: " + formattedDate1 + "\n" + dates+"\n");

                    String body = cursor1.getString(cursor1.getColumnIndex(columns[2]));
//                log.append("3,Body: " + body + "\n\n");

//                }
//            }
            String process = "";
            for (int i = 0; i < app.size(); i++)
            {
                Log.i("PROCESS", i + ": " +app.get(i).toString());
                process = process + "/" + app.get(i).toString();
            }
            mydb.insertSms(address, formattedDate1, body, process);

        }
        else
            Log.i("Count", "0");
    }

    private boolean hasperSMSSent(PackageManager pm, String packagename) {
        try {
            PackageInfo packageInfo1 = pm.getPackageInfo(packagename, PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = packageInfo1.requestedPermissions;

            if(requestedPermissions != null) {
                for (int k = 0; k < requestedPermissions.length; k++) {
                    if(requestedPermissions[k].contains("android.permission.SEND_SMS"))
                        return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }



    private void cancelPrePI(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BR_AlarmRestartService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
