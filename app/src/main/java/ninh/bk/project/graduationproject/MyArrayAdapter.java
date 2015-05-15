package ninh.bk.project.graduationproject;

/**
 * Created by NINH_CNTTK55 on 3/10/2015.
 */
import java.util.ArrayList;

import android.app.Activity;
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

public class MyArrayAdapter extends ArrayAdapter<String>{
    Activity context=null;
    ArrayList<String> myArray;
    int layoutId;
    DBHelper mydb;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<String> Array) {
        super(context, layoutId, Array);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = Array;
        mydb = new DBHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        convertView.setMinimumHeight(78);
        final TextView stt=(TextView) convertView.findViewById(R.id.stt);
        final TextView status=(TextView) convertView.findViewById(R.id.status);
        final TextView name=(TextView) convertView.findViewById(R.id.name);
        final ImageView icon=(ImageView) convertView.findViewById(R.id.icon);


        int stt12 = position+1;
        stt.setText(""+stt12);
        name.setText(myArray.get(position));


        if(mydb.getStatus(mydb.getPackage(myArray.get(position)))==0) status.setText("OFF");
        else  status.setText("ON");

        try {
            Drawable image = resize(context.getPackageManager().getApplicationIcon(mydb.getPackage(myArray.get(position))));
            icon.setImageDrawable(image);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        stt.setTextColor(Color.parseColor("#000077"));
        name.setTextColor(Color.parseColor("#000077"));
        status.setTextColor(Color.parseColor("#000077"));
        if (position%2==1)
        {
            convertView.setBackgroundColor(Color.parseColor("#CFCFCF"));
        }

        mydb.close();

        return convertView;
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
