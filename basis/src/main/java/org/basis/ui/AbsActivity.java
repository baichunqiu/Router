
package org.basis.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.basis.network.controller.NetController;
import org.basis.network.view.TitleBar;
import org.basis.ui.base.BaseActivity;
import org.loader.utilslib.R;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsActivity
 * @Description: 没有列表的activity
 */
public abstract class AbsActivity<T> extends BaseActivity {
    public LinearLayout ll_contain;
    private View contentView;
    private NetController<T> mController;
    public TitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs);
        titleBar = findViewById(R.id.titleBar);
        _initNetController();
        _init();

    }

    public void _initNetController(){
        mController = new NetController<T>() {
            @Override
            public void _onResponceCallBack(String url, int state, String msg) {
                onResponceCallBack(url,state,msg);
            }

            @Override
            public void _bindData(List<T> mNetData) {
                if (null != mNetData && !mNetData.isEmpty()){
                    bindData(mNetData.get(0));
                }
            }
        };
    }

    public void _init() {
        //初始化内容视图
        contentView = initContentView();
        ll_contain = (LinearLayout)findViewById(R.id.ll_content);
        ll_contain.addView(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initView(contentView);
    }

    public void getNetData(final String mUrl, final Map<String, String> params, Class<T> tClass, final String mDialogMsg) {
        if (null == mController){
            return;
        }
        mController.postArr(mUrl,params,tClass, buildDailog(mDialogMsg));
    }

    public void postOperate(final String mUrl, final Map<String, String> params, final String mDialogMsg) {
        if (null == mController){
            return;
        }
        mController.post(mUrl,params, buildDailog(mDialogMsg));
    }



    /**
     * 接口响应回调
     * @param url 接口连接
     * @param state 是否成功
     * @param msg 服务器返回msg
     */
    public void onResponceCallBack(String url,int state,String msg){
    }

    /**
     * 绑定数据
     * @param t 调接口获取数据后必执行此方法 故可能为null
     */
    public void bindData(T t) {
    }

    /**
     * init ContentView
     * @return
     */
    public abstract View initContentView();


    public abstract void initView(View parent);
}
