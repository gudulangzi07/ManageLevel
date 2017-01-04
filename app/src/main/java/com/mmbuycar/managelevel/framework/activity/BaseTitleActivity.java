package com.mmbuycar.managelevel.framework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmbuycar.managelevel.R;
import com.mmbuycar.managelevel.framework.activity.listener.TitleLeftListener;
import com.mmbuycar.managelevel.framework.activity.listener.TitleRightListener;

/**
 * @ClassName: BaseTitleActivity
 * @author: 张京伟
 * @date: 2017/1/3 19:18
 * @Description: 标题
 * @version: 1.0
 */
public abstract class BaseTitleActivity extends BaseActivity {
    private ActionBar actionBar;
    private View titleView;

    private LinearLayout ll_left;//返回按钮
    private TextView tv_title;//标题
    private LinearLayout ll_right;//右边布局

    private TitleLeftListener titleLeftListener;
    private TitleRightListener titleRightListener;

    public void setTitleLeftListener(TitleLeftListener titleLeftListener) {
        this.titleLeftListener = titleLeftListener;
    }

    public void setTitleRightListener(TitleRightListener titleRightListener) {
        this.titleRightListener = titleRightListener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        actionBar = getSupportActionBar();
        //隐藏系统自带的标题
        actionBar.setDisplayShowTitleEnabled(false);
        //隐藏系统自带的
        actionBar.setDisplayShowHomeEnabled(false);
        //是否显示标题栏下
        //actionBar.setDisplayHomeAsUpEnabled(false);
        //显示自定义视图
        actionBar.setDisplayShowCustomEnabled(true);

        //android5.0以后设置下划线
        actionBar.setElevation(0.5f);

        titleView = LayoutInflater.from(this).inflate(R.layout.title_layout, null);
        ll_left = (LinearLayout) titleView.findViewById(R.id.ll_left);
        tv_title = (TextView) titleView.findViewById(R.id.tv_title);
        ll_right = (LinearLayout) titleView.findViewById(R.id.ll_right);

        actionBar.setCustomView(titleView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                if (titleLeftListener != null){
                    titleLeftListener.onTitleLeftClickListener();
                }else{
                    onBackPressed();
                }
                break;
            case R.id.ll_right:
                if (titleRightListener != null){
                    titleRightListener.onTitleRightClickListener();
                }
                break;
        }
    }

    //隐藏标题栏
    public void hideActionBar() {
        if (actionBar != null)
            actionBar.hide();
    }

    //显示标题栏
    public void showActionBar() {
        if (actionBar != null)
            actionBar.show();
    }

    //设置标题
    public void setTitle(String title){
        tv_title.setText(title);
    }

    //隐藏返回按钮
    public void hideLeftLayout(){
        ll_left.setVisibility(View.GONE);
    }

    //显示返回按钮
    public void showLeftLayout(){
        ll_left.setVisibility(View.VISIBLE);
    }
}
