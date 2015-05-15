package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by NINH_CNTTK55 on 3/7/2015.
 */
public class SV_CheckAppRunning extends Service{

    // it make thread != null ????? because set null in destroy but Log is still exist on onStartCommand
    TH_BlockAppThread block ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(block!=null)
        {
            Log.i("THREADNULL","giuyg");
            block.interrupt();
            block = null;
        }
        block = new TH_BlockAppThread(getApplicationContext(), this);
        block.start();

//        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
//        Toast.makeText(this, "onTaskRemoved", Toast.LENGTH_SHORT).show();

        block.interrupt();
        block = null;

        Intent restartService = new Intent(getApplicationContext(),
                BR_MaintainService.class);
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getBroadcast(
                getApplicationContext(), 1, restartService,
                0);

        //Restart the service once it has been killed android

        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmService.setExact(AlarmManager.RTC, System.currentTimeMillis()+100, restartServicePI);

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();

            block.interrupt();
            block = null;

        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        Log.i("DESTROY", "djibjgk"+pm.isScreenOn());
//        if(!pm.isScreenOn())
//            block.stop();
        if(pm.isScreenOn()) {
            Intent restartService = new Intent(getApplicationContext(),
                    BR_MaintainService.class);
            restartService.setPackage(getPackageName());
            PendingIntent restartServicePI = PendingIntent.getBroadcast(
                    getApplicationContext(), 1, restartService,
                    0);

            //Restart the service once it has been killed android

            AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
            alarmService.setExact(AlarmManager.RTC, System.currentTimeMillis() + 100, restartServicePI);
            Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        }
    }
}
