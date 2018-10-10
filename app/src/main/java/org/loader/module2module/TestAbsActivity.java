package org.loader.module2module;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import org.basis.ui.AbsActivity;

/**
 * @author: BaiCQ
 * @ClassName: TestAbsActivity
 * @date: 2018/10/10
 * @Description: 展示Base功能封装功能
 */
public class TestAbsActivity extends AbsActivity {

    @Override
    protected View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_base_abs,null);
    }

    public void normalNetActivity(View view){
        startActivity(new Intent(mActivity, NetWorkActivity.class));
    }
    public void absListActivity(View view){
        startActivity(new Intent(mActivity, NetMasterActivity.class));
    }
}
