package com.mmbuycar.managelevel.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ClassName: MainAdapter
 * @author: 张京伟
 * @date: 2017/1/3 17:34
 * @Description:
 * @version: 1.0
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public MainAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
