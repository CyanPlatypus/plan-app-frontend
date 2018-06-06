package com.plan_app.android_plan_app.data.source;


import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.UserCredentials;

import io.realm.Realm;

public class UserRepository {

    private UserRepository INSTANCE = null;

    private UserRepository() {

    }

    public UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public void getUser(@NonNull String email, GetUserCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        UserCredentials user = realm.where(UserCredentials.class).equalTo("email", email).findFirst();
        if (user == null) {
            callback.onUserNotFound();
        }
        else {
            callback.onUserFound(user);
        }
        realm.close();
    }

    public void addUser(@NonNull UserCredentials user, AddUserCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(UserCredentials.class).equalTo("email", user.getEmail()).findFirst() != null) {
            callback.onUserExists();
            realm.close();
            return;
        }
        realm.executeTransaction(realm1 -> {
            realm1.insert(user);
        });
        realm.close();
        callback.onSuccess();
    }

    public void deleteUser(@NonNull String email) {
        Realm realm = Realm.getDefaultInstance();
        UserCredentials user = realm.where(UserCredentials.class).equalTo("email", email).findFirst();
        if (user != null) {
            realm.executeTransaction(realm1 -> {
                user.deleteFromRealm();
            });
        }
        realm.close();
    }


    interface GetUserCallback {
        void onUserFound(UserCredentials user);

        void onUserNotFound();
    }

    interface AddUserCallback {
        void onSuccess();

        void onUserExists();
    }
}
