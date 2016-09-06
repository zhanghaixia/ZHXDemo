package com.zzz.zhxdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zzz.zhxdemo.R;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonActivity extends AppCompatActivity {

    private AudioManager am;

    public static DecimalFormat df = new DecimalFormat("0.00");
    protected boolean isOk = true;

    public final static int LANGUAGE_CHINA = 0;
    public final static int LANGUAGE_ENGLISH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
    }

     protected  void initView(){};

     public void gotoActivity(Context packageContext, Class<?> cls) {
         Intent intent = new Intent(packageContext, cls);
         startActivity(intent);
     }

     //设置textview带图片的位置和间距
     protected Drawable getCompoundDrawable(int id) {
         Drawable nav_up = getResources().getDrawable(id);
         nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());

         return nav_up;
     }

     protected void setViewVisibility(View v, boolean isShow) {

         if (isShow) {
             v.setVisibility(View.VISIBLE);
         } else {
             v.setVisibility(View.GONE);
         }
     }

     /**
      * 设置添加屏幕的背景透明度
      *
      * @param bgAlpha
      */
     public void backgroundAlpha(float bgAlpha) {
         WindowManager.LayoutParams lp = getWindow().getAttributes();
         lp.alpha = bgAlpha; // 0.0-1.0
         getWindow().setAttributes(lp);
     }

     protected String getEditText(EditText edit) {
         return edit.getText().toString().trim();
     }

     // 针对那些需要变动内容的string对象
     protected String getDataString(int id, Object... formatArgs) {
         return getResources().getString(id, formatArgs);
     }

     protected Spanned getDataHtmlString(int id, Object... formatArgs) {
         return Html.fromHtml(getResources().getString(id, formatArgs));
     }

     // 修改应用语言
     protected void setLanguage(Locale local) {
         Resources resource = getResources();
         DisplayMetrics dm = resource.getDisplayMetrics();
         Configuration config = resource.getConfiguration();
         config.locale = local;
         getBaseContext().getResources().updateConfiguration(config, dm);
     }

     // 设置音乐静音
     public void setVoiceSilence() {
         // 获得系统音频管理服务对象
         am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
         am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
     }

     public static String formatSecToTimeStr(int num) {
         int hour = 0;
         int minute = 0;
         int second = 0;

         hour = num / 3600;
         minute = (num - hour * 3600) / 60;
         second = num - hour * 3600 - minute * 60;
         return (getTwoLength(hour) + ":" + getTwoLength(minute) + ":" + getTwoLength(second));
     }

     private static String getTwoLength(final int data) {
         if (data < 10) {
             return "0" + data;
         } else {
             return "" + data;
         }
     }

    private String transNumToString(Object num){
        return num + "";
    }

    //check info

    protected boolean judgeEmpty(EditText edit){
        if(edit.getText().toString().trim().equals("")){
            edit.setBackgroundResource(R.drawable.edit_error_bg);
            isOk = false;
            return true;
        }
        edit.setBackgroundResource(R.drawable.selector_edit_bg);
        return false;
    }

    protected boolean judgeEmpty(EditText edit, TextView text){
        if(edit.getText().toString().trim().equals("")){
            edit.setBackgroundResource(R.drawable.edit_error_bg);
            text.setVisibility(View.VISIBLE);
            isOk = false;
            return true;
        }
        edit.setBackgroundResource(R.drawable.selector_edit_bg);
        text.setVisibility(View.GONE);
        return false;
    }

    protected void checkPwd(EditText pwdText, EditText confirmPwd, TextView pwdErrorOne, TextView pwdErrorTwo, TextView pwdErrorThree, TextView confirmPwdError){
        String pwdString = pwdText.getText().toString();
        String noBlankPwd = pwdString.replaceAll(" ", "");
        String confirmPwString = confirmPwd.getText().toString();

        int pwdLength = pwdString.length();

        if(judgeEmpty(pwdText)){
            pwdErrorOne.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
            pwdErrorTwo.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
            pwdErrorThree.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
            isOk = false;
        }else{
            if(pwdLength > 16 || pwdLength < 6){
                pwdErrorOne.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
                isOk = false;
            }else{
                pwdErrorOne.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_warm_info), null, null, null);
            }

            if(noBlankPwd.length() != pwdLength){
                pwdErrorTwo.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
                isOk = false;
            }else{
                pwdErrorTwo.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_warm_info), null, null, null);
            }

            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(pwdString.trim());
            if (m.matches() && pwdLength < 9) {
                pwdErrorThree.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_pwd_error_info), null, null, null);
                isOk = false;
            } else {
                pwdErrorThree.setCompoundDrawables(getCompoundDrawable(R.mipmap.ic_warm_info), null, null, null);
            }
        }

        if(judgeEmpty(confirmPwd)){
            confirmPwdError.setVisibility(View.VISIBLE);
            isOk = false;
        }else{
            if(!confirmPwString.trim().equals(pwdString.trim())){
                confirmPwdError.setVisibility(View.VISIBLE);
                confirmPwdError.setText(R.string.pwd_unmatch);
                isOk = false;
            }else{
                confirmPwdError.setVisibility(View.GONE);
            }
        }
    }





}
