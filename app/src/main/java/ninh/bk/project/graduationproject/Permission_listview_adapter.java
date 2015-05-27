package ninh.bk.project.graduationproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nguyen Ke Ninh on 5/28/2015.
 */
public class Permission_listview_adapter extends ArrayAdapter<String> {

    Context context=null;
    ArrayList<String> array;
    int layoutId;

    public Permission_listview_adapter(Context context, int layoutId, ArrayList<String> array) {
        super(context, layoutId, array);
        this.context = context;
        this.layoutId = layoutId;
        this.array = array;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);


            holder = new RecordHolder();
            holder.item=(TextView) row.findViewById(R.id.item);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        int stt12 = position+1;

        holder.item.setText(stt12 + ". " + array.get(position));


        return row;
    }

    static class RecordHolder {
        TextView item;
    }
}
