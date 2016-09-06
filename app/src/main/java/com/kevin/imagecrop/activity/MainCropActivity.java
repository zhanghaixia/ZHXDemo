package com.kevin.imagecrop.activity;

import android.support.v4.app.FragmentTransaction;

import com.kevin.imagecrop.activity.basic.BaseActivity;
import com.kevin.imagecrop.fragment.MainFragment;
import com.kevin.imagecrop.fragment.basic.BaseFragment;
import com.zzz.zhxdemo.R;

/**
 * 版权所有：XXX有限公司
 *
 * MainCropActivity
 *
 * @author zhou.wenkai ,Created on 2016-5-5 10:24:35
 * 		   Major Function：<b>主界面</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class MainCropActivity extends BaseActivity {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main_crop);
    }

    @Override
    protected void initViews() {
        initMainFragment();
    }

    /**
     * 初始化内容Fragment
     *
     * @return void
     */
    public void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment mFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        transaction.commit();
    }

    @Override
    protected void initEvents() {

    }
}
