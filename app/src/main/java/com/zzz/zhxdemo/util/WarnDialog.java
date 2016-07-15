package com.zzz.zhxdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zzz.zhxdemo.R;

/**
 * Created by zhanghx2 on 2016/7/15.
 */
public class WarnDialog extends Dialog {
    private Dialog warnDialog;
    private Context context;

    private TextView warnText;

    public WarnDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public void showWarnDialog(int layoutId, int resid){

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        warnText = (TextView)view.findViewById(R.id.error_text);

        warnText.setText(resid);
        warnDialog = new Dialog(context, R.style.processDialog);
        warnDialog.setContentView(view);
        warnDialog.setCanceledOnTouchOutside(false);
        warnDialog.show();
    }

    public void dismissWarnDialog(){
        warnDialog.dismiss();
    }
}
