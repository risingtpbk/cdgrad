package ninh.bk.project.graduationproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by NINH_CNTTK55 on 3/9/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabase.db";
    public static final String TABLE_NAME = "appinfo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_APPPACKAGE = "apppackage";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_OLDSENT = "oldsent";
    public static final String COLUMN_OLDRECEIVED = "oldreceived";
    public static final String COLUMN_THISSENT = "thissent";
    public static final String COLUMN_THISRECEIVED = "thisreceived";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_PROCESS = "process";
    public static final String COLUMN_COUNT = "count";


    Context context;


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table appinfo " +"(id integer primary key, name text, apppackage text, status integer)"
        );

        db.execSQL(
                "create table datainfo " +"(id integer primary key, apppackage text, oldsent double, oldreceived double, thissent double, thisreceived double)"
        );

        db.execSQL(
                "create table smsinfo " +"(id integer primary key, address text, date text, body text, process text)"
        );

        db.execSQL(
                "create table appsentsms " +"(id integer primary key, apppackage text, count integer)"
        );

    }

    public boolean insertSms  (String address, String date, String body, String process)
    {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("address", address);
            contentValues.put("date", date);
            contentValues.put("body", body);
            contentValues.put("process",process);

            db.insert("smsinfo", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList getSms()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from smsinfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                String address = res.getString(res.getColumnIndex(COLUMN_ADDRESS));
                String date = res.getString(res.getColumnIndex(COLUMN_DATE));
                String body = res.getString(res.getColumnIndex(COLUMN_BODY));
                String process = res.getString(res.getColumnIndex(COLUMN_PROCESS));


                array_list.add(
                        new ItemSMS(address, date, body, process)
                );

                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }

    public boolean insertApp  (String name, String apppackage, int status)
    {
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("apppackage", apppackage);
        contentValues.put("status", status);

        db.insert("appinfo", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }


    public boolean updateApp (Integer id, String name, String apppackage, int status)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("apppackage", apppackage);
            contentValues.put("status", status);

            db.update("appinfo", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public void insertAppSentSMS (String apppackage, int count)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("apppackage", apppackage);
            contentValues.put("count", count);

            db.insert("appsentsms", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateAppSentSMS(String apppackage, int count) {

        int id = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select id from appsentsms where apppackage='" + apppackage + "'", null);

            res.moveToFirst();
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("count", count);

            db.update("appsentsms", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public ArrayList getAllAppSentSMS()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from appsentsms", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                String name = getAppName(res.getString(res.getColumnIndex(COLUMN_APPPACKAGE)));
                int count = res.getInt(res.getColumnIndex(COLUMN_COUNT));

                array_list.add(
                        new App_SMS_Item(name, count)
                        );
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }

    public void insertData (String apppackage, double oldsent, double oldreceived, double thissent, double thisreceived)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("apppackage", apppackage);
            contentValues.put("oldsent", oldsent);
            contentValues.put("oldreceived", oldreceived);
            contentValues.put("thissent", thissent);
            contentValues.put("thisreceived", thisreceived);

            db.insert("datainfo", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList getAllData()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from datainfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                double oldsent = res.getDouble(res.getColumnIndex(COLUMN_OLDSENT));
                double oldreceived = res.getDouble(res.getColumnIndex(COLUMN_OLDRECEIVED));
                double thissent = res.getDouble(res.getColumnIndex(COLUMN_THISSENT));
                double thisreceived = res.getDouble(res.getColumnIndex(COLUMN_THISRECEIVED));

                double sent = oldsent + thissent;
                double received = oldreceived + thisreceived;

                array_list.add(
                        new ItemData(getAppName(res.getString(res.getColumnIndex(COLUMN_APPPACKAGE))),
                                sent, received));
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }



    public boolean updateStatus (Integer id, int status)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", status);

            db.update("appinfo", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }



    public Integer deleteApp (Integer id)
    {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return db.delete("appinfo", "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList getAllApp()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select name from appinfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }

    public ArrayList<Item> getAllAppFullInfo()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select name, apppackage, status from appinfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                String name = res.getString(res.getColumnIndex(COLUMN_NAME));
                String apppackage = res.getString(res.getColumnIndex(COLUMN_APPPACKAGE));
                String status;
                if (res.getInt(res.getColumnIndex(COLUMN_STATUS)) == 0) status = "OFF";
                else status = "ON";

                array_list.add(new Item(name, apppackage, context.getResources().getDrawable( R.mipmap.ic_launcher), status));
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }

    public ArrayList getAllAppPackage()
    {
        ArrayList array_list = null;
        try {
            array_list = new ArrayList();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select apppackage from appinfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                array_list.add(res.getString(res.getColumnIndex(COLUMN_APPPACKAGE)));
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }

    public String getPackage(String appname)
    {
        String name = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select apppackage from appinfo where name = \"" + appname + "\"", null);
            res.moveToFirst();
            name = res.getString(res.getColumnIndex(COLUMN_APPPACKAGE));
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return name;
    }

    public int getStatus(String apppackage)
    {
        int status = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select status from appinfo where apppackage = '" + apppackage + "'", null);
            res.moveToFirst();
            status = res.getInt(res.getColumnIndex(COLUMN_STATUS));
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public void changeStatus(String apppackage)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Cursor res = db.rawQuery("select id, status from appinfo where apppackage='" + apppackage + "'", null);

            res.moveToFirst();
            Integer id = res.getInt(res.getColumnIndex(COLUMN_ID));

            Log.i("ID", "" + id);
            int status = res.getInt(res.getColumnIndex(COLUMN_STATUS));
            Log.i("STATUS", "" + status);
            int statusChanged;

            if (status == 1) statusChanged = 0;
            else statusChanged = 1;
            Log.i("STATUSCHANGED", "" + statusChanged);

            updateStatus(id, statusChanged);
//        contentValues.put("status", statusChanged);
//
//        db.update("appinfo", contentValues, "id = ?", new String[] {Integer.toString(id) } );
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String getAppName(String apppackage)
    {
        String appname = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select name from appinfo where apppackage='" + apppackage + "'", null);

            res.moveToFirst();
            appname = res.getString(res.getColumnIndex(COLUMN_NAME));
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return appname;
    }

    public int numberOfRows(){
        int numRows = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return numRows;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS appinfo");
        onCreate(db);
    }

    public void updateData(String package_name, double thissent, double thisreceived) {

        int id = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select id from datainfo where apppackage='" + package_name + "'", null);

            res.moveToFirst();
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

            try {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("thissent", thissent);
                contentValues.put("thisreceived", thisreceived);

                db.update("datainfo", contentValues, "id = ? ", new String[]{Integer.toString(id)});
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

    }

    public void updateDataAfterBoot() {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from datainfo", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {

                int id = res.getInt(res.getColumnIndex(COLUMN_ID));
                double oldsent = res.getDouble(res.getColumnIndex(COLUMN_OLDSENT));
                double oldreceived = res.getDouble(res.getColumnIndex(COLUMN_OLDRECEIVED));
                double thissent = res.getDouble(res.getColumnIndex(COLUMN_THISSENT));
                double thisreceived = res.getDouble(res.getColumnIndex(COLUMN_THISRECEIVED));

                oldsent = oldsent + thissent;
                oldreceived = oldreceived + thisreceived;

                    SQLiteDatabase db1 = this.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("oldsent", oldsent);
                    contentValues.put("oldreceived", oldreceived);
                    contentValues.put("thissent", 0);
                    contentValues.put("thisreceived", 0);

                    db1.update("datainfo", contentValues, "id = ? ", new String[]{Integer.toString(id)});


                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public int getCount(String apppackage) {

            int count = 0;
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor res = db.rawQuery("select count from appsentsms where apppackage='" + apppackage + "'", null);

                res.moveToFirst();
                count = res.getInt(res.getColumnIndex(COLUMN_COUNT));
                res.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        return count;
    }
}
