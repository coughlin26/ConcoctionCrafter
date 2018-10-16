/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager _pager;
    private FragmentPagerAdapter _pageAdapter;
    private TabLayout _tabLayout;
    private Toolbar _toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _pager = findViewById(R.id.pager);
        _tabLayout = findViewById(R.id.pager_header);
        _toolBar = findViewById(R.id.toolbar);
        _pageAdapter = new CustomPageAdapter(getSupportFragmentManager());
        _pager.setAdapter(_pageAdapter);

        _tabLayout.setupWithViewPager(_pager);
        setSupportActionBar(_toolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(this, "Save coming soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_import:
                Toast.makeText(this, "Import coming soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.units_action:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
