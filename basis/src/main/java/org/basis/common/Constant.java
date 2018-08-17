package org.basis.common;

import android.os.Environment;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: NetConstant
 * @date: 2018/8/17
 * @Description: NetConstant 公共常量值
 */
public class Constant {
    public final static String ACTION_APP_EXIT = "org_app_exit";

    /*****************存储路径 path*****************/
    public final static String ROOT = Environment.getExternalStorageDirectory()+ File.separator;
    public final static String BASE_PATH = ROOT + "basis";
    public final static String SP_DEFAULT = "org_basis_common";

    /*****************UI refresh*****************/
    //页码sizek
    public final static String pageSize = "size";
    //当前页的索引key
    public final static String pageIndex = "index";
    /*showViewType*/
    //没有数据
    public static int NO_DATA = 0;
    //加载失败
    public static int LOAD_FAIL = -1;
    //显示数据
    public static int SHOW_DATA = 1;
}
