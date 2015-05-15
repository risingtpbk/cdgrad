package ninh.bk.project.graduationproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ninh on 19/03/2015.
 */
public class SMSStat extends ActionBarActivity{

    ListView getSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_stat);

        DBHelper mydb = new DBHelper(this);
        getSMS = (ListView)findViewById(R.id.getSMS);

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

        ArrayList<String> ar = mydb.getSms();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);

        getSMS.setAdapter(adapter);
    }

}
