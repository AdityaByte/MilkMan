package com.aditya.milkman.param;

public class DatabaseParam {
    public static final String DB_NAME = "milkman_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "person";

    // Now we have to define the column names.
    public static final String KEY_ID = "person_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_ADDRESS = "person_address";
    public static final String KEY_MILK_WANTED = "milk_wanted";
    public static final String KEY_STARTING_DATE = "starting_date";
}
