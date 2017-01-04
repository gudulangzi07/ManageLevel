package com.mmbuycar.managelevel.framework.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmbuycar.managelevel.framework.handler.RepeatedClickHandler;
import com.mmbuycar.managelevel.utils.KeyboardUtil;

import butterknife.ButterKnife;

/**
 * @ClassName: BaseFragment
 * @author: 张京伟
 * @date: 2017/1/4 10:41
 * @Description: 基类fragment
 * @version: 1.0
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean isFirstVisible = true;
    protected boolean isVisible;

    protected boolean isPrepared;// 标志位，标志已经初始化完成。
    private RepeatedClickHandler repeatedClickHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 处理重复点击
        repeatedClickHandler = new RepeatedClickHandler();
        View view = onLayoutView(inflater, container);
        ButterKnife.bind(view);
        beforeInitView();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare(){
        if (isPrepared && isFirstVisible){
            isFirstVisible = false;
            onFirstUserVisible();
        }else{
            isPrepared = true;
        }
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if (isFirstVisible){
                initPrepare();
            }else{
                isVisible = true;
                onUserVisible();
            }
        } else {
            isVisible = false;
            onUserInvisible();
        }
    }

    /**
     * 初始化布局
     */
    protected abstract View onLayoutView(LayoutInflater inflater, ViewGroup container);

    protected abstract void beforeInitView();

    protected abstract void initView(View container);

    protected abstract void onclickEvent(View view);

    protected void onUserVisible(){
        lazyLoad();
    }

    //第一次展示给用户调用方法
    protected abstract void onFirstUserVisible();

    protected abstract void lazyLoad();

    //对用户不可见
    protected void onUserInvisible(){
    }

    @Override
    public void onClick(View view) {
        KeyboardUtil.hideSoftInput(getActivity());
        repeatedClickHandler.handleRepeatedClick(view);
        onclickEvent(view);
    }
}
