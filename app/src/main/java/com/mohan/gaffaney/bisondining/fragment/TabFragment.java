package com.mohan.gaffaney.bisondining.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mohan.gaffaney.bisondining.R;
import com.mohan.gaffaney.bisondining.objects.foodItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.mohan.gaffaney.bisondining.activity.MainActivity.Favorites;


public class TabFragment extends Fragment {
    PagerAdapter adapter;
    ViewPager mViewPager;
    TabLayout tabs;

    public static final String[] FOODS = {
            "Burger", "Potatoes", "Grilled Cheese", "Salad", "Memes", "Pencils", "Yogurt", "Lasagna", "Fries", "Apples", "Oreo Cookie Salad", "Chicken Strips", "Tacos"};

    public static ArrayList<foodItem> Residence = new ArrayList<>();
    public static ArrayList<foodItem> West = new ArrayList<>();
    public static ArrayList<foodItem> Union = new ArrayList<>();
    private final String[] diningHalls  = {"Residence", "West", "Union"};

    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs;
    private OnFragmentInteractionListener mListener;

    public TabFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        // Inflate the layout for this fragment
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        adapter = new PagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(mViewPager);

        for(int i = 0; i < FOODS.length; i++){
            Residence.add(new foodItem(FOODS[i], true));
            West.add(new foodItem(FOODS[i], true));
            Union.add(new foodItem(FOODS[i], true));

        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        editor = prefs.edit();


        if(prefs.getStringSet("Residence", null) == null){
            editor.putStringSet("Residence", setArrayListToSet(Residence));
            editor.commit();
        }
        if(prefs.getStringSet("West", null) == null){
            editor.putStringSet("West", setArrayListToSet(West));
            editor.commit();
        }
        if(prefs.getStringSet("Union", null) == null){
            editor.putStringSet("Union", setArrayListToSet(Union));
            editor.commit();
        }



        return view;
    }

    public static Set<String> setArrayListToSet(ArrayList<foodItem> foods){
        Set<String> storedFood = new HashSet<>();
        Gson gson = new Gson();
        for(foodItem a: foods){
            storedFood.add(gson.toJson(a));
        }
        return storedFood;
    }

    public static ArrayList<foodItem> getArrayListFromPrefs(String key){
        Set<String> set = prefs.getStringSet(key, null);
        Gson gson = new Gson();
        ArrayList<foodItem> foods = new ArrayList<>();
        for (String a : set) {
            foods.add(gson.fromJson(a, foodItem.class));
        }
        return foods;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MenuListFragment.newInstance(i);
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

    public static class FoodAdapter extends ArrayAdapter<foodItem>{
        private ArrayList<foodItem> foods;
        public FoodAdapter(Context context, ArrayList<foodItem> food){
            super(context, 0, food);
            foods = food;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            final foodItem foodItem = getItem(position);

            if(convertView ==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorite_item, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.favorite_text);
            CheckBox fav = (CheckBox) convertView.findViewById(R.id.favorite_img);
            name.setText(foodItem.itemName);
            fav.setChecked(foodItem.isFavorite);
            fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    foods.get(position).toggleFavorite();
                    editor.putStringSet("Residence", setArrayListToSet(foods));
                    editor.commit();
                }
            });
            for(foodItem a : getArrayListFromPrefs("Residence")){
                System.out.println(a.toString());
            }


            return convertView;
        }
    }



    public static class MenuListFragment extends ListFragment {
        int Num;

        static MenuListFragment newInstance(int num){
            MenuListFragment f = new MenuListFragment();

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
            View v = inflater.inflate(R.layout.list_fragment, container, false);
            View view = inflater.inflate(R.layout.favorite_item, container, false);
            return v;
        }

        public int getNum(){
            return Num;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            switch (Num){
                case 0:
                    setListAdapter(new FoodAdapter(getActivity(), getArrayListFromPrefs("Residence")));
                    break;
                case 1:
                    setListAdapter(new FoodAdapter(getActivity(), getArrayListFromPrefs("West")));
                    break;
                case 2:
                    setListAdapter(new FoodAdapter(getActivity(), getArrayListFromPrefs("Union")));
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


}
