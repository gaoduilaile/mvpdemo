/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.antonioleiva.mvpexample.app.Login.presenter;


import android.content.Context;

import com.antonioleiva.mvpexample.app.Login.model.LoginModel;
import com.antonioleiva.mvpexample.app.Login.view.LoginView;

public class LoginPresenter implements LoginModel.LoginInterface {

    private LoginView loginView;
    private LoginModel loginModel;
    private Context context;

    public LoginPresenter(Context context, LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel();
        this.context = context;
    }

    public void login(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginModel.login(context, username, password, this);
    }

    public void onDestroy() {
        loginView = null;
    }

    /*Model层接口回调*/
    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
