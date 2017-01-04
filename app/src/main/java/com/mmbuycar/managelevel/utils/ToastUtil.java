package com.mmbuycar.managelevel.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName: ToastUtil
 * @author: 张京伟
 * @date: 2016/12/27 16:38
 * @Description:
 * @version: 1.0
 */
public class ToastUtil {
    /**
     * 短时间显示Toast
     * @param info 显示的内容
     */
    public void showToast(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     * @param resId 显示的内容
     */
    public void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
