package org.basis.network.listener.refresh.defauleinstance;


import org.basis.ui.base.IRefresh;
import org.basis.network.listener.refresh.OnRefreshListener;

/**
 * @author: BaiCQ
 * @ClassName: DefaultRefreshListener
 * @Description: OnRefreshListener默认实现类
 */
public class DefaultRefreshListener implements OnRefreshListener {
    private IRefresh iRefreshUI;

    public DefaultRefreshListener(IRefresh iRefreshUI) {
        this.iRefreshUI = iRefreshUI;
    }

    public void onRefresh(){
        iRefreshUI.getNetData(false,true);
    }
}
