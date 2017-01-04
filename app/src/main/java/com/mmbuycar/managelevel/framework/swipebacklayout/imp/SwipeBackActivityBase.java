package com.mmbuycar.managelevel.framework.swipebacklayout.imp;

import com.mmbuycar.managelevel.framework.swipebacklayout.views.SwipeBackLayout;

/**
 * Created by Administrator on 2016/3/14.
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    void scrollToFinishActivity();
}
