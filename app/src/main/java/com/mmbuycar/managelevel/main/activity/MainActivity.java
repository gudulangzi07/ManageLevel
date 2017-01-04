package com.mmbuycar.managelevel.main.activity;

import android.view.View;

import com.mmbuycar.managelevel.R;
import com.mmbuycar.managelevel.framework.activity.BaseTitleActivity;
import com.mmbuycar.managelevel.framework.bottombar.BottomTabBar;
import com.mmbuycar.managelevel.widget.NoScrollViewPager;

import butterknife.BindView;

public class MainActivity extends BaseTitleActivity {

    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        //隐藏返回按钮
        hideLeftLayout();
    }

    @Override
    public void onClickEvent(View view) {
        super.onClickEvent(view);

    }
}
