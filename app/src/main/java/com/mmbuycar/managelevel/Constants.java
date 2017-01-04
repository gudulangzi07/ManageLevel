package com.mmbuycar.managelevel;

import android.os.Environment;

/**
 * @ClassName: Constants
 * @author: 张京伟
 * @date: 2016/12/27 12:10
 * @Description:
 * @version: 1.0
 */
public class Constants {
    public static final String FILE_PATH_BASE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ManageLevel";
    public static final String FILE_PATH_LOG = FILE_PATH_BASE + "/log";
    public static final String LOG_NAME = "ManageLevelLog.txt";

    public static final String APP_CONFIG_FILE_NAME = "AppConfig.json";
}
