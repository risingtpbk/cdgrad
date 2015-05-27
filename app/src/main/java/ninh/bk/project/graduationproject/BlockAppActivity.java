package ninh.bk.project.graduationproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NINH_CNTTK55 on 3/8/2015.
 */
public class BlockAppActivity extends ActionBarActivity{

    DBHelper mydb;
    ListView show_premission;
    TextView show_app;
    EditText password;
    String packageapp;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    ArrayList<String> array = new ArrayList<>();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_app_activity);

        builder = new AlertDialog.Builder(BlockAppActivity.this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        packageapp = extras.getString("PACKAGE");

        mydb = new DBHelper(this);
        show_app = (TextView)findViewById(R.id.show_app);
        show_premission = (ListView)findViewById(R.id.show_permission);
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

                        array.add(requestedPermissions[k]);
//                                log.append(i+ ": "+requestedPermissions[k]+"\n");
//                                log.append("\n");
                                i++;



                        }

                    }
                    else {
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

        String w = log.toString();
        show_app.setText(w);

        Permission_listview_adapter adapter=new Permission_listview_adapter(this, R.layout.permission_listview, array);
        show_premission.setAdapter(adapter);

        show_premission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                builder.setTitle(array.get(position));
                builder.setMessage(getinfo(array.get(position)));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                    }
                });
                builder.show();
            }
        });

    }

    private String getinfo(String s) {
        PermissionInfo pinfo;
        String info;
        try {
        PackageManager pm = getPackageManager();
            pinfo = pm.getPermissionInfo(s, PackageManager.GET_META_DATA);
            info = "- " + pinfo.loadLabel(pm).toString() + "\n" + "- Description:" + "\n" + pinfo.loadDescription(pm).toString();
        } catch (Exception e) {
            return "Sorry, this permission will be updated later...";
        }

        return info;

    }

    public void confirm(View v)
    {
        if(password.getText().toString().equals(sharedpreferences.getString("pass", "")))
        {
//            mydb.changeStatus(packageapp);
            addtemporaryallow(packageapp);
            this.finish();
        }
        else {
            password.setText("");
            password.setHint("Wrong password");
        }
    }

    private void addtemporaryallow(String packageapp) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String old = sharedpreferences.getString("tempallow", "");
        if(!old.contains(packageapp))
        editor.putString("tempallow", old + packageapp + "/");
        editor.commit();
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
