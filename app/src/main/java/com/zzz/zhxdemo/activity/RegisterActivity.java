package com.zzz.zhxdemo.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.zzz.zhxdemo.R;
import com.zzz.zhxdemo.db.SQLiteHelper;
import com.zzz.zhxdemo.util.WarnDialog;

public class RegisterActivity extends CommonActivity implements View.OnClickListener {

    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private Spinner spinnerThree;

    private EditText userName;
    private EditText realName;
    private EditText pwdText;
    private EditText confirmPwd;
    private EditText ageText;
    private EditText heightText;
    private EditText weightText;
    private EditText ansOneText;
    private EditText ansTwoText;
    private EditText ansThreeText;

    private TextView nameError;
    private TextView realNameError;
    private TextView pwdErrorOne;
    private TextView pwdErrorTwo;
    private TextView pwdErrorThree;
    private TextView confirmPwdError;
    private TextView ageRrror;
    private TextView heightError;
    private TextView weightError;

    private Button rgBtn;
    private RadioButton boyBtn;
    private RadioButton girlBtn;

    private SQLiteHelper db = null;

    private WarnDialog warnDialog;

    private ArrayAdapter adapter;

    private final int RG_SUCCESS = 0;
    private final int RG_FAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initDb();
    }

    private void initDb(){
        db =SQLiteHelper.getInstance(getApplicationContext());
        db.open();
    }

    @Override
    protected void initView() {
        rgBtn = (Button)findViewById(R.id.register_btn);
        rgBtn.setOnClickListener(this);

        boyBtn = (RadioButton)findViewById(R.id.user_sex_boy);
        boyBtn.setChecked(true);

        girlBtn = (RadioButton)findViewById(R.id.user_sex_girl);

        initSetView();
        initSpinner();
        initErrorText();
    }

    private void initSetView(){
        userName = (EditText)findViewById(R.id.user_name);
        realName = (EditText)findViewById(R.id.user_real_name);
        pwdText = (EditText)findViewById(R.id.user_pwd_edit);
        confirmPwd = (EditText)findViewById(R.id.user_confirm_pwd);
        ageText = (EditText)findViewById(R.id.user_age);
        heightText = (EditText)findViewById(R.id.user_height);
        weightText = (EditText)findViewById(R.id.user_weight);
        ansOneText = (EditText)findViewById(R.id.user_answer_one);
        ansTwoText = (EditText)findViewById(R.id.user_answer_two);
        ansThreeText = (EditText)findViewById(R.id.user_answer_three);
    }

    private void initErrorText(){
        nameError = (TextView)findViewById(R.id.name_error_text);
        realNameError = (TextView)findViewById(R.id.real_name_error_text);
        pwdErrorOne = (TextView)findViewById(R.id.pwd_error_one);
        pwdErrorTwo = (TextView)findViewById(R.id.pwd_error_two);
        pwdErrorThree = (TextView)findViewById(R.id.pwd_error_three);
        confirmPwdError = (TextView)findViewById(R.id.confirm_pwd_error_text);
        ageRrror = (TextView)findViewById(R.id.age_error_text);
        heightError = (TextView)findViewById(R.id.height_error_text);
        weightError = (TextView)findViewById(R.id.weight_error_text);
    }

    private void initSpinner() {

        spinnerOne = (Spinner) findViewById(R.id.question_one_spinner);
        spinnerTwo = (Spinner) findViewById(R.id.question_two_spinner);
        spinnerThree = (Spinner) findViewById(R.id.question_three_spinner);
        // 将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.security, android.R.layout.simple_spinner_item);

        // 设置下拉列表的风格
        adapter.setDropDownViewResource(R.layout.layout_spinner_item);

        // 将adapter 添加到spinner中
        spinnerOne.setAdapter(adapter);
        spinnerTwo.setAdapter(adapter);
        spinnerThree.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        warnDialog = new WarnDialog(RegisterActivity.this);
        isOk = true;
        if(v.getId() == R.id.register_btn){
            checkRgInfo();
            if(isOk && checkName(userName,nameError)){
                rgOneUser();
                warnDialog.showWarnDialog(R.layout.layout_dialog_error, R.string.reg_success);
                myHandler.sendEmptyMessageDelayed(RG_SUCCESS, 3000);

            }else{
                warnDialog.showWarnDialog(R.layout.layout_dialog_error, R.string.reg_failed_info_tips);
                myHandler.sendEmptyMessageDelayed(RG_FAIL, 3000);
            }
        }
    }

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case RG_SUCCESS:
                    warnDialog.dismissWarnDialog();
                   // gotoActivity(RegisterActivity.this, NavigationActivity.class);
                    RegisterActivity.this.finish();
                    break;
                case RG_FAIL:
                    warnDialog.dismissWarnDialog();
                    break;
                default:
                    break;
            }

        }

    };

    private void rgOneUser(){

        String[] values = getUserData();
        db.insert("TBL_PERSONAL_INFO", new String[]{"NAME","REAL_NAME","PWD","SEX","AGE","HEIGHT","WEIGHT","QUESTION_ONE","ANSWER_ONE","QUESTION_TWO","ANSWER_TWO","QUESTION_THREE","ANSWER_THREE"}, values);
    }

    private String[] getUserData(){
        String sexChoice = boyBtn.isChecked()?"0":"1";

        String quesOne = spinnerOne.getSelectedItemPosition() + "";
        String quesTwo = spinnerTwo.getSelectedItemPosition() + "";
        String quesThree = spinnerThree.getSelectedItemPosition() + "";

        String[] values = {getEditText(userName), getEditText(realName), getEditText(pwdText), sexChoice, getEditText(ageText), getEditText(heightText), getEditText(weightText),
                quesOne, getEditText(ansOneText), quesTwo, getEditText(ansTwoText), quesThree, getEditText(ansThreeText)};

        return values;
    }

    private boolean checkRgInfo(){
        judgeEmpty(userName, nameError);
        judgeEmpty(realName, realNameError);

        checkPwd(pwdText, confirmPwd, pwdErrorOne, pwdErrorTwo, pwdErrorThree, confirmPwdError);

        judgeEmpty(ageText, ageRrror);
        judgeEmpty(heightText, heightError);
        judgeEmpty(weightText, weightError);
        if(!judgeEmpty(ansOneText))
        {
            int quesOne = spinnerOne.getSelectedItemPosition();
            if(quesOne == 0){
                spinnerOne.setBackgroundResource(R.mipmap.spinner_error_bg);
                isOk = false;
            }
        }
        return isOk;

    }

    private boolean checkName(EditText editText,TextView text){
        SQLiteHelper dbHelper = db;
        String name = editText.getText().toString().trim();
        Cursor cursor = dbHelper.rawQuery("select * from TBL_PERSONAL_INFO where name = ?", new String[] { name });
        if(cursor.moveToNext()){
            editText.setBackgroundResource(R.drawable.edit_error_bg);
            text.setVisibility(View.VISIBLE);
            text.setText(R.string.reg_failed_name_used);
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }
}
