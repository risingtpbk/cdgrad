package ninh.bk.project.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Nguyen Ke Ninh on 5/25/2015.
 */
public class DataArrayAdapter extends ArrayAdapter<ItemData> {
    Context context=null;
    ArrayList<ItemData> array;
    int layoutId;
    DBHelper mydb;
    int typesort;
    int width;
    DecimalFormat df = new DecimalFormat("0.##");
    public DataArrayAdapter(Context context, int layoutId, ArrayList<ItemData> array, int typesort) {
        super(context, layoutId, array);
        this.context = context;
        this.layoutId = layoutId;
        this.array = array;
        this.typesort = typesort;
        mydb = new DBHelper(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        this.width = display.getWidth();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);


            holder = new RecordHolder();
            holder.name=(TextView) row.findViewById(R.id.name1);
            holder.sent=(TextView) row.findViewById(R.id.sent);
            holder.received=(TextView)row.findViewById(R.id.received);
            holder.percent=(TextView)row.findViewById(R.id.percent);
            holder.chart1 = (TextView)row.findViewById(R.id.chart1);
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

        holder.name.setText(stt12 + "." + array.get(position).getName());
        holder.sent.setText("Sent: " + df.format(array.get(position).getSent()) + " KB");
        holder.received.setText("Received: "+df.format(array.get(position).getReceived()) + " KB");

        String per = calPer(position);
        holder.percent.setText(per + " %");
        holder.chart1.setWidth((int)((width * Float.parseFloat(per))/100));
        holder.chart1.setBackgroundColor(Color.parseColor("#006400"));

        return row;
    }

    private String calPer(int postion) {

        double total = 0;

        if(typesort == 0)
        {
            double thisposreceived = array.get(postion).getReceived();
            for (int i = 0; i < array.size(); i++) {
                total = total + array.get(i).getReceived();
            }

            String percent = df.format(thisposreceived * 100 / total);
            return percent;
        }

        if(typesort == 1)
        {
            double thispossent = array.get(postion).getSent();
            for (int i = 0; i < array.size(); i++) {
                total = total + array.get(i).getSent();
            }

            String percent = df.format(thispossent * 100 / total);
            return percent;
        }

        if(typesort == 2) {
            double thispostotal = array.get(postion).getSent() + array.get(postion).getReceived();
            for (int i = 0; i < array.size(); i++) {
                total = total + array.get(i).getSent() + array.get(i).getReceived();
            }

            String percent = df.format(thispostotal * 100 / total);
            return percent;
        }





        return "0";
    }


    static class RecordHolder {
        TextView name;
        TextView sent;
        TextView received;
        TextView percent;
        TextView chart1;

    }

}
