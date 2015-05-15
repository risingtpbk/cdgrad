package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Created by NINH_CNTTK55 on 3/10/2015.
 */
public class BR_MaintainService extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent data) {

    try {
            Intent intent = new Intent(context, SV_CheckAppRunning.class);
            context.startService(intent);
        Log.i("STT", "" + isMyServiceRunning(context));
    }
    catch (Exception e)
    {
        e.printStackTrace();
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



}



