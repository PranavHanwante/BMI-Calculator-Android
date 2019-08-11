package com.example.pranav.bmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DbHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;

    DbHandler(Context context) {


        super(context, "bmidb", null, 1);/*studentdb    DATABASE KA NAAM*/
        this.context = context;
        db = this.getWritableDatabase();

    }



    /*  extends SQLiteOpenHelper is an abstarct has 2 method onCreate,onUpgrade need to overwite that method    */


    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bmidetail(bmi text, name text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addBmi(Student s) {

        ContentValues cv = new ContentValues();
        cv.put("bmi", s.getRno());
        cv.put("name", s.getName());

        long id = db.insert("bmidetail", null, cv);

        if (id < 0)
            Toast.makeText(context, "insertt issuee", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "1 Recordd inserted", Toast.LENGTH_SHORT).show();


    }

    public String viewBmi() {
        StringBuffer sb = new StringBuffer();

        /*Result set in java is similer to cursor in android*/
        Cursor cursor = db.query("bmidetail", null, null, null, null, null, null);
        /*Colunmn name,Where condition,Where conditions ka argument*/
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String rno = cursor.getString(0);
                String name = cursor.getString(1);
                sb.append("Bmi " + rno + " Date " + name + "\n");

            }
            while (cursor.moveToNext());
        }
        return sb.toString();
    }


}
