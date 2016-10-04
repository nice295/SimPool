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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "FacebookLogin";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignin;
    private TextView textSignup;
    private ProgressDialog progressDialog;
    private  LoginButton loginButton;

    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mPasswordInputLayout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference();
    private CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign);

        mEmailInputLayout = (TextInputLayout) findViewById(R.id.input_email);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.input_password);

        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignIn);
        textSignup = (TextView) findViewById(R.id.textSignUp);

        loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        mCallbackManager = CallbackManager.Factory.create();
        findViewById(R.id.button_facebook_signout).setOnClickListener(this);

        buttonSignin.setOnClickListener(this);
        textSignup.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d(TAG, "이름 : " + user.getDisplayName());
                    Log.d(TAG, "이메일 : " + user.getEmail());
//                    Log.d(TAG, "사진 : " + user.getPhotoUrl().toString());

                    try{
                        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
                    }catch (NullPointerException e){
                        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail(), null);
                    }
//                    if(user.getPhotoUrl().toString() == null){
//                        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail(), null);
//                    }
//                    else{
//                        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
//                    }
//                    Intent intent = new Intent(SignActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
            }
        };

        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (mUser == null) {
//            Intent intent = new Intent(getActivity(), FacebookLoginActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        }

        if (mUser == null) {
            return;
        }

        //mProgressBar.setVisibility(View.VISIBLE);



        //facebook login
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


    }
    private void writeNewUser(String userId, String name, String email, String imageUrl) {
        User user = new User(name, email, imageUrl);

        mDatabase.child("users").child(userId).setValue(user);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void userLogin(){

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!(showError(email, password))){

            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity

                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
//                            Toast.makeText(LoginActivity.this, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            mEmailInputLayout.setError("회원 정보가 일치하지 않습니다.");
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if (view == buttonSignin) {
            userLogin();
        }
        if (view == textSignup) {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
        if (view == loginButton){


        }
        if(view.getId() == R.id.button_facebook_signout){
            signOut();
        }

    }
    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

    }
    public boolean showError(String e, String p){
        mEmailInputLayout.setError(null);
        mPasswordInputLayout.setError(null);

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
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}