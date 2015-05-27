package ninh.bk.project.graduationproject;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    DBHelper mydb;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pass = "pass";
    SharedPreferences sharedpreferences;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        Intent restartService = new Intent(getApplicationContext(),BR_AlarmRestartService.class);
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getBroadcast(getApplicationContext(), 0, restartService, 0);

        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmService.setExact(AlarmManager.RTC, System.currentTimeMillis()+100, restartServicePI);

        Intent intent = new Intent(this, SV_CheckAppRunning.class);
        startService(intent);

        Intent countdata = new Intent(getApplicationContext(),BR_CountData.class);
        countdata.setPackage(getPackageName());
        countdata.putExtra("ACTION","DONT_PLUS");
        PendingIntent countDataPI = PendingIntent.getBroadcast(getApplicationContext(), 1, countdata, 0);

        AlarmManager alarmService1 = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmService1.setExact(AlarmManager.RTC, System.currentTimeMillis()+100, countDataPI);

        Intent sms = new Intent(getApplicationContext(),BR_SMS.class);
        sms.setPackage(getPackageName());
        PendingIntent smsPI = PendingIntent.getBroadcast(getApplicationContext(), 2, sms, 0);

        AlarmManager alarmService2 = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
        alarmService2.setExact(AlarmManager.RTC, System.currentTimeMillis()+100, smsPI);


        File databaseFile = new File("/data/data/ninh.bk.project.graduationproject/databases/MyDatabase.db");
        // check if databases folder exists, if not create one and its subfolders
        if (!databaseFile.exists()) {
            getAllApplication();
            updateAllData();
            insertAllAppSentSMS();
            Log.i("DATABASE", "Already has");
        } else {
//            updateAllApplication();

        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(!sharedpreferences.contains("pass"))
        {
            Intent i = new Intent(this, CreatePassword.class);
            startActivity(i);
        }
    }

    private void updateAllData() {
        final PackageManager pm = getPackageManager();
        // get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        // loop through the list of installed packages and see if the selected
        // app is in the list
        for (ApplicationInfo packageInfo : packages) {

            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                    !pm.getLaunchIntentForPackage(packageInfo.packageName).equals("")&&
                    hasperInternetSent(pm,packageInfo.packageName)
                    )
            {

                String package_name = packageInfo.packageName;

                mydb.insertData(package_name, 0, 0, 0, 0);

            }
        }
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

    private void insertAllAppSentSMS() {
        final PackageManager pm = getPackageManager();
        // get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        // loop through the list of installed packages and see if the selected
        // app is in the list
        for (ApplicationInfo packageInfo : packages) {

            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                    !pm.getLaunchIntentForPackage(packageInfo.packageName).equals("")&&
                    hasperSMSSent(pm,packageInfo.packageName)
                    )
            {

                String package_name = packageInfo.packageName;

                mydb.insertAppSentSMS(package_name, 0);

            }
        }
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

    private void updateAllApplication() {
        //        int i=1;
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        ArrayList<String> listAllAppPackage = mydb.getAllAppPackage();
        ArrayList<String> listAllCurrentAppPackage = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages)
        {
//			check if name is null
            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                    !pm.getLaunchIntentForPackage(packageInfo.packageName).equals(""))

            {
//                Log.d(""+i, pm.getApplicationLabel(packageInfo)+";"+ packageInfo.packageName);
//                i++;


                String name = pm.getApplicationLabel(packageInfo).toString();
                String apppackage = packageInfo.packageName;

                listAllCurrentAppPackage.add(apppackage);

                if (!listAllAppPackage.contains(apppackage))
                    mydb.insertApp(name, apppackage, 0);

            }

        }

                for(int i = 0; i <listAllAppPackage.size(); i++)
                {
                    if(!listAllCurrentAppPackage.contains(listAllAppPackage.get(i)))
                        mydb.deleteApp(i);
                }

        mydb.close();
    }


    private void getAllApplication() {

//        int i=1;
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages)
        {
//			check if name is null
            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                    !pm.getLaunchIntentForPackage(packageInfo.packageName).equals(""))

            {
//                Log.d(""+i, pm.getApplicationLabel(packageInfo)+";"+ packageInfo.packageName);
//                i++;
                String name = pm.getApplicationLabel(packageInfo).toString();
                String apppackage = packageInfo.packageName;
                mydb.insertApp(name, apppackage, 0);
            }

        }

        mydb.close();

    }


    public void statistics(View v)
    {
        Intent i = new Intent(this,Statistics.class);
        startActivity(i);
    }

    public void options(View v)
    {
        Intent i = new Intent(this, Options.class);
        startActivity(i);
    }

    public void exit(View v)
    {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Toast.makeText(this, "STS Destroy", Toast.LENGTH_SHORT).show();
//        stopService(new Intent(getBaseContext(), MyService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SMSMS","pause");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.setting) {

        }

        return super.onOptionsItemSelected(item);
    }

}
