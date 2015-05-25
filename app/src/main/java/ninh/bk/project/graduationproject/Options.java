package ninh.bk.project.graduationproject;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NINH_CNTTK55 on 3/8/2015.
 */
public class Options extends ActionBarActivity{

    ListView listapp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon_key_alert_2);

        final DBHelper mydb = new DBHelper(this);

        listapp = (ListView)findViewById(R.id.options);


        final ArrayList<Item> arrayapp = mydb.getAllAppFullInfo();

        final MyArrayAdapter lvadapter = new MyArrayAdapter(this, R.layout.mylistview, arrayapp);
        listapp.setAdapter(lvadapter);

        listapp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1, int arg2,long arg3) {
                //System.out.println("position : "+ arg2+ "; value =" + result[arg2]);
                mydb.changeStatus(arrayapp.get(arg2).getApppackage());
                if(arrayapp.get(arg2).getStatus().equals("ON"))
                    arrayapp.get(arg2).setStatus("OFF");
                else
                    arrayapp.get(arg2).setStatus("ON");
                lvadapter.notifyDataSetChanged();

                mydb.close();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        stopService(new Intent(getBaseContext(), MyService.class));
    }

}
