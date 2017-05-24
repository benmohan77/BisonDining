package com.mohan.gaffaney.bisondining.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mohan.gaffaney.bisondining.R;
import com.mohan.gaffaney.bisondining.fragment.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    PagerAdapter adapter;
    FragmentManager fragmentManager;

    static ArrayList<String> Favorites = new ArrayList<>();
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private String[] activityTitles;
    private final String[] diningHalls  = {"Residence", "West", "Union"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String[] FOODS = {
            "Burger", "Potatoes", "Grilled Cheese", "Salad", "Memes", "Pencils", "Yogurt", "A", "B", "C", "D", "E", "F"};
    public static  String[] RESIDENCE = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static  String[] WEST = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static  String[] UNION = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Assign default View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //Get views and layouts
        activityTitles = getResources().getStringArray(R.array.nav_item_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);


        //Assign title
        mTitle = mDrawerTitle = getTitle();

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, activityTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new PagerAdapter(getSupportFragmentManager());



        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(mViewPager);


    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }


    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return ArrayListFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position){
            CharSequence txt;
            switch (position){
                case 0:
                    txt = diningHalls[0];
                    break;
                case 1:
                    txt = diningHalls[1];
                    break;
                case 2:
                    txt = diningHalls[2];
                    break;
                default:
                    txt = "";
                    break;
            }
            return txt;
        }
    }



    public static class ArrayListFragment extends ListFragment {
        int Num;

        static ArrayListFragment newInstance(int num){
            ArrayListFragment f = new ArrayListFragment();

            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            Num = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.list_fragment_object, container, false);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            switch (Num){
                case 0:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, RESIDENCE));
                    break;
                case 1:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, WEST));
                    break;
                case 2:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, UNION));
                    break;
                default:

                    break;
            }

        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + FOODS[position]);
            Favorites.add(FOODS[position]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position){
        PreferenceFragmentCompat fragment;
        switch(position){
            case 0:
                break;
            case 1:
                fragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("settings").commit();
                break;
            default:
                break;
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }
}
