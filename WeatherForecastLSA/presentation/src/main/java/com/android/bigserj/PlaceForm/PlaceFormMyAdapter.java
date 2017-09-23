package com.android.bigserj.PlaceForm;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PlaceFormMyAdapter extends FragmentPagerAdapter {

    // 2
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();

    private Fragment mCurrentFragment;

    public PlaceFormMyAdapter(FragmentManager mgr) {
        super(mgr);
    }

    // кол-во фрагментов
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    // возвращает указанный фрагмент по его номеру
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    // добавляет новый фрагмент
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    // добавляет фрагмент в указанное место
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object)
            mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    // добавляет описание фрагмента
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    // возвращает коллекцию всех фрагментов
    public List<Fragment> getmFragmentList() {
        return mFragmentList;
    }

    // заменяет текузую коллекцию фрагментов новой
    public void setmFragmentList(List<Fragment> mFragmentList) {
//        this.mFragmentList.clear();
//        this.mFragmentList.addAll(mFragmentList);
        this.mFragmentList = mFragmentList;
    }


}