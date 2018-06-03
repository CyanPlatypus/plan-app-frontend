package com.plan_app.android_plan_app.sign_up;

import com.plan_app.android_plan_app.BasePresenter;
import com.plan_app.android_plan_app.BaseView;

/**
 * Created by brandstein on 4/30/18.
 */

public interface SignInSignUpContract {

    interface View extends BaseView<Presenter> {
        void displayLoginError(String message);
        void displayEmailError(String message);
        void displayPasswordError(String message);

        void fatalError(String message);

        boolean isActive();

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {
        void signUpUser(String login, String email, String pass);
        void signInUser(String email, String pass);
    }
}
