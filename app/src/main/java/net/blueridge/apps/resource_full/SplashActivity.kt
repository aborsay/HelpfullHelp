package net.blueridge.apps.resource_full

import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import android.util.Log
import android.view.View

class SplashActivity : Master_Parent(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mSplashHandler = Handler()
    protected var appData: ResourceFullApplication

    private val animationStarted = false

    private val runAfterSplash = Runnable {
        moveForward()
        /*  */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setContentView(R.layout.splash_activity)
        val context: Context

        setBackground()

        appData = applicationContext as ResourceFullApplication
        super.onCreate(savedInstanceState)
        appData.addToClassOrder(0)
        appData.setCurrentSection(2)
        Log.d(Constants.LOGCAT, "ResourceFull onCreate")
        loaderManager.initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<Cursor>? {
        val cursorLoader: CursorLoader?

        cursorLoader = if (id==0)
            CursorLoader(this, DBProvider.CONTENT_URI_ALL_USERS, null, null, null, null)
        else null

        return cursorLoader
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        appData.setCurrentUserID("0")
        appData.addToClassOrder(1)
        appData.addToSettings(data)

        mSplashHandler.postDelayed(runAfterSplash, 500)

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {}

    private fun moveForward() {
        val main = Intent(applicationContext, MainActivity::class.java)
        startActivity(main)
        finish()
    }

    companion object {
        val STARTUP_DELAY = 300
        val ANIM_ITEM_DURATION = 1000
        val ITEM_DELAY = 300
    }
}

