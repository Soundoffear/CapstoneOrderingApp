package com.example.soundoffear.capstoneorderingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.utilities.InternetCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPasswordLogin";

    private FirebaseAuth mAuth;

    @BindView(R.id.home_screen_e_mail_login_input)
    EditText et_login_email_input;
    @BindView(R.id.home_screen_password_login_input)
    EditText et_login_password_input;
    @BindView(R.id.home_screen_login_button)
    Button btn_login;
    @BindView(R.id.home_screen_sign_up_button)
    Button btn_sign_up;
    @BindView(R.id.home_screen_exit_button)
    Button btn_exit;

    InternetCheck internetCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        internetCheck = new InternetCheck(getApplicationContext());

        internetCheck.execute();

        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void logIn(String email, String password) {
        //verify e-mail and password is not empty
        if (!validateInput(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "LogIn with email: Successful");
                            Toast.makeText(getApplicationContext(), R.string.logged_in, Toast.LENGTH_SHORT).show();

                            Intent startMainPageActivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(startMainPageActivity);
                        } else {
                            Log.w(TAG, "LogIn with email: Failed", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                        }

                        if (!task.isSuccessful()) {
                            Log.d(TAG, "LOGIN FAILED");
                        }
                    }
                });
    }

    private void createAccount(String email, String password) {
        if (!validateInput(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.account_created, Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailValidation(user);
                        }
                        if(!task.isSuccessful()) {
                            try{
                                throw task.getException();
                            } catch (FirebaseAuthActionCodeException fe) {
                                et_login_email_input.setError(getString(R.string.invalid_email));
                                et_login_email_input.requestFocus();
                            } catch (FirebaseAuthInvalidUserException fe) {
                                et_login_email_input.setError(getString(R.string.user_exists));
                                et_login_email_input.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });
    }

    private boolean validateInput(String email, String password) {

        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            et_login_email_input.setError("Required input");
            valid = false;
        } else {
            et_login_email_input.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            et_login_password_input.setError("Required input");
            valid = false;
        } else {
            et_login_password_input.setError(null);
        }
        return valid;
    }

    private void sendEmailValidation(FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String email = et_login_email_input.getText().toString();

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.mail_send) + email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.mail_not_send, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.home_screen_login_button) {
            logIn(et_login_email_input.getText().toString(), et_login_password_input.getText().toString());
        }
        if (id == R.id.home_screen_sign_up_button) {
            createAccount(et_login_email_input.getText().toString(), et_login_password_input.getText().toString());
        }
        if (id == R.id.home_screen_exit_button) {
            Toast.makeText(this, R.string.exiting_app, Toast.LENGTH_LONG).show();
        }

    }
}
