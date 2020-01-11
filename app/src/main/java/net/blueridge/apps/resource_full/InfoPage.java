package net.blueridge.apps.resource_full;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by aborsay on 10/2/2018.
 */

public class InfoPage extends Master_Parent {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_info);
        setBackground();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        appData.setCurrentSection(2);
        appData.setClassesForLaunch();




        mTabLayout =  findViewById(R.id.menuTabs);
        mViewPager =  findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("About"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Contact"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Donate"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Help"));
        //mTabLayout.setTabTextColors(getResources().getColor(R.color.white_mine));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    }