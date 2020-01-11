package net.blueridge.apps.resource_full;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;


/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * This class handles the creation of the database tables that are different for each device
 * and that are used throughout the program.
 * It pulls at first from a sqlite database in the asset folder.
 * It is able to update the database when the the version number in the sqlite file is higher
 * also in S$K_DBHelper the verion number is set higher then what is currently in the
 * Application it will call the update functions.
 * It only holds on to the user data so the user does not have to begin again.
 */

final class Database {



    public Database() {    }


    ////////////////////////////////////////////////////////////////////////

    public static void onCreate(SQLiteDatabase db){
        App_Users.onCreate(db);
        App_Users.createGuestUser(db);
        Log.d(Constants.LOGCAT, "App_Users.onCreate(db)");



        App_Users_Usage.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Usage.onCreate(db)");

        Settings.onCreate(db);
        Log.d(Constants.LOGCAT, "Settings.onCreate(db)");

    }


    static void addNew(SQLiteDatabase db){
        App_Users.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users.onCreate(db)");

        App_Users_Usage.onCreate(db);
        Log.d(Constants.LOGCAT, "App_Users_Usage.onCreate(db)");


        Settings.onCreate(db);
        Settings.createSettings(db);
        Log.d(Constants.LOGCAT, "Settings.onCreate(db)");

    }



    static void updateDB(SQLiteDatabase mDatabase, Context mContext, String mDBOldName){

        SQLiteDatabase mDatabaseOld;


        File dbFile = mContext.getDatabasePath(mDBOldName);

        mDatabaseOld = SQLiteDatabase.openOrCreateDatabase(dbFile,null);

        App_Users.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users.doUpDate(mDatabase, mMyDataBase);");

        App_Users_Usage.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "App_Users_Usage.doUpDate(mDatabase, mDatabaseOld);");


        Settings.doUpDate(mDatabase, mDatabaseOld);
        Log.d(Constants.LOGCAT, "Settings.doUpDate(mDatabase, mDatabaseOld);");



    }


    public static final class App_Users_Usage implements  BaseColumns{
        public App_Users_Usage(){ }



        public static final String APP_USERS_USAGE_CREATE
                ="CREATE TABLE  IF NOT EXISTS app_users_usage ( " +
                "_id INTEGER PRIMARY KEY, " +
                "app_user_id INTEGER, " +
                "usage_time_stamp TEXT, "+
                "usage_pattern TEXT )";
        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_USAGE_CREATE);
        }

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users_usage");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users_usage", null);
            if(mCursor.moveToFirst()) {
                do{
                    String mSQL="SELECT COUNT(_id) AS count_id " +
                            "FROM  app_users_usage " +
                            "WHERE _id=" + mCursor.getString(mCursor.getColumnIndex("_id"));
                    Cursor mCursorDuplicate = mDatabase.rawQuery(mSQL,null);
                    if(mCursorDuplicate.moveToFirst()){
                        if(mCursorDuplicate.getInt(mCursorDuplicate.getColumnIndex("count_id"))==0){
                            String updateSQL = "INSERT INTO app_users_usage (_id, app_user_id, " +
                                    "usage_time_stamp, usage_pattern)VALUES (";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("_id")) + ",";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("usage_time_stamp")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("usage_pattern")) + "')";
                            mDatabase.execSQL(updateSQL);
                        }
                    }
                    mCursorDuplicate.close();
                }while(mCursor.moveToNext());
            }
            mCursor.close();
        }

    }
    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   APP USERS TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class App_Users implements BaseColumns{

        public App_Users(){ }

        public static final String APP_USERS_CREATE="CREATE TABLE  IF NOT EXISTS app_users ( " +
                "_id INTEGER PRIMARY KEY, " +
                "app_user_id INTEGER, " +
                "app_user_name TEXT, " +
                "app_user_time TEXT, " +
                "app_user_fn TEXT, " +
                "app_user_ln TEXT, " +
                "app_user_gender INTEGER, " +
                "app_user_long TEXT, " +
                "app_user_lat TEXT, " +
                "app_user_device TEXT, " +
                "app_user_age INTEGER, " +
                "imei TEXT, " +
                "android_id TEXT )";


        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CREATE);
            createGuestUser(db);
        }

        //////////////////////////////////////////////////////////////////////


        public static void createGuestUser(SQLiteDatabase db){
            Cursor c=db.rawQuery("SELECT * FROM app_users WHERE app_user_id=0",null);
            if(!c.moveToFirst()){
                String mSQL="INSERT INTO app_users  (_id, app_user_id, app_user_name, " +
                        "app_user_time, app_user_fn, app_user_ln, " +
                        " app_user_gender, app_user_long, app_user_lat, " +
                        " app_user_device, app_user_age) " +
                        "VALUES (0,0, '"+Constants.GUEST_NAME+"', " +
                        "'"+ System.currentTimeMillis()+"'," +
                        "'',''," +
                        "0,'',''," +
                        "'',0)";
                db.execSQL(mSQL);

            }
            c.close();
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS app_users");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM app_users", null);
            if(mCursor.moveToFirst()) {
                do{
                    String mSQL="SELECT COUNT(_id) AS count_id " +
                            "FROM  app_users " +
                            "WHERE app_user_id=" + mCursor.getString(mCursor.getColumnIndex("_id"));
                    Cursor mCursorDuplicate = mDatabase.rawQuery(mSQL,null);
                    if(mCursorDuplicate.moveToFirst()){
                        if(mCursorDuplicate.getInt(mCursorDuplicate.getColumnIndex("count_id"))==0){
                            String updateSQL = "INSERT INTO app_users (_id, app_user_id, " +
                                    "app_user_name, app_user_time, app_user_fn, app_user_ln, " +
                                    "app_user_gender, app_user_long, app_user_lat, " +
                                    "app_user_device, app_user_age)VALUES (";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_name")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_time")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_fn")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_ln")) + "',";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_gender")) + ",";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_long")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_lat")) + "',";
                            updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_device")) + "',";
                            updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_age")) + ")";
                            mDatabase.execSQL(updateSQL);
                        }
                    }
                    mCursorDuplicate.close();
                }while(mCursor.moveToNext());
            }
            mCursor.close();
        }


    }



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *   settings TABLE
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Settings implements BaseColumns {
        public Settings(){}

        public static final String SETTINGS_CREATE="CREATE TABLE IF NOT EXISTS settings (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "settings_id INTEGER, " +
                "setting TEXT, " +
                "setting_data TEXT );";

        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(SETTINGS_CREATE);

        }

        public static void createSettings(SQLiteDatabase db){
            ContentValues initialValues = new ContentValues();

            initialValues.put("_id",1);
            initialValues.put("settings_id", 1);
            initialValues.put("setting", "sound_effects");
            initialValues.put("setting_data", "true");
            db.insert("settings", null, initialValues);

            initialValues.put("_id",2);
            initialValues.put("settings_id", 2);
            initialValues.put("setting", "create_all");
            initialValues.put("setting_data", "true");
            db.insert("settings", null, initialValues);

            initialValues.put("_id",3);
            initialValues.put("settings_id", 3);
            initialValues.put("setting", "last_upload_timestamp");
            initialValues.put("setting_data", "0");
            db.insert("settings", null, initialValues);
        }


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS settings");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM settings", null);
            if(mCursor.moveToFirst()) {
                do {
                    String updateSQL = "INSERT INTO settings " +
                            "(_id, settings_id, setting, setting_data ) " +
                            "VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("settings_id")) + ",";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("setting")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("setting_data")) + "')";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }



    //////////////////////////////////////////////////////////////////////
    /** ***************************************************
     *  Contacts
     *****************************************************/
    //////////////////////////////////////////////////////////////////////

    public static final class Contacts implements BaseColumns{

        public Contacts(){ }



        public static final String APP_USERS_CREATE="CREATE TABLE  IF NOT EXISTS Contacts ( "+
                "_id INTEGER PRIMARY KEY, " +
                "contact_id INTEGER," +
                "contact_name TEXT," +
                "contact_organization TEXT," +
                "contact_position TEXT," +
                "contact_address TEXT," +
                "contact_subaddress TEXT," +
                "contact_city TEXT," +
                "contact_state TEXT," +
                "contact_postalcode TEXT," +
                "contact_phone TEXT," +
                "contact_2nd_phone TEXT," +
                "contact_fax TEXT," +
                "contact_email TEXT," +
                "contact_web_url TEXT," +
                "contact_allow INTEGER," +
                "helpType1 TEXT," +
                "helpType2 TEXT," +
                "helpType3 TEXT," +
                "helpType4 TEXT," +
                "helpType5 TEXT," +
                "helpType6 TEXT," +
                "helpType7 TEXT," +
                "helpType8 TEXT," +
                "helpType9 TEXT," +
                "helpType10 TEXT," +
                "helpType11 TEXT," +
                "helpType12 TEXT," +
                "contact_image1 TEXT," +
                "contact_image2 TEXT," +
                "contact_image3 TEXT," +
                "contact_image1_title TEXT," +
                "contact_image2_title TEXT," +
                "contact_image3_title TEXT," +
                "deleted_user TEXT)";

        ////////////////////////////////////////////////////////////////////////

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(APP_USERS_CREATE);
        }

        //////////////////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////////////////

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS Contacts");
            onCreate(mDatabase);



            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM Contacts", null);
            if(mCursor.moveToFirst()) {
                do{


                    String updateSQL = "INSERT INTO Contacts (_id," +
                            "contact_id, " +
                            "contact_name, " +
                            "contact_organization, " +
                            "contact_position, " +
                            "contact_address, " +
                            "contact_subaddress, " +
                            "contact_city, " +
                            "contact_state, " +
                            "contact_postalcode, " +
                            "contact_phone, " +
                            "contact_2nd_phone, " +
                            "contact_fax, " +
                            "contact_email, " +
                            "contact_web_url, " +
                            "contact_allow, " +
                            "helpType1, " +
                            "helpType2, " +
                            "helpType3, " +
                            "helpType4, " +
                            "helpType5, " +
                            "helpType6, " +
                            "helpType7, " +
                            "helpType8, " +
                            "helpType9, " +
                            "helpType10, " +
                            "helpType11, " +
                            "helpType12, " +
                            "contact_image1, " +
                            "contact_image2, " +
                            "contact_image3, " +
                            "contact_image1_title, " +
                            "contact_image2_title, " +
                            "contact_image3_title, " +
                            "deleted_user" +
                            ")VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_id")) + ",";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_name")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_avatar")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_fn")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_ln")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_teacher")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_school")) + "',";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_gender")) + ",";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_long")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_lat")) + "',";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_device")) + "',";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("app_user_age")) + ",";
                    updateSQL += "'" + mCursor.getString(mCursor.getColumnIndex("app_user_grade")) + "')";
                    mDatabase.execSQL(updateSQL);

                }while(mCursor.moveToNext());
            }
            mCursor.close();
        }


    }


    public static final class HelpTypes implements BaseColumns{
        public HelpTypes (){}

        public static final String AUBU_CREATE="CREATE TABLE HelpTypes (" +
                "_id INTEGER PRIMARY KEY. " +
                "helpTypeID INTEGER. " +
                "helpTypeName TEXT. " +
                "helpType1 INTEGER. " +
                "helpType2 INTEGER. " +
                "helpType3 INTEGER. " +
                "helpType4 INTEGER. " +
                "helpType5 INTEGER)";

        public static void onCreate(SQLiteDatabase db){
            db.execSQL(AUBU_CREATE);
        }

        public static void doUpDate(SQLiteDatabase mDatabase, SQLiteDatabase mDatabaseOld){
            mDatabase.execSQL("DROP TABLE IF EXISTS HelpTypes");
            onCreate(mDatabase);

            Cursor mCursor=mDatabaseOld.rawQuery("SELECT * FROM HelpTypes", null);
            if(mCursor.moveToFirst()){
                do {
                    String updateSQL = "INSERT INTO app_users_book_user (_id, " +
                            "helpTypeID, " +
                            "helpTypeName, " +
                            "helpType1, " +
                            "helpType2, " +
                            "helpType3, " +
                            "helpType4, " +
                            "helpType5" +
                            ")VALUES (";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpTypeID")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpTypeID")) + ",";
                    updateSQL +=  "'" + mCursor.getString(mCursor.getColumnIndex("helpTypeName")) + "',";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpType1")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpType2")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpType3")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpType4")) + ",";
                    updateSQL += mCursor.getString(mCursor.getColumnIndex("helpType5")) + ")";
                    mDatabase.execSQL(updateSQL);

                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }


}
