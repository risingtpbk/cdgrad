package ninh.bk.project.graduationproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ninh on 19/03/2015.
 */
public class SMSStat extends ActionBarActivity{

    ListView getSMS;
    ListView appSent;
    private Menu menu;
    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_stat);

        DBHelper mydb = new DBHelper(this);
        getSMS = (ListView)findViewById(R.id.getSMS);
        appSent = (ListView)findViewById(R.id.AppSentData);
        getSupportActionBar().setTitle("App Statistics");
//		[_id, thread_id, address, person, date, date_sent, protocol,
//		 read, status, type, reply_path_present, subject, body, service_center,
//		 locked, sub_id, error_code, seen, si_or_id, group_id, imsi]

//        Uri SMS_INBOX = Uri.parse("content://sms");
//        Cursor c = getContentResolver().query(SMS_INBOX, null, null, null, null);
//        Log.i("COLUMNS", Arrays.toString(c.getColumnNames()));

 /*       Uri mSmsinboxQueryUri = Uri.parse("content://sms/sent");
        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,
                new String[] { "_id", "thread_id", "address", "person", "date", "date_sent", "protocol",
                        "read", "status", "type", "reply_path_present", "subject", "body", "service_center",
                        "locked", "sub_id", "error_code", "seen", "si_or_id", "group_id", "imsi"}, null, null, null);*/

        ArrayList<ItemSMS> ar = mydb.getSms();
        Collections.sort(ar, new Comparator<ItemSMS>() {
            @Override
            public int compare(ItemSMS lhs, ItemSMS rhs) {

                return (rhs.getTime().compareToIgnoreCase(lhs.getTime()));
            }
        });
        final SMSArrayAdapter adapter = new SMSArrayAdapter(this, R.layout.sms_listview, ar);
        getSMS.setAdapter(adapter);

        ArrayList<App_SMS_Item> ar2 = mydb.getAllAppSentSMS();

        Collections.sort(ar2, new Comparator<App_SMS_Item>() {
            @Override
            public int compare(App_SMS_Item lhs, App_SMS_Item rhs) {

                return (int) (rhs.getCount() - lhs.getCount());
            }
        });

        final App_smsArrayAdapter adapter2 = new App_smsArrayAdapter(this, R.layout.app_sms_listview, ar2);
        appSent.setAdapter(adapter2);

        getSMS.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sms, menu);
        this.menu = menu;
        menuItem = menu.findItem(R.id.next);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {

            if(appSent.isShown()) {
                appSent.setVisibility(View.INVISIBLE);
                getSMS.setVisibility(View.VISIBLE);
                menuItem.setIcon(R.drawable.ic_action_previous_item);
                getSupportActionBar().setTitle("SMS Sent Info");
            }
            else
            {
                appSent.setVisibility(View.VISIBLE);
                getSMS.setVisibility(View.INVISIBLE);
                menuItem.setIcon(R.drawable.ic_action_next_item);
                getSupportActionBar().setTitle("App Statistics");
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
