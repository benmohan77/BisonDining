package com.mohan.gaffaney.bisondining.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mohan.gaffaney.bisondining.R;

import static com.mohan.gaffaney.bisondining.activity.MainActivity.Favorites;


public class TabFragment extends Fragment {
    PagerAdapter adapter;
    ViewPager mViewPager;
    TabLayout tabs;

    public static final String[] FOODS = {
            "Burger", "Potatoes", "Grilled Cheese", "Salad", "Memes", "Pencils", "Yogurt", "A", "B", "C", "D", "E", "F"};
    public static  String[] RESIDENCE = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static  String[] WEST = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static  String[] UNION = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    private final String[] diningHalls  = {"Residence", "West", "Union"};


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

        return view;
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
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            switch (Num){
                case 0:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.favorite_item, R.id.favorite_text, RESIDENCE));
                    break;
                case 1:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.favorite_item, R.id.favorite_text, WEST));
                    break;
                case 2:
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.favorite_item, R.id.favorite_text, UNION));
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
