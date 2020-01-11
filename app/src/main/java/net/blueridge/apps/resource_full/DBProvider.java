package net.blueridge.apps.resource_full;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.*;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aborsay on 9/29/2018.
 */

public class DBProvider extends ContentProvider {
    private Context mContext;
    private boolean mSetAll;


    private SQLiteDatabase mDatabase;


    private static final int ALL_USERS = 1; //0
    private static final int INDIVIDUAL_USER = 2; //0
    private static final int USER_USAGE = 3;
    private static final int CONTACTS = 4;
    private static final int CONTACT_SEARCH = 7;
    private static final int HELP_TYPES = 5;
    private static final int SETTINGS = 6;
    private static final int DEFAULT = 99;

    private static final String AUTHORITY = "net.blueridge.apps.resource_full.DBProvider";
    public static final Uri CONTENT_URI_ALL_USERS
            = Uri.parse("content://" + AUTHORITY + "/users");
    public static final Uri CONTENT_URI_INDIVIDUAL_USER
            = Uri.parse("content://" + AUTHORITY + "/user_individual");
    public static final Uri CONTENT_URI_USER_USAGE
            = Uri.parse("content://" + AUTHORITY + "/user_usage");
    public static final Uri CONTENT_URI_CONTACTS
            = Uri.parse("content://" + AUTHORITY + "/contacts");
    public static final Uri CONTENT_URI_CONTACTS_SEARCH
            = Uri.parse("content://"+AUTHORITY +"/contact_search");
    public static final Uri CONTENT_URI_HELP_TYPES
            = Uri.parse("content://" + AUTHORITY + "/help_types");
    public static final Uri CONTENT_URI_SETTINGS
            = Uri.parse("content://" + AUTHORITY + "/settings");
    public static final Uri CONTENT_URI_DEFAULT
            = Uri.parse("content://" + AUTHORITY + "/default");



    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "users", ALL_USERS);
        uriMatcher.addURI(AUTHORITY, "user_individual", INDIVIDUAL_USER);
        uriMatcher.addURI(AUTHORITY, "user_usage", USER_USAGE);
        uriMatcher.addURI(AUTHORITY, "contacts", CONTACTS);

        uriMatcher.addURI(AUTHORITY, "contact_search", CONTACT_SEARCH);
        uriMatcher.addURI(AUTHORITY, "help_types", HELP_TYPES);
        uriMatcher.addURI(AUTHORITY, "settings", SETTINGS);
        uriMatcher.addURI(AUTHORITY, "default", DEFAULT);
    }

    public DBProvider() {
        mSetAll = false;
    }


    @Override
    public boolean onCreate() {

        mSetAll = false;
        mContext = getContext();

        Resource_DBHelper dbHelper = new Resource_DBHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();
        // mAppProcesses = new AppProviderProcesses();
        return false;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(Constants.LOGCAT, " public Cursor query");
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String mRawSQL;
        Cursor mCursor;
        ResourceFullApplication mAppData =
                ((ResourceFullApplication) mContext.getApplicationContext());
        ArrayList<HashMap<String, String>> mUserGPL;
        switch (uriMatcher.match(uri)) {
            case DEFAULT: {
                mCursor = null;
                break;
            }
            case ALL_USERS: {
                                /*
                 * ALL_USERS
                 * is called as a default in case more users are added
                 * at a later date
                 */
                queryBuilder.setTables("app_users");
                mRawSQL = "SELECT  * " +
                        "FROM app_users ";
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                // LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Location location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (mCursor.moveToFirst()) {
                    if (mCursor.getString(mCursor.getColumnIndex("app_user_device")).equals("")) {

                        mRawSQL = "UPDATE app_users " +
                                "SET app_user_device =\"" + Settings.Secure.getString(
                                mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID) + "\" " +
                                "WHERE app_user_id=0";
                        mDatabase.execSQL(mRawSQL);
                    }
                }

                mRawSQL = "SELECT  * " +
                        "FROM settings ";
                mCursor = mDatabase.rawQuery(mRawSQL, null);

                break;
            }
            case CONTACTS:{
                mRawSQL = "SELECT " +
                        "(SELECT COUNT(contact_id) as count " +
                        "FROM contacts " +
                        "WHERE helpType1 LIKE \""+ selection+"\" ) as count," +
                        "contacts.* " +
                        "FROM contacts " +
                        "WHERE deleted_user != 1 and helpType1 LIKE \""+selection+"\" " +
                        "ORDER BY contact_name_last ASC, " +
                        "contact_name_first ASC, " +
                        "contact_organization ASC " +
                        "LIMIT " + projection[0]+",9999999999";
              /*  mRawSQL = "SELECT  * FROM contacts " +
                        "WHERE helpType1 LIKE " + selection + " " +
                        "ORDER BY contact_name ASC";
                        */

                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case CONTACT_SEARCH:{
                mRawSQL = "SELECT " +
                        "(SELECT COUNT(contact_id) as count " +
                        "FROM contacts " +
                        "WHERE contact_name_last LIKE \"%"+selection+"%\"" +
                        "OR contact_name_first LIKE \"%"+selection+"%\""+
                        "OR contact_organization LIKE \"%"+selection+"%\"" +
                        ") as count," +
                        "contacts.* " +
                        "FROM contacts " +
                        "WHERE  deleted_user != 1  " +
                        "AND (contact_name_last LIKE \"%"+selection+"%\"" +
                        "OR contact_name_first LIKE \"%"+selection+"%\""+
                        "OR contact_organization LIKE \"%"+selection+"%\")" +
                        "ORDER BY contact_name_last ASC, " +
                        "contact_name_first ASC, " +
                        "contact_organization ASC " +
                        "LIMIT " + projection[0]+",9999999999";
              /*  mRawSQL = "SELECT  * FROM contacts " +
                        "WHERE helpType1 LIKE " + selection + " " +
                        "ORDER BY contact_name ASC";
                        */
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            case HELP_TYPES:{
                mRawSQL = "SELECT * FROM helpTypes WHERE helpType1 =" + projection[1] + " ";
                if(projection[2].equals("0")) {
                    mRawSQL += "AND helpType2 != 0 AND helpType3 = 0";
                }else if(Integer.valueOf(projection[2]) > 0 && projection[3].equals("0")){
                    mRawSQL += "AND helpType2 = "+projection[2] +" " +
                            "AND helpType3 != 0 AND helpType4 = 0";
                }else if(Integer.valueOf(projection[3]) > 0 && projection[4].equals("0")){
                    mRawSQL += "AND helpType2 = "+projection[2] +" " +
                            "AND helpType3 = "+projection[3]+" AND helpType4 != 0 " +
                            "AND helpType5 = 0";
                }else if(Integer.valueOf(projection[4]) > 0 && projection[5].equals("0")){
                    mRawSQL += "AND helpType2 = "+projection[2] +" " +
                            "AND helpType3 = "+projection[3]+" " +
                            "AND helpType4 = " + projection[4] + "  "+
                            "AND helpType5 != 0";
                }else{
                    mRawSQL += "AND helpType2 != 0 AND helpType3 = 0";
                }
                mCursor = mDatabase.rawQuery(mRawSQL, null);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return mCursor;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues mValues, String mWhere,
                      String[] mInfo) {
        Log.d(Constants.LOGCAT, "public int update");

        String mRawSQL;
        Cursor mCursor;
        switch (uriMatcher.match(uri)) {
            default:
                break;
        }
        return 0;
    }
}
