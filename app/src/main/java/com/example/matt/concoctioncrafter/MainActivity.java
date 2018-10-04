/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager _pager;
    private FragmentPagerAdapter _pageAdapter;
    private TabLayout tabLayout;
    private Button _startBoilButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.pager_header);
        _pageAdapter = new CustomPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pageAdapter);

        tabLayout.setupWithViewPager(_pager);
    }

    @Override
    public void onBackPressed() {
        if (_pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            _pager.setCurrentItem(_pager.getCurrentItem() - 1);
        }
    }

    /**
     * A custom page adapter.
     */
    private class CustomPageAdapter extends FragmentPagerAdapter {
        private CustomPageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new RecipeFragment();
            } else {
                return new BrewDayFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.recipe);
            } else {
                return getString(R.string.brew_day);
            }
        }
    }
}
