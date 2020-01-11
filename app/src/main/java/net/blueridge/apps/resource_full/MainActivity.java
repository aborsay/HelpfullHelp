package net.blueridge.apps.resource_full;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

/**
 * Created by aborsay on 10/1/2018.
 */

public class MainActivity extends Master_Parent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_main);


        setBackground();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        appData.setCurrentSection(1);
        appData.setClassesForLaunch();


    }



    public void clickIcon(View view){
        int categoryTag = Integer.parseInt(view.getTag().toString());
        switch(categoryTag) {
            default:{
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:{
                appData.setCategory(categoryTag);
                appData.setNameSearch("");
                Intent main = new Intent(this,ContactPage.class);
                startActivity(main);
                finish();
                break;
            }
            case 11:{
                appData.setCategory(categoryTag);
                appData.setNameSearch("");
                Intent main = new Intent(this,InfoPage.class);
                startActivity(main);
                finish();
                break;
            }

        }
    }

    public void searchBy(View view){
        appData.setCategory(0);
        appData.setNameSearch(((EditText) findViewById(R.id.searchString)).getText().toString());
        Intent main = new Intent(this,ContactPage.class);
        startActivity(main);
        finish();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, null, null, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }
}
