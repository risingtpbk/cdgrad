package ninh.bk.project.graduationproject;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import java.util.List;

/**
 * Created by NINH_CNTTK55 on 3/7/2015.
 */
public class TH_BlockAppThread extends Thread{

    Context context = null;
    Service service = null;
    DBHelper mydb;


    public TH_BlockAppThread(Context con, Service service){
        this.context = con;
        this.service = service;

        mydb = new DBHelper(context);
    }



    public void run() {


        while(true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }

            try {

                Log.i("TASK", "===============================");
                // Using ACTIVITY_SERVICE with getSystemService(String)
                // to retrieve a ActivityManager for interacting with the global system state.

                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> alltasks = am.getRunningTasks(1);

                for (int i1 = 0; i1 < alltasks.size(); i1++) {
                    ActivityManager.RunningTaskInfo aTask = alltasks.get(i1);

//                    Log.i("TASK", "===============================");
//
//                    Log.i("TASK", "aTask.baseActivity: "
//                            + aTask.baseActivity.flattenToShortString());
//
//                    Log.i("TASK", "aTask.baseActivity: "
//                            + aTask.baseActivity.getClassName());
//
//                    Log.i("TASK", "aTask.topActivity: "
//                            + aTask.topActivity.flattenToShortString());
//
//                    Log.i("TASK", "aTask.topActivity: "
//                            + aTask.topActivity.getClassName());
//
//                    Log.i("TASK", "===============================");


                    if (!aTask.topActivity.getClassName().equals(context.getPackageName() + ".BlockAppActivity")) {

                        if (mydb.getStatus(aTask.topActivity.getPackageName()) == 1) {
                            Intent i = new Intent(service, BlockAppActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("PACKAGE", aTask.topActivity.getPackageName());
                            service.startActivity(i);
                            mydb.close();
                        }
                    }
                }

            } catch (Throwable t) {
                Log.i("TASK121", "Throwable caughtkkkkkkkkkkkkkkkkkkkkk: "+ t.getMessage(), t);
            }

        }

    }

}
