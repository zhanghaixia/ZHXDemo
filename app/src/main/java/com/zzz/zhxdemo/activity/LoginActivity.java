package com.zzz.zhxdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zzz.zhxdemo.R;
import com.zzz.zhxdemo.adapter.NameListAdapter;
import com.zzz.zhxdemo.db.SQLiteHelper;
import com.zzz.zhxdemo.util.WarnDialog;

import java.util.ArrayList;

public class LoginActivity extends Activity/* implements SeekBar.OnSeekBarChangeListener */{

    private EditText nameText;
    private EditText pwdText;
    private ImageView showDrowBtn;
    private CheckBox remPwd;
    private TextView findPwd;
    private Button loginBtn;
    private Button rgBtn;
    private FrameLayout frame;
    private SeekBar seekBar;
    private LinearLayout nameEditLayout;

    private ArrayList<String> mList = new ArrayList<String>();
    private PopupWindow mPopup;
    private boolean mShowing;
    private NameListAdapter mAdapter;
    private ListView mListView;
    private boolean mInitPopup;
    private WarnDialog warnDialog;

    private SQLiteHelper db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /* initView();
        initDate();*/
    }

   /* private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            warnDialog.dismissWarnDialog();
        }

    };

    private void initDate(){
        ObjectInputStream in = null;
        try {
            InputStream is = openFileInput("account.obj");
            in = new ObjectInputStream(is);
            mList = (ArrayList<String>) in.readObject();
            if (mList.size() > 0) {
                nameText.setText(mList.get(0));
                *//*if(sp.getBoolean("isRemember", false)){
                    pwdText.setText(sp.getString(nameText.getText().toString().trim(), ""));
                    remPwd.setChecked(true);
                }*//*
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void initPopup(){
        mAdapter = new NameListAdapter(LoginActivity.this, mList);
        mListView = new ListView(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                nameText.setText(mList.get(position));
                pwdText.setText("");
                mPopup.dismiss();
            }
        });

        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int width = pwdText.getWidth();
        System.out.println(width);
        mPopup = new PopupWindow(mListView, width, height, true);
        mPopup.setOutsideTouchable(true);
        mPopup.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.rect_cycle_white_bg));
        mPopup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                mShowing = false;
            }
        });
    }

    private void rememberName(){
        String input = nameText.getText().toString();
        mList.remove(input);
        mList.add(0,input);
        if (mList.size() > 5) {
            mList.remove(0);
        }
        ObjectOutputStream out = null;
        try {
            FileOutputStream os = openFileOutput("account.obj",
                    MODE_PRIVATE);
            out = new ObjectOutputStream(os);
            out.writeObject(mList);
        } catch (Exception e) {


        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub

        nameEditLayout = (LinearLayout)findViewById(R.id.sr_username_edit_layout);
        pwdText = (EditText)findViewById(R.id.sr_password);
        nameText = (EditText)findViewById(R.id.sr_username);

        initDropBtn();
        initLoginBtn();


        nameText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    nameEditLayout.setBackgroundResource(R.drawable.set_button_cir_white_border);
                }else{
                    nameEditLayout.setBackgroundResource(R.drawable.rect_cycle_edit_white_gray);
                }
            }
        });


        remPwd = (CheckBox)findViewById(R.id.sr_remember);

        findPwd = (TextView)findViewById(R.id.sr_reget_psw);
        findPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                gotoActivity(LoginActivity.this, ForgotPwdActivity.class);
                LoginActivity.this.finish();
            }
        });



        rgBtn = (Button)findViewById(R.id.sr_register);
        rgBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                gotoActivity(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.finish();
            }
        });

        frame = (FrameLayout)findViewById(R.id.quick_start_layout_login);
        seekBar = (SeekBar)frame.findViewById(R.id.quick_enter_bar);
        seekBar.setOnSeekBarChangeListener(this);

    }

    private void initDropBtn(){
        showDrowBtn = (ImageView)findViewById(R.id.show_drop);
        showDrowBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mList!=null && mList.size()>0 && !mInitPopup){
                    mInitPopup = true;
                    initPopup();
                }
                if (mPopup != null) {
                    if (!mShowing) {
                        mPopup.showAsDropDown(nameText,0,-5);
                        mShowing  = true;
                    } else {
                        mPopup.dismiss();
                    }
                }
            }
        });
    }

    private void initLoginBtn(){
        loginBtn = (Button)findViewById(R.id.sr_login);
        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(checkLogin()){
                    rememberName();
                    gotoActivity(LoginActivity.this, QuickStartRunActivity.class);
                    LoginActivity.this.finish();
                }else{
                    warnDialog = new WarnDialog(LoginActivity.this);
                    warnDialog.showWarnDialog(R.layout.layout_dialog_error, R.string.login_error, true);
                    myHandler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        });
    }

    private boolean checkLogin(){
        db = MainActivity.db;
        String name = nameText.getText().toString().trim();
        String pwd = pwdText.getText().toString().trim();
        Cursor cursor = db.rawQuery("select * from TBL_PERSONAL_INFO where name = ?", new String[] { name });
        if(cursor.moveToNext()){
            if(pwd.equals(cursor.getString(3))){
                if(remPwd.isChecked()){
                    editor.putBoolean("isRemember", true);
                    editor.putString(name, pwd);

                }
                else{
                    editor.putBoolean("isRemember", false);
                }
                editor.putString("name", name);
                String sexString = cursor.getInt(4) == 0?"boy":"girl";
                editor.putString("sex", sexString);
                editor.putFloat("weight", cursor.getFloat(7));
                editor.putBoolean("isLogin", true);
                editor.commit();
                cursor.close();
                return true;
            }
        }
        editor.putBoolean("isLogin", false);
        editor.commit();
        cursor.close();
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // TODO Auto-generated method stub
        if(progress == seekBar.getMax()){
            gotoFitSelect();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        seekBar.setProgress(0);
    }
*/
}
