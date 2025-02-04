package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AdvertDatabase extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "adverts";

    private static final String DB_NAME = "AdvertDatabase.db";

    public AdvertDatabase(Context context)
    {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlDB = "CREATE TABLE adverts (id TEXT PRIMARY KEY, name TEXT, phone TEXT, description TEXT, date TEXT, location TEXT, longitude DOUBLE, latitude DOUBLE, type TEXT)";
        db.execSQL(sqlDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addAdvert(Advert advert)
    {
        SQLiteDatabase sql_DB = getWritableDatabase();
        ContentValues cal = new ContentValues();
        cal.put("id", advert.getId());
        cal.put("name", advert.getName());
        cal.put("phone", advert.getPhone());
        cal.put("description", advert.getDescription());
        cal.put("date", advert.getDateString());
        cal.put("location", advert.getLocation().getName());
        cal.put("longitude", advert.getLocation().getLongitude());
        cal.put("latitude", advert.getLocation().getLatitude());
        cal.put("type", advert.getType());

        long rowId = sql_DB.insert(TABLE_NAME, null, cal);
        sql_DB.close();

        if (rowId > -1)
        {
            System.out.println("Advert Added" + rowId);
            return true;
        }
        else
        {
            System.out.println("Insert Failed | Error");
            return false;
        }
    }

    public Boolean deleteAdvert(String id) {
        SQLiteDatabase sql_DB = getWritableDatabase();
        long rowId = sql_DB.delete(TABLE_NAME, "id=?", new String[]{id});
        sql_DB.close();

        if (rowId > -1)
        {
            System.out.println("Advert Deleted" + rowId);
            return true;
        }
        else
        {
            System.out.println("Delete Failed | Error");
            return false;
        }
    }

    public Advert getAdvert(String id)
    {
        SQLiteDatabase sql_DB = this.getReadableDatabase();
        Cursor query = sql_DB.query(TABLE_NAME, new String[] {"id", "name", "phone", "description", "date", "location", "longitude", "latitude", "type"},
                "id=?", new String[]{id}, null, null, null, null);
        if (query != null)
        {
            query.moveToFirst();
        }
        Location location = new Location(query.getString(5), query.getInt(6), query.getInt(7));
        Advert advert = new Advert(query.getString(0), query.getString(1), query.getString(2), query.getString(3), query.getString(4), location, query.getString(8));
        query.close();
        sql_DB.close();
        return advert;
    }
    public List<Advert> getAllAdvert()
    {
        SQLiteDatabase sql_DB = this.getReadableDatabase();
        Cursor query = sql_DB.query(TABLE_NAME, null, null, null, null, null, null);
        List<Advert> result = new ArrayList<>();

        while(query.moveToNext())
        {
            result.add(new Advert(
                query.getString(0),
                query.getString(1),
                query.getString(2),
                query.getString(3),
                query.getString(4),
                new Location(query.getString(5), query.getDouble(6), query.getDouble(7)),
                query.getString(8)
            ));
        }
        query.close();
        sql_DB.close();
        return result;
    }
}
