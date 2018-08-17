package org.basis.network.listener.refresh.defauleinstance;


import org.basis.ui.base.IRefresh;
import org.basis.network.listener.refresh.OnLoadListener;

/**
 * @author: BaiCQ
 * @ClassName: DefaultRefreshListener
 * @Description: OnLoadListener
 */
public class DefaultLoadListener implements OnLoadListener {
    private IRefresh iRefreshUI;

    public DefaultLoadListener(IRefresh iRefreshUI) {
        this.iRefreshUI = iRefreshUI;
    }

    public void onLoad(){
        iRefreshUI.getNetData(false,false);
    }
}
