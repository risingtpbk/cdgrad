package ninh.bk.project.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by NINH_CNTTK55 on 3/6/2015.
 */
public class Statistics extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

    }

    public void checkSMS(View v)
    {
        Intent i = new Intent(this, SMSStat.class);
        startActivity(i);
    }

    public void checkData(View v)
    {
        Intent i = new Intent(this, DataStat.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        stopService(new Intent(getBaseContext(), MyService.class));
    }
}
