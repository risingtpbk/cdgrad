package ninh.bk.project.graduationproject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninh on 19/03/2015.
 */
public class DataStat extends ActionBarActivity{

    ListView ListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_stat);

        final DBHelper mydb = new DBHelper(this);
        ListData = (ListView)findViewById(R.id.listData);

        final ArrayList<String> arraydata = mydb.getAllData();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arraydata);
        ListData.setAdapter(adapter);

    }

}
