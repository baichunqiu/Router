package org.basis.network.controller;

import android.view.LayoutInflater;
import android.view.View;

import org.basis.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: IOperator
 * @Description: UIController 交互数据接口
 */
public interface IOperator<T> {

    /**
     * 解析数据时,数据预处理
     *
     * @param netData
     */
    List<T> onPreHandleData(List<T> netData);

    /**
     * adapter设置数据前,数据处理。
     * 此处没使用泛型，特殊情况需要可能修改类型
     *
     * @param netData
     */
    List onPreRefreshData(List<T> netData);

    /**
     * create content 视图 添加到ll_content节点中
     * @param inflater
     */
    View onCreateContentView(LayoutInflater inflater);

    /**
     * 设置适配器
     */
    AbsBaseAdapter setAdapter();
}
