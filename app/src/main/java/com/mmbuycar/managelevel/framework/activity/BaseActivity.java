package com.mmbuycar.managelevel.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mmbuycar.managelevel.R;
import com.mmbuycar.managelevel.SoftApplication;
import com.mmbuycar.managelevel.framework.handler.RepeatedClickHandler;
import com.mmbuycar.managelevel.framework.swipebacklayout.activity.SwipeBackActivity;
import com.mmbuycar.managelevel.framework.swipebacklayout.views.SwipeBackLayout;
import com.mmbuycar.managelevel.utils.KeyboardUtil;

import butterknife.ButterKnife;

/**
 * @ClassName: BaseActivity
 * @author: 张京伟
 * @date: 2016/12/27 15:25
 * @Description:
 * @version: 1.0
 */
public abstract class BaseActivity extends SwipeBackActivity implements OnClickListener{

    protected SoftApplication softApplication;
    protected SwipeBackLayout swipeBackLayout;//设置手势关闭
    protected RepeatedClickHandler repeatedClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        softApplication = (SoftApplication) getApplicationContext();
        SoftApplication.unDestroyActivityList.add(this);
        //设置手势关闭
        swipeBackLayout = getSwipeBackLayout();
        // 处理重复点击
        repeatedClickHandler = new RepeatedClickHandler();

        setContentLayout();
        ButterKnife.bind(this);
        beforeInitView();
        initView();
    }

    @Override
    public void onClick(View view) {
        KeyboardUtil.hideSoftInput(this);
        repeatedClickHandler.handleRepeatedClick(view);
        onClickEvent(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtil.hideSoftInput(this);
        SoftApplication.unDestroyActivityList.remove(this);
    }

    /**
     * 设置布局文件
     * */
    abstract public void setContentLayout();

    protected abstract void beforeInitView();

    protected abstract void initView();

    /**
     * onClick方法的封装，在此方法中处理点击
     * @param view 被点击的View对象
     */
    protected abstract void onClickEvent(View view);
}
