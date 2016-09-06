package com.zzz.zhxdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kevin.imagecrop.activity.MainCropActivity;
import com.zzz.zhxdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button cropBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        cropBtn = (Button)findViewById(R.id.btn_crop_image);
        cropBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_crop_image:
                Intent intent = new Intent(MainActivity.this, MainCropActivity.class);
                startActivity(intent);
                break;
            default:

        }
    }
}
