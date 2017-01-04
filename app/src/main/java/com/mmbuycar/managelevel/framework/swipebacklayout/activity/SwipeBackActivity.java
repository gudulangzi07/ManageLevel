package com.mmbuycar.managelevel.framework.swipebacklayout.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mmbuycar.managelevel.framework.swipebacklayout.imp.SwipeBackActivityBase;
import com.mmbuycar.managelevel.framework.swipebacklayout.utils.SwipeBackActivityHelper;
import com.mmbuycar.managelevel.framework.swipebacklayout.utils.Utils;
import com.mmbuycar.managelevel.framework.swipebacklayout.views.SwipeBackLayout;

/**
 * @ClassName: SwipeBackActivity
 * @author: 张京伟
 * @date: 2016/3/14 15:33
 * @Description:
 * @version: 1.0
 */
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mHelper != null)
            return mHelper.findViewById(id);
        return view;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
