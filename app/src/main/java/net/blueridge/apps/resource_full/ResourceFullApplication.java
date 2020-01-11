package net.blueridge.apps.resource_full;

import android.app.Application;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aborsay on 10/1/2018.
 */

public class ResourceFullApplication extends Application {
    private ArrayList<HashMap<String,String>> mSettings;
    private int mUpdateType=0;
    private ArrayList<Integer> mClassOrder;

    private int mCurrentSection=0;
    private String mCurrentUserID;

    private int categoryID;
    private String mNameSearch = "";
    private String mCurrentSearch ="01|%";
    private int mDatabaseVersion=0;

    private String mRawSQL ="";
    public ResourceFullApplication() {
        Log.d(Constants.LOGCAT, "Starting Resource Full Application");

    }

    @Override
    public void onCreate() {
        super.onCreate();


        /*
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
*/

        mClassOrder=new ArrayList<>();
      //  mCurrentActivityName="SplashPage";

        /*
         * The fonts are statically set here. Currently the Italic and the Italic Bold are
         * set but never used in the app. Even despite that being unused data it
         * must remain in case it is ever in use.
         */
/*
        mCurrentFontType= Typeface.createFromAsset(getAssets(),
                "fonts/Convergence-Regular.ttf");
//               "fonts/Viga-Regular.ttf");/* "fonts/AndikaNewBasic.ttf");*/
/*
        mNumberFontTypeface=Typeface.createFromAsset(getAssets(),
                "fonts/primer_print_bold.ttf"); //this is for the numbers

*/
        mCurrentSection=0;
        Log.d(Constants.LOGCAT, "Creating Motoli Application");
    }


    public void setRawSQL(String mRawSQL){
        this.mRawSQL = mRawSQL;
    }
    public String getRawSQL(){
        return this.mRawSQL;
    }

    public int getDatabaseVersion(){
        return mDatabaseVersion;
    }

    public void setDatabaseVersion(int mDatabaseVersion){
        this.mDatabaseVersion = mDatabaseVersion;
    }

    public void setSearch(String currentSearch){
        this.mCurrentSearch = currentSearch;
    }

    public String getSearch(){
        return this.mCurrentSearch;
    }
    public void setCategory(int categoryID){
        this.categoryID = categoryID;
    }

    public int getCategoryID(){
        return  this.categoryID;
    }
    /****************************************************************************
     * addToClassOrder(Integer classNumber)
     * This is used to help the app move to correct activities.
     * Each activity sets this to a stack list
     * @param classNumber Sets stack number. Usually is 5 for most general activities
     */
    public void addToClassOrder(Integer classNumber){
        mClassOrder.add(classNumber);
    }

    /****************************************************************************
     * getCurrentSection()
     * helps to move Launch_Platform screen back to location where the person had last scrolled to
     * @return mCurrentSection
     */
    public int getCurrentSection(){
        return mCurrentSection;
    }
    /****************************************************************************
     * setCurrentSection(int mSection)
     * is used to help Activities_Platform set the section so the screen in Launch_Platform will
     * go back to previous location
     * @param   mSection    Current section app is in
     */
    public void setCurrentSection(int mSection){
        mCurrentSection=mSection;
    }
    /****************************************************************************
     * setCurrentUserID(String mCurrentUserID)
     * This is to set the mCurrentUserID to be used throughout the app.
     * The default is current 0 but in case we ever decide to add the ability
     * to chose between different users this user id must remain
     * @param mCurrentUserID sets current ID (0)
     */
    public void setCurrentUserID(String mCurrentUserID){
        this.mCurrentUserID=mCurrentUserID;

    }

    public  void setNameSearch(String mNameSearch){
        this.mNameSearch = mNameSearch;
    }

    public String getNameSearch(){
        return this.mNameSearch;
    }
    /****************************************************************************
     * void  setClassesForLaunch()
     * Used when the app starts in order to allow proper movement with back button
     * This moves past the stack for users currently and goes right to Launch_Platform order
     */
    public void setClassesForLaunch(){
        mClassOrder.clear();
        mClassOrder=new ArrayList<>();
        mClassOrder.add(0);
        mClassOrder.add(1);
        mClassOrder.add(2);
    }
    /**
     * Is only called in Motoli_Front to update setting when application is started
     * @param mData setting data
     */
    public void addToSettings(Cursor mData){
        mSettings = new ArrayList<>();

        if(mData.moveToFirst()){
            do{
                mSettings.add(new HashMap<String, String>());
                mSettings.get(mSettings.size()-1).put("settings_id",
                        mData.getString(mData.getColumnIndex("settings_id")));
                mSettings.get(mSettings.size()-1).put("setting",
                        mData.getString(mData.getColumnIndex("setting")));
                mSettings.get(mSettings.size()-1).put("setting_data",
                        mData.getString(mData.getColumnIndex("setting_data")));

            }while(mData.moveToNext());
        }
    }
}
