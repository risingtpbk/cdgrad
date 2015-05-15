package ninh.bk.project.graduationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by NINH_CNTTK55 on 3/8/2015.
 */
public class BlockAppActivity extends ActionBarActivity{

    DBHelper mydb;
    TextView show_premission;
    EditText password;
    String packageapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_app_activity);


        Bundle extras = getIntent().getExtras();
        packageapp = extras.getString("PACKAGE");

        mydb = new DBHelper(this);
        show_premission = (TextView)findViewById(R.id.show_permission);
        password = (EditText)findViewById(R.id.password);

        final StringBuilder log = new StringBuilder();

        int i =1;
        final PackageManager pm = getPackageManager();

            try {
                    PackageInfo packageInfo1 = pm.getPackageInfo(packageapp, PackageManager.GET_PERMISSIONS);

                    //Get Permissions
                    String[] requestedPermissions = packageInfo1.requestedPermissions;

                ApplicationInfo appinfo = pm.getApplicationInfo(packageapp, PackageManager.GET_PERMISSIONS);



                                log.append("- Application :"   + pm.getApplicationLabel(appinfo)+"\n");
                                log.append("- Package :" + packageapp+"\n");
//                                log.append("- Launch Intent For Package :"   +  pm.getLaunchIntentForPackage(MyService.currentApp)+"\n");
                                log.append("- Permission: " + "\n");
                if(requestedPermissions != null) {
                    for (int k = 0; k < requestedPermissions.length; k++) {
                                log.append(i+ ": "+requestedPermissions[k]+"\n");
                                log.append("\n");
                                i++;



                        }

                    }
                    else {
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }



            String w = log.toString();
            show_premission.setText(w);


    }

    public void confirm(View v)
    {
        if(password.getText().toString().equals("123"))
        {
            mydb.changeStatus(packageapp);
            this.finish();
        }
        else
            password.setHint("Wrong password, let try again");
    }

    public void exit(View v)
    {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        stopService(new Intent(getBaseContext(), MyService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            getSupportActionBar().setTitle(mydb.getAppName(packageapp));
        }
        catch (Exception er)
        {
            er.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        this.finish();
    }
}
