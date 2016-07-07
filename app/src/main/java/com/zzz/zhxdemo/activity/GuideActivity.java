package com.zzz.zhxdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzz.zhxdemo.R;
import com.zzz.zhxdemo.adapter.GuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/*
* 1.this class is used for guide
* 2.include four page to show the app info and at last ,one button to enter the app.
* 3.if you forget to slide the pages, every 10 secs ,it will into next page util the last,
* then, enter the app.
* 4.the dots num is changed through the pagers' num.
* 5.add skip text to skip guide, enter the next activity.
* */


public class GuideActivity extends CommonActivity implements ViewPager.OnPageChangeListener {

    private GuidePagerAdapter guideAdapter;
    private ViewPager guideViewPager;
    private Button enterBtn;
    private TextView skipText;

    private List<View> views;

    //引导图片资源
    private static final int[] pics = { R.mipmap.guide_page_one,
            R.mipmap.guide_page_two, R.mipmap.guide_page_three,
            R.mipmap.guide_page_four};

    //底部小点图片
    private ImageView[] dots ;

    //记录当前选中位置
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

        myHandler.sendEmptyMessageDelayed(0, 10000);
    }
    @Override
    public void initView() {
        // TODO Auto-generated method stub

        views = new ArrayList<View>();
        views = new ArrayList<>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setBackgroundResource(pics[i]);
            views.add(iv);
        }

        guideViewPager = (ViewPager)findViewById(R.id.guide_pager);
        guideAdapter = new GuidePagerAdapter(views);
        guideViewPager.setAdapter(guideAdapter);
        guideViewPager.addOnPageChangeListener(this);


        //初始化底部小点
        initDots();

        enterBtn = (Button)findViewById(R.id.enter_btn);

        skipText = (TextView)findViewById(R.id.skip_text);
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
    }

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(currentIndex < views.size()){
                setCurDot(currentIndex + 1);
                setCurView(currentIndex);

            }else{
                gotoMainActivity();
            }
        }

    };

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        //设置底部小点选中状态
        if(arg0 == views.size() - 1){
            showDots(false);
            enterBtn.setVisibility(View.VISIBLE);
        }else{
            showDots(true);
            enterBtn.setVisibility(View.GONE);
        }
        setCurDot(arg0);

        myHandler.removeMessages(0);
        myHandler.sendEmptyMessageDelayed(0, 10000);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.dot_layout);

        dots = new ImageView[pics.length];

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dots_black);
        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = new ImageView(GuideActivity.this);
            dots[i].setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
            dots[i].setBackgroundResource(R.drawable.selector_dot_black_gray);
            ll.addView(dots[i]);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }


    /**
     *设置当前的引导页
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }

        guideViewPager.setCurrentItem(position);
    }

    private void showDots(boolean isShow){
        //循环取得小点图片
        for (int i = 0; i < dots.length; i++) {
            if(isShow){
                dots[i].setVisibility(View.VISIBLE);
            }else{
                dots[i].setVisibility(View.GONE);
            }

        }
    }

    /**
     *这只当前引导小点的选中
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
    }


    public void enterMain(View v){
        gotoMainActivity();
    }

    private void gotoMainActivity(){
        myHandler.removeMessages(0);
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        GuideActivity.this.finish();
    }

}

