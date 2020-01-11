package net.blueridge.apps.resource_full;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class SplashActivityOld extends Master_Parent implements LoaderManager.LoaderCallbacks<Cursor> {
    protected Handler mSplashHandler = new Handler();
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;
    protected  ResourceFullApplication appData;

    private boolean animationStarted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(R.layout.splash_activity);
        Context context;

        setBackground();

        appData=((ResourceFullApplication) getApplicationContext());
        super.onCreate(savedInstanceState);
        appData.addToClassOrder(0);
        appData.setCurrentSection(2);
        Log.d(Constants.LOGCAT, "ResourceFull onCreate");
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader;

        switch (id) {
            case 0:
                cursorLoader =  new CursorLoader(this,
                        DBProvider.CONTENT_URI_ALL_USERS, null, null, null, null);
                break;
            default:
                cursorLoader = null;
                break;
        }
        return  cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appData.setCurrentUserID("0");
        appData.addToClassOrder(1);
        appData.addToSettings(data);

        mSplashHandler.postDelayed(runAfterSplash, 500);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private Runnable runAfterSplash = new Runnable(){
        @Override
        public void run(){
            moveForward();
            /*  */
        }
    };

    private void moveForward(){
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
        finish();
    }
}

