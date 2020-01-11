package net.blueridge.apps.resource_full;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by aborsay on 10/1/2018.
 */

public class Master_Parent extends AppCompatActivity {
    protected  ResourceFullApplication appData;

    SharedPreferences localPrefs;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=((ResourceFullApplication) getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /*
        Intent intent = new Intent();
        intent.setAction("com.motoli.apps.allsubjects.SendBroadcast");
        //intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        */
    }

    public void setBackground(){
        if(findViewById(R.id.background) != null) {
            localPrefs = this.getPreferences(Context.MODE_PRIVATE);
            int savedBackground = localPrefs.getInt("background_type", 0);
            switch (savedBackground) {
                case 0:
                default:
                    findViewById(R.id.background).setBackground(
                            ContextCompat.getDrawable(this, R.drawable.background_normal_grad));
                    break;
                case 1:
                    findViewById(R.id.background).setBackground(
                            ContextCompat.getDrawable(this, R.drawable.background_blue_grad));
                    break;
                case 2:
                    findViewById(R.id.background).setBackground(
                            ContextCompat.getDrawable(this, R.drawable.backgrond_mix_grad));
                    break;
                case 3:
                    findViewById(R.id.background).setBackground(
                            ContextCompat.getDrawable(this, R.drawable.background_light_grad));
                    break;
                case 4:
                    findViewById(R.id.background).setBackground(
                            ContextCompat.getDrawable(this, R.drawable.background_red_grad));
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onStart(){
        super.onStart();

    }

    public void onDestroy(){
        super.onDestroy();
        //audioFunctions.stopAudio();
    }
    public void moveBackToMain(View view){
        moveBackWords();
    }
    protected void moveBackWords(){
        Intent main;

        int section = appData.getCurrentSection();
        if(section<=1){
            finishAffinity();
        }else{
            main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = true;
        if ((keyCode == KeyEvent.KEYCODE_BACK )) {
            moveBackWords();
        }
        return result;
    }
    /**
     * Will exit application completely
     * @param view exitApp Image
     */
    public void exitApplication(View view){
        finishAffinity();
    }
}
