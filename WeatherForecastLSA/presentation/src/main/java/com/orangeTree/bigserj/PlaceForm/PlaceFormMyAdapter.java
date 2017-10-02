package com.orangeTree.bigserj.PlaceForm;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlaceFormMyAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> fragmentList = new ArrayList<>();


    public PlaceFormMyAdapter(FragmentManager mgr) {
        super(mgr);
    }


    // кол-во фрагментов
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    // возвращает указанный фрагмент по его номеру
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    // возвращает коллекцию всех фрагментов
    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    // заменяет текузую коллекцию фрагментов новой
    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }


}