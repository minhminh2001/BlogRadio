package com.vnnplus.blogradio.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vnnplus.blogradio.MainActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.base.BaseActivity;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.fragment.FragmentRegister;

/**
 * Created by Giangdv on 4/17/2017.
 */

public class Dialog {
    public static void DialogRegister(final Activity activity , final ViewGroup viewGroup , final FragmentManager manager , String tittle){
        final android.app.Dialog mDialog = new android.app.Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setContentView(R.layout.dialog_register);
        Util.isShow = false;
        TextView tv_register , tv_cancel , tv_tittle;
        tv_register = (TextView) mDialog.findViewById(R.id.tv_register);
        tv_cancel = (TextView) mDialog.findViewById(R.id.tv_cancel);
        tv_tittle = (TextView) mDialog.findViewById(R.id.tv_tittle);
        tv_tittle.setText(tittle);
        mDialog.setCancelable(false);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.isShow = true;
                mDialog.dismiss();
                activity.finish();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.isShow = true;
                mDialog.dismiss();
                if (!Util.offPayment.equals("FALSE")){
                    MainActivity.switchFragment(viewGroup , manager , new FragmentRegister(activity,manager)  ,false);
                }
            }
        });
        try {
            mDialog.show();
        }catch (Exception e){}
    }
    public static void dialogConfirm(Context context, String textTitle, String textContent, String textBtn1, String textBtn2, final PopupConfirmCallback callback) {
        final android.app.Dialog mDialog = new android.app.Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setContentView(R.layout.view_dialog_confirm);
        //
        Button btn1, btn2;
        TextView tvTitle, tvContent;
        //
        btn1 = (Button) mDialog.findViewById(R.id.btn1);
        btn2 = (Button) mDialog.findViewById(R.id.btn2);
        tvTitle = (TextView) mDialog.findViewById(R.id.tvTitle);
        tvContent = (TextView) mDialog.findViewById(R.id.tvContentMs);

        btn1.setText(textBtn1);
        btn2.setText(textBtn2);
        tvTitle.setText(textTitle);
        tvContent.setText(textContent);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(mDialog, v.getId());
            }
        };
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        //
        mDialog.show();
    }

    public interface PopupConfirmCallback {
        public void callback(android.app.Dialog dialog, int btnId);

    }
    public interface PopupconfirmRegister{
        public void callback(android.app.Dialog dialog , int btnId , String edt_sdt);
    }
    public static void dialogRegister(Activity activity , final PopupconfirmRegister callback){
        final android.app.Dialog mDialog = new android.app.Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setContentView(R.layout.view_dialog_import_sdt);
        final EditText edt_phone;
        Button bt_ok;
        bt_ok = (Button) mDialog.findViewById(R.id.btn1);
        edt_phone = (EditText) mDialog.findViewById(R.id.edt_phone);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(mDialog, view.getId(),edt_phone.getText().toString());
            }
        });
        mDialog.show();
    }
    public static void showDialogExtends(final Activity activity , String msg , String tittle){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setTitle(tittle);
        builder.setCancelable(true);
        builder.setPositiveButton("OK" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static void showpopupNetwork(final Activity activity, String msg , String tittle){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setTitle(tittle);
        builder.setCancelable(true);
        BaseActivity.ishowDialog = false;
        builder.setPositiveButton("OK" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                BaseActivity.ishowDialog = true;
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
