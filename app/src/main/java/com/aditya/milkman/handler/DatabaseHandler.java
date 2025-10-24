package com.aditya.milkman.handler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.aditya.milkman.model.MilkRecord;
import com.aditya.milkman.param.DatabaseParam;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, DatabaseParam.DB_NAME, null, DatabaseParam.DB_VERSION);
    }

    /** onCreate:
     * This method runs automatically by the SQLiteOpenHelper to create the table.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = String.format(
                "CREATE TABLE %s(%s VARCHAR(6) PRIMARY KEY, %s VARCHAR(30) NOT NULL, %s TEXT, %s REAL NOT NULL, %s TEXT NOT NULL)",
                DatabaseParam.TABLE_NAME,
                DatabaseParam.KEY_ID,
                DatabaseParam.KEY_NAME,
                DatabaseParam.KEY_ADDRESS,
                DatabaseParam.KEY_MILK_WANTED,
                DatabaseParam.KEY_STARTING_DATE
        );

        // Now we have to execute the query.
        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveRecord(MilkRecord milkRecord) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseParam.KEY_ID, generateId(milkRecord.getPersonName()));
        contentValues.put(DatabaseParam.KEY_NAME, milkRecord.getPersonName());
        contentValues.put(DatabaseParam.KEY_ADDRESS, milkRecord.getPersonAddress());
        contentValues.put(DatabaseParam.KEY_MILK_WANTED, milkRecord.getMilkWanted());
        contentValues.put(DatabaseParam.KEY_STARTING_DATE, milkRecord.getStartingDate());

        // Now we have to get the instance of the writable database.
        SQLiteDatabase writableDb = getWritableDatabase();
        writableDb.insert(DatabaseParam.TABLE_NAME, null, contentValues);

        // Crucial.
        writableDb.close();
    }

    private String generateId(String personName) {
        SecureRandom secureRandom = new SecureRandom();
        return personName.trim().substring(0, 3) + secureRandom.nextInt(999);
    }

    public List<MilkRecord> fetchAllRecords() {

        // Empty collection.
        List<MilkRecord> milkRecords = new ArrayList<>();

        // Getting an instance of the readable db.
        SQLiteDatabase readableDb = getReadableDatabase();
        // Preparing Read Query.
        String readQuery = String.format("SELECT * from %s", DatabaseParam.TABLE_NAME);
        @SuppressLint("Recycle")
        Cursor cursor = readableDb.rawQuery(readQuery, null);
        // Move the cursor to the starting index.
        if (cursor.moveToFirst()) {
            // Now we have to fetch the record.
            do {
                String personId = cursor.getString(0);
                String personName = cursor.getString(1);
                String personAddress = cursor.getString(2);
                float milkWanted = Float.parseFloat(cursor.getString(3));
                String startingDate = cursor.getString(4);
                milkRecords.add(MilkRecord
                        .builder()
                                .personId(personId)
                                .personName(personName)
                                .personAddress(personAddress)
                                .milkWanted(milkWanted)
                                .startingDate(startingDate)
                        .build());
            } while (cursor.moveToNext());
        }

        // Crucial.
        readableDb.close();

        return milkRecords;
    }

    public boolean isRecordExistsByName(String personName) {
        SQLiteDatabase readableDb = getReadableDatabase();
        Cursor cursor = readableDb.rawQuery("SELECT * FROM " + DatabaseParam.TABLE_NAME + " WHERE " + DatabaseParam.KEY_NAME + " = ?", new String[]{personName});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public int deleteRecord(String personId) {
        SQLiteDatabase writableDB = getWritableDatabase();
        return writableDB.delete(DatabaseParam.TABLE_NAME, "person_id = ?", new String[]{personId});
    }
}
