package com.example.leedayeon.listdetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "RegisterActivity";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    private Button buttonSignUp;

    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mRePasswordInputLayout;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        toolbar = (Toolbar)findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        mEmailInputLayout = (TextInputLayout) findViewById(R.id.input_email);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.input_password);
        mRePasswordInputLayout = (TextInputLayout) findViewById(R.id.input_re_password);

        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            //profile activity here
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//        }

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRePassword = (EditText) findViewById(R.id.editTextPassword2);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(this);


    }

    private void registerUser() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextRePassword.getText().toString();

        if (!(showError(email, password, password2))) {
            return;
        }


        progressDialog.setMessage("회원가입을 진행중입니다...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (task.isSuccessful()) {

                                    //user is successfully registered and logged in
                                    //we will start the profile activity here

//                                    Toast.makeText(RegisterActivity.this, "회원가입이 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                                    mEmailInputLayout.setError("회원가입이 성공하였습니다.");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), SignActivity.class));
                                } else {
//                                    Toast.makeText(RegisterActivity.this, "회원가입이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                    mEmailInputLayout.setError(null);
                                    mPasswordInputLayout.setError(null);
                                    mRePasswordInputLayout.setError(null);
                                    mEmailInputLayout.setError("회원가입이 실패하였습니다. 다시 시도해주세요.");
                                }
                            }
                        }
                );

        progressDialog.hide();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignUp) {
            registerUser();
        }

    }

    public boolean showError(String e, String p, String p2) {
        mEmailInputLayout.setError(null);
        mPasswordInputLayout.setError(null);
        mRePasswordInputLayout.setError(null);

        if (TextUtils.isEmpty(e)) {
//            Toast.makeText(LoginActivity.this, "email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            mEmailInputLayout.setError("email을 입력해주세요.");
            return false;
        }
        if (TextUtils.isEmpty(p)) {
//            Toast.makeText(LoginActivity.this, "password를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mPasswordInputLayout.setError("password를 입력해주세요.");
            return false;
        }
        if (TextUtils.isEmpty(p2)) {
            mRePasswordInputLayout.setError("password를 재입력해주세요.");
        }
        if (!TextUtils.equals(p, p2)) {
            mRePasswordInputLayout.setError("password가 일치하지 않습니다. 다시 입력해주세요");
        }

        return true;
    }

}
