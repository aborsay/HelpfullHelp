package net.blueridge.apps.resource_full;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/12/2015.
 *
 * This class handles the connection to the database, the create of the in app database
 * and all updating of the database. It work with class Database to add required tables not included
 * in general build.
 * It tracks version of the database to update if needed without losing userdata
 *
 * Used and accessed only by AppProvider
 */
public class Resource_DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION=13;
    private static final String DB_NAME="resource_full.sqlite";
    private static final String DB_NAME_OLD="resource_full.old.sqlite";
    private static	String DB_PATH = null;

    private static final String DB_NAME_TEMP="resource_full.new.sqlite";
    private static	String DB_PATH_TEMP = null;


    private final Context myContext;


    public Resource_DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Database.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(Constants.LOGCAT, "upgrade database please");
    }




    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {}





    /**
     * getWritableDatabase()
     * This is the most important function in this class as it is called by AppProvider
     * whenever the application is started in order to connect to the database properly.
     * It returns the connection to AppProvider so the application can access
     * and use the writable database
     * @return SQLiteDatabase
     */
    @Override
    public SQLiteDatabase getWritableDatabase(){
        ResourceFullApplication appData =
                ((ResourceFullApplication) myContext.getApplicationContext());
        File dbFile = myContext.getDatabasePath(DB_NAME);
        SQLiteDatabase mDB;

        if (!dbFile.exists()) {
            try {
                SQLiteDatabase checkDB = myContext.openOrCreateDatabase(DB_NAME,
                        Context.MODE_PRIVATE, null);
                if(checkDB != null){
                    checkDB.close();
                }

                copyDataBase(dbFile);

                mDB= SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                        SQLiteDatabase.OPEN_READWRITE);
                moveDatabaseFile(mDB);

                mDB.setVersion(1);
                appData.setDatabaseVersion(1);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        else{
            if(appData.getDatabaseVersion() < DB_VERSION) {
                mDB = SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                        SQLiteDatabase.OPEN_READWRITE);

                Log.d(Constants.LOGCAT, "mDB.getVersion(): " + mDB.getVersion());
                Log.d(Constants.LOGCAT, "DB_VERSION: " + DB_VERSION);
                appData.setDatabaseVersion(mDB.getVersion());
                mDB.close();
                if (appData.getDatabaseVersion() < DB_VERSION) {
                    Log.d(Constants.LOGCAT, "needs to change");
                    DB_PATH = "/data/data/" + myContext.getPackageName() + "/" + "databases/";
                    File to = new File(DB_PATH, DB_NAME_OLD);

                    dbFile.renameTo(to);
                    dbFile = myContext.getDatabasePath(DB_NAME);
                    if (!dbFile.exists()) {
                        try {
                            SQLiteDatabase checkDB = myContext.openOrCreateDatabase(DB_NAME,
                                    myContext.MODE_PRIVATE, null);
                            if (checkDB != null) {
                                checkDB.close();
                            }
                            copyDataBase(dbFile);

                            mDB = SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                                    SQLiteDatabase.OPEN_READWRITE);
                            //  moveDatabaseFile(mDB);
                            placeOldDatabaseFiles(mDB);
                            mDB.setVersion(DB_VERSION);
                            mDB.close();


                        } catch (IOException e) {
                            throw new RuntimeException("Error creating source database", e);
                        }
                    }
                }
            }

        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void placeOldDatabaseFiles(SQLiteDatabase mDatabase) {
        Database.updateDB(mDatabase, myContext, DB_NAME_OLD);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void moveDatabaseFile(SQLiteDatabase db){
        Database.addNew(db);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    private void copyDataBase(File dbFile) throws IOException {
        InputStream is = myContext.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}
