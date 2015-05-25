package ninh.bk.project.graduationproject;

/**
 * Created by NINH_CNTTK55 on 3/10/2015.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Item>{
    Context context=null;
    ArrayList<Item> myArray;
    int layoutId;
    DBHelper mydb;

    public MyArrayAdapter(Context context, int layoutId, ArrayList<Item> Array) {
        super(context, layoutId, Array);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = Array;
        mydb = new DBHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {



        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);
            row.setMinimumHeight(78);


            holder = new RecordHolder();
            holder.stt = (TextView) row.findViewById(R.id.stt);
            holder.status=(TextView) row.findViewById(R.id.status);
            holder.name=(TextView) row.findViewById(R.id.name);
            holder.icon=(ImageView) row.findViewById(R.id.icon);
            row.setTag(holder);
                     } else {
                         holder = (RecordHolder) row.getTag();
                     }


        int stt12 = position+1;

        if(stt12%2==1)
            row.setBackgroundColor(Color.parseColor("#CFCFCF"));
        else
            row.setBackgroundColor(Color.parseColor("#00000000"));

        holder.stt.setText(""+stt12);
        holder.name.setText(myArray.get(position).getName());
        holder.status.setText(myArray.get(position).getStatus());
        holder.icon.setImageDrawable(myArray.get(position).getImage());

        holder.stt.setTextColor(Color.parseColor("#000077"));
        holder.name.setTextColor(Color.parseColor("#000077"));
        if(holder.status.getText().equals("ON"))
            holder.status.setTextColor(Color.parseColor("#006400"));
        else
        holder.status.setTextColor(Color.parseColor("#696969"));

        mydb.close();

        return row;
    }




    static class RecordHolder {
        TextView stt;
        TextView name;
        TextView status;
        ImageView icon;

    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 65, 65, false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }
}
