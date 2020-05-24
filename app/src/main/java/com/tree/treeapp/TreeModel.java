package com.tree.treeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TreeModel extends SQLiteOpenHelper {

    private static final String TAG = TreeModel.class.getSimpleName();

    private final static String DB_NAME = "tree";
    private final static String TREE_TABLE = "tree_table";
    private final static String TREE_ID = "id";
    private final static String TREE_NAME = "name";
    private final static String TREE_NICKNAME = "nickname";
    private final static String TREE_CYCLE = "cycle";
    private final static String TREE_LATITUDE = "latitude";
    private final static String TREE_LONGITUDE = "longitude";

    private static final String SQL_CREATE =
            "CREATE TABLE "+TREE_TABLE+" (" +
                    TREE_ID + " INTEGER PRIMARY KEY," +
                    TREE_NAME + " TEXT," +
                    TREE_NICKNAME + " TEXT," +
                    TREE_CYCLE + " INTEGER," +
                    TREE_LATITUDE + " TEXT," +
                    TREE_LONGITUDE + " TEXT" +
                    ")";

    public TreeModel(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<TreeItem> getTreeList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TREE_ID,
                TREE_NAME,
                TREE_NICKNAME,
                TREE_CYCLE,
                TREE_LATITUDE,
                TREE_LONGITUDE
        };

//        String[] projection = {"*"};


        String sortOrder = TREE_ID + " DESC";

        Cursor cursor = db.query(
                TREE_TABLE,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        cursor.moveToFirst();

        ArrayList<TreeItem> treeItems = new ArrayList<>();

        while (cursor.getPosition() < cursor.getCount()) {

            Log.d(TAG, "getTreeList: cursor.getColumnIndex(TREE_LATITUDE) = " + cursor.getColumnIndex(TREE_LATITUDE));
            Log.d(TAG, "getTreeList: cursor. = " + cursor.getString(5));
            Log.d(TAG, "getTreeList: cursor. = " + cursor.getString(4));
            Log.d(TAG, "getTreeList: cursor. = " + cursor.getString(3));
            Log.d(TAG, "getTreeList: cursor. = " + cursor.getString(2));
            Log.d(TAG, "getTreeList: cursor. = " + cursor.getString(1));

            String name = cursor.getString(cursor.getColumnIndex(TREE_NAME));
            String nickname = cursor.getString(cursor.getColumnIndex(TREE_NICKNAME));

            int cycle = cursor.getInt(cursor.getColumnIndex(TREE_CYCLE));
            int id = cursor.getInt(cursor.getColumnIndex(TREE_ID));

            double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(TREE_LATITUDE)));
            double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(TREE_LONGITUDE)));

            TreeItem treeItem = new TreeItem(id, name, nickname, cycle, new double[]{latitude, longitude});
            treeItems.add(treeItem);

            cursor.moveToNext();
        }

        return treeItems;
    }

    public long insert(String name, String nickname, int cycle, double[] address) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TREE_NAME, name);
        contentValues.put(TREE_NICKNAME, nickname);
        contentValues.put(TREE_CYCLE, cycle);
        contentValues.put(TREE_LATITUDE, Double.toString(address[0]));
        contentValues.put(TREE_LONGITUDE, Double.toString(address[1]));

        long id = db.insert(TREE_TABLE, null, contentValues);
        return id;
    }

    public long remove(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TREE_ID + " = ?";
        long result = db.delete(TREE_TABLE, whereClause, new String[]{Double.toString(id)});
        return result;
    }

    public void removeTable() {
        this.getReadableDatabase().execSQL("DROP TABLE " + TREE_TABLE);
    }

}
