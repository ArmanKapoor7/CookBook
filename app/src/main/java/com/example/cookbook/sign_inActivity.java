package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.cookbook.phoneverification.verification;

public class sign_inActivity extends AppCompatActivity {

    TextInputLayout email, password;
    private FirebaseAuth firebaseAuth;
    String userenteredPassword;
    String userenteredEmail, user_password, user_uname, user_fname, user_photo, user_lname;
    Button signup, signinbtn, forgetpassword;
    ImageView logo;
    TextView logotext, signintext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor, editor2;
    ProgressBar progressBar;
    DatabaseReference reference;
    private SharedPreferences sharedPreferences2;
    public static int j;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.from_right, R.anim.to_left);
            finish();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }

        firebaseAuth = FirebaseAuth.getInstance();

        logo = findViewById(R.id.logo);
        logotext = findViewById(R.id.logotext);
        signintext = findViewById(R.id.signintext);
        email = findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                email.setErrorEnabled(false);
            }
        });
        password = findViewById(R.id.password);
        signinbtn = findViewById(R.id.signinbtn);
        signup = findViewById(R.id.signup);


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkemail() | !checkpassword())
                    return;
                else {
                    openPopupWindow();
                    closeKeyboard();
                    isuser();

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignup();
            }
        });

        forgetpassword = findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_inActivity.this, resetPassword.class);

                Pair[] pair = new Pair[3];

                pair[0] = new Pair<View, String>(logo, "logoname");
                pair[1] = new Pair<View, String>(email, "emailtext");
                pair[2] = new Pair<View, String>(signinbtn, "btntext");

                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(sign_inActivity.this, pair);
                }
                startActivity(intent, options.toBundle());

            }
        });

    }


    private void gotoSignup() {
        Intent intent = new Intent(sign_inActivity.this, sign_upActivity.class);

        Pair[] pair = new Pair[7];

        pair[0] = new Pair<View, String>(logo, "logoname");
        pair[1] = new Pair<View, String>(logotext, "logotext");
        pair[2] = new Pair<View, String>(signintext, "signintext");
        pair[3] = new Pair<View, String>(email, "emailtext");
        pair[4] = new Pair<View, String>(password, "passwordtext");
        pair[5] = new Pair<View, String>(signinbtn, "btntext");
        pair[6] = new Pair<View, String>(signup, "signuptext");

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(sign_inActivity.this, pair);
        }
        startActivity(intent, options.toBundle());

    }

    private void isuser() {
        userenteredEmail = email.getEditText().getText().toString().trim();
        userenteredPassword = password.getEditText().getText().toString().trim();


        firebaseAuth.signInWithEmailAndPassword(userenteredEmail, userenteredPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            email.setError(null);
                            email.setErrorEnabled(false);
                            saveEmail();
                            reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        if (userenteredEmail.equals(snapshot1.child("email").getValue(String.class))) {
                                            user_password = snapshot1.child("password").getValue(String.class);
                                            user_uname = snapshot1.child("username").getValue(String.class);
                                            user_fname = snapshot1.child("firstname").getValue(String.class);
                                            user_lname = snapshot1.child("lastname").getValue(String.class);
                                            user_photo = snapshot1.child("profileImage").getValue(String.class);
                                            Toast.makeText(sign_inActivity.this, "1" +user_fname + user_lname + user_uname, Toast.LENGTH_SHORT).show();
                                            save_name();
                                            savephoto();
                                            //Toast.makeText(sign_inActivity.this, "2"+user_fname + user_lname + user_uname, Toast.LENGTH_SHORT).show();
                                            if (!userenteredPassword.equals(user_password)) {
                                                reference.child(user_uname).child("password").setValue(userenteredPassword);
                                                //Toast.makeText(sign_inActivity.this, "3" +user_uname + " " + user_password + " " + userenteredPassword, Toast.LENGTH_SHORT).show();
                                            }

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                                    j=1;
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            },100);
                                            popUpWindow.fa.finish();
                                            //Toast.makeText(sign_inActivity.this, "2" +name + " " + psword + " ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if(user_fname==null){
                                        popUpWindow.fa.finish();
                                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        currentUser.delete();
                                        Toast.makeText(sign_inActivity.this, "There was a problem with your verification. Please create your account again.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            popUpWindow.fa.finish();
                            Toast.makeText(sign_inActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


      /*  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("username").equalTo(userenteredUsername);




        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordfromDB = dataSnapshot.child(userenteredUsername).child("password").getValue(String.class);

                    if (passwordfromDB.equals(userenteredPassword)){

                        String fname = dataSnapshot.child(userenteredUsername).child("firstname").getValue(String.class);
                        String lname = dataSnapshot.child(userenteredUsername).child("lastname").getValue(String.class);
                        String uname = dataSnapshot.child(userenteredUsername).child("username").getValue(String.class);
                        String phone = dataSnapshot.child(userenteredUsername).child("phone").getValue(String.class);
                        String email = dataSnapshot.child(userenteredUsername).child("email").getValue(String.class);
                        String password = dataSnapshot.child(userenteredUsername).child("password").getValue(String.class);

                        user1 = 1;
                        saveUsername();

                        Intent intent = new Intent(getApplicationContext(),Profile.class);

                        intent.putExtra("fname",fname);
                        intent.putExtra("lname",lname);
                        intent.putExtra("uname",uname);
                        intent.putExtra("phone",phone);
                        intent.putExtra("email",email);
                        intent.putExtra("password",password);

                        startActivity(intent);
                        finish();
                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }

                }
                else {
                    username.setError("Username does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
*/

    }

    private void openPopupWindow() {
        Intent popup = new Intent(getApplicationContext(), popUpWindow.class);
        startActivity(popup);
    }

    private void save_name() {
        sharedPreferences2 = getSharedPreferences("shared_prefs2", MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();

        editor2.putString("savedfname", user_fname);
        editor2.putString("savedlname", user_lname);
        editor2.putString("saveduname", user_uname);
        editor2.apply();

    }

    private void savephoto() {
        sharedPreferences3 = getSharedPreferences("shared_prefs3", MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();

        editor3.putString("savedphoto", user_photo);
        editor3.apply();
    }

    private void saveEmail() {

        sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("savedEmail", email.getEditText().getText().toString());
        //editor.putInt("signin",user1);
        editor.apply();

        //  Toast.makeText(getApplicationContext(), email.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public boolean checkemail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Inavalid email id");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    public boolean checkpassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
