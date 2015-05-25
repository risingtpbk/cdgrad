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
public class App_smsArrayAdapter extends ArrayAdapter<App_SMS_Item> {
    Context context=null;
    ArrayList<App_SMS_Item> array;
    int layoutId;
    DBHelper mydb;
    int width;

    public App_smsArrayAdapter(Context context, int layoutId, ArrayList<App_SMS_Item> array) {
        super(context, layoutId, array);
        this.context = context;
        this.layoutId = layoutId;
        this.array = array;
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
            holder.name=(TextView) row.findViewById(R.id.nameapp);
            holder.count=(TextView) row.findViewById(R.id.count);
            holder.chart2 = (TextView)row.findViewById(R.id.chart2);
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

        holder.name.setText(stt12 + ". " + array.get(position).getName());
        holder.count.setText(""+array.get(position).getCount());

        double part = getPartPos(position);

        holder.chart2.setWidth((int)(width * part));
        holder.chart2.setBackgroundColor(Color.parseColor("#006400"));

        return row;
    }

    private double getPartPos(int postion) {

        double total = 0;




            int thiscount = array.get(postion).getCount();
            for (int i = 0; i < array.size(); i++) {
                total = total + array.get(i).getCount();
            }

            return thiscount/total;

    }

    static class RecordHolder {
        TextView name;
        TextView count;
        TextView chart2;
    }
}
