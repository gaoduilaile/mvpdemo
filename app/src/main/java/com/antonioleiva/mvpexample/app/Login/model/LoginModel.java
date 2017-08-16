package com.antonioleiva.mvpexample.app.Login.model;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.customview.CustomDialog;

public class LoginModel {

    public CustomDialog customDialog;

    public void login(final Context context, final String username, final String password, final LoginInterface loginInterface) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        // 网络请求接口代码

        boolean error = false;
        if (TextUtils.isEmpty(username)) {
            loginInterface.onUsernameError();
            error = true;
            show(context);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();

                }
            },5000);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginInterface.onPasswordError();
            error = true;
            return;
        }
        if (!error) {
            loginInterface.onSuccess();
        }
    }

    public interface LoginInterface {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    public void show(Context context) {
        if (!((Activity) context).isFinishing()) {
            if (customDialog == null) {
                customDialog = new CustomDialog(context, R.style.customDialog);
                customDialog.show();
            } else {
                customDialog.show();
            }
        }
    }

    public void dismiss() {
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
    }

}
