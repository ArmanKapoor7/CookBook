package com.example.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class phoneverification extends AppCompatActivity {

    String verification_code_by_system;
    EditText verification_code_entered_by_the_user;
    Button verify_btn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    String phoneNo, photo;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button resend;
    public static int verification;

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete();
        currentUser = null;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverification);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }

        resend = findViewById(R.id.resendbtn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resend.setVisibility(View.VISIBLE);
            }
        },30000);

        verification_code_entered_by_the_user = findViewById(R.id.verification_code_entered_by_user);
        verify_btn = findViewById(R.id.verifybtn);

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        firstname= getIntent().getStringExtra("firstname");
        lastname = getIntent().getStringExtra("lastname");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        phoneNo = getIntent().getStringExtra("phone");
        photo = getIntent().getStringExtra("photo");
        sendVerificationCodetoUser(phoneNo);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodetoUser(phoneNo);
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = verification_code_entered_by_the_user.getText().toString();
                closeKeyboard();

                if (code.isEmpty() || code.length() < 6) {
                    verification_code_entered_by_the_user.setError("Wrong OTP...");
                    verification_code_entered_by_the_user.requestFocus();
                    return;
                }
                openPopupWindow();
                verifyCode(code);
            }
        });
    }

    private void openPopupWindow() {
        Intent popup = new Intent(getApplicationContext(), popUpWindow.class);
        startActivity(popup);
    }

    private void sendVerificationCodetoUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder()
                        .setPhoneNumber("+91" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code_by_system = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    openPopupWindow();
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                popUpWindow.fa.finish();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                currentUser.delete();
                Toast.makeText(phoneverification.this, e.getMessage() +", Please resend OTP.", Toast.LENGTH_SHORT).show();
            }
        };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_code_by_system, code);
        signintheuserbycredentials(credential);
    }

    private void signintheuserbycredentials(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(phoneverification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserHelperClass helperClass = new UserHelperClass(firstname,lastname,username,phoneNo,email,password,photo);
                    reference.child(username).setValue(helperClass);
                    saveEmail();
                    popUpWindow.fa.finish();
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    popUpWindow.fa.finish();
                    resend.setVisibility(View.VISIBLE);
                    Toast.makeText(phoneverification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete();
        currentUser = null;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void saveEmail() {

        sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("savedEmail",email);
        //editor.putInt("signin",user1);
        editor.apply();

        //  Toast.makeText(getApplicationContext(), email.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
    }
}

