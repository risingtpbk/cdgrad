package ninh.bk.project.graduationproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ninh on 19/03/2015.
 */
public class DataStat extends ActionBarActivity{

    ListView ListData;
    ArrayList<ItemData> arraydata;
    DataArrayAdapter adapter;
    AlertDialog sortDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_stat);

        final DBHelper mydb = new DBHelper(this);
        ListData = (ListView)findViewById(R.id.listData);

        arraydata = mydb.getAllData();

        Collections.sort(arraydata, new Comparator<ItemData>() {
            @Override
            public int compare(ItemData lhs, ItemData rhs) {

                return (int) (rhs.getReceived() - lhs.getReceived());
            }
        });

        //0 received, 1 sent, 2 total

        adapter = new DataArrayAdapter(this, R.layout.datalistview, arraydata, 0);
        ListData.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort) {
            final CharSequence[] items = {" Received "," Sent "," Total "};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sort list by");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    switch (item) {
                        case 0:
                        try{
                            Collections.sort(arraydata, new Comparator<ItemData>() {
                                @Override
                                public int compare(ItemData lhs, ItemData rhs) {

                                    return (int) (rhs.getReceived() - lhs.getReceived());
                                }
                            });

                            adapter = new DataArrayAdapter(DataStat.this, R.layout.datalistview, arraydata, 0);
                            ListData.setAdapter(adapter);
                            }
                            catch (Exception e)
                            {
                            }
                            break;

                        case 1:

                            try {
                                Collections.sort(arraydata, new Comparator<ItemData>() {
                                    @Override
                                    public int compare(ItemData lhs, ItemData rhs) {

                                        return (int) (rhs.getSent() - lhs.getSent());
                                    }
                                });

                                adapter = new DataArrayAdapter(DataStat.this, R.layout.datalistview, arraydata, 1);
                                ListData.setAdapter(adapter);
                            }
                            catch (Exception e)
                            {
                            }
                            break;

                        case 2:

                            try {
                                Collections.sort(arraydata, new Comparator<ItemData>() {
                                    @Override
                                    public int compare(ItemData lhs, ItemData rhs) {

                                        return (int) (rhs.getSent()+rhs.getReceived() - lhs.getSent() - lhs.getReceived());
                                    }
                                });

                                adapter = new DataArrayAdapter(DataStat.this, R.layout.datalistview, arraydata, 2);
                                ListData.setAdapter(adapter);
                            }
                            catch (Exception e)
                            {
                            }


                            break;
                    }
                    sortDialog.dismiss();
                }
            });
            sortDialog = builder.create();
            sortDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
