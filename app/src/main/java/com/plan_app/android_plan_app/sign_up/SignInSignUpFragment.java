package com.plan_app.android_plan_app.sign_up;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.plan_app.android_plan_app.R;

/**
 * Created by brandstein on 4/30/18.
 */

public class SignInSignUpFragment extends Fragment implements SignInSignUpContract.View {

    private SignInSignUpContract.Presenter mPresenter;

    private AutoCompleteTextView mLoginNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
//    private View mLoginFormView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signin_signup, container, false);

        mEmailView = root.findViewById(R.id.email);
        mLoginNameView = root.findViewById(R.id.name);
        mPasswordView = root.findViewById(R.id.password);
        mProgressView = getActivity().findViewById(R.id.login_progress);

        Button mSignInButton = root.findViewById(R.id.email_sign_in_button);

        Button mSignUpButton = root.findViewById(R.id.email_sign_up_button);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signInUser(mEmailView.getText().toString(),
                        mPasswordView.getText().toString());
            }
        });



        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signUpUser(mLoginNameView.getText().toString(),
                        mEmailView.getText().toString(),
                        mPasswordView.getText().toString());
            }
        });

        //mLoginFormView = findViewById(R.id.login_form);

        return root;
    }

    @Override
    public void setPresenter(@NonNull SignInSignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayLoginError(String message) {
        mLoginNameView.setError(message);
    }

    @Override
    public void displayEmailError(String message) {
        mEmailView.setError(message);
    }

    @Override
    public void displayPasswordError(String message) {
        mPasswordView.setError(message);
    }

    @Override
    public void fatalError(String message) {
        Snackbar.make(getView(), message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
