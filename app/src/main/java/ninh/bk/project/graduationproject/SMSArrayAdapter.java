package ninh.bk.project.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nguyen Ke Ninh on 5/26/2015.
 */
public class SMSArrayAdapter extends ArrayAdapter<ItemSMS> {
    Context context=null;
    ArrayList<ItemSMS> array;
    int layoutId;
    DBHelper mydb;

    public SMSArrayAdapter(Context context, int layoutId, ArrayList<ItemSMS> array) {
        super(context, layoutId, array);
        this.context = context;
        this.layoutId = layoutId;
        this.array = array;
        mydb = new DBHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);


            holder = new RecordHolder();
            holder.address=(TextView) row.findViewById(R.id.address);
            holder.time=(TextView) row.findViewById(R.id.time);
            holder.body = (TextView)row.findViewById(R.id.body);
            holder.process = (TextView)row.findViewById(R.id.process);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        int stt12 = position+1;

        if(stt12%2==1)
//        {
//            holder.chart1.setWidth(200);
//            holder.chart1.setBackgroundColor(Color.BLUE);
            row.setBackgroundColor(Color.parseColor("#CFCFCF"));
//        }
        else
//            {
//            holder.chart1.setWidth(100);
//            holder.chart1.setBackgroundColor(Color.GREEN);
            row.setBackgroundColor(Color.parseColor("#00000000"));
//        }

        holder.address.setText(stt12 + ". " + array.get(position).getAddress());
        holder.time.setText("Time: "+array.get(position).getTime());
        holder.body.setText("Body: "+array.get(position).getBody());
        holder.process.setText("Processes: "+array.get(position).getProcess());

        return row;
    }

    static class RecordHolder {
        TextView address;
        TextView time;
        TextView body;
        TextView process;

    }

}
