package com.startup.ecapp.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.startup.ecapp.data.model.LoggedInUser;
import com.startup.ecapp.ui.login.LoginActivity;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public final String TAG="LoginDataSource";
    boolean loginSuccess=false ;
    public Result<LoggedInUser> login(Context mContext,String username, String password) {


        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginSuccess =true;
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });



    //TODO: Check current user signed in then show screen
        try {
            // TODO: handle loggedInUser authentication
            if(loginSuccess) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Jane Doe");
                return new Result.Success<>(fakeUser);
            }
            else{
                Exception e=new Exception("Login Failed");
                throw e;
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}