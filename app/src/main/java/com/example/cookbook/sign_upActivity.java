package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_upActivity extends AppCompatActivity {


    TextInputLayout fname, lname, uname, regmail, regpassword, regphone;
    Button signin, signup;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    ImageView logo;
    TextView logotext, signintext;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor2, editor3;
    SharedPreferences sharedPreferences2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }


        logo = findViewById(R.id.logo);
        logotext = findViewById(R.id.logotext);
        signintext = findViewById(R.id.signintext);

        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        uname = findViewById(R.id.username);
        regmail = findViewById(R.id.email);
        regpassword = findViewById(R.id.password);
        regphone = findViewById(R.id.phone);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        mAuth = FirebaseAuth.getInstance();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_upActivity.this, sign_inActivity.class);
                Pair[] pair = new Pair[7];

                pair[0] = new Pair<View, String>(logo, "logoname");
                pair[1] = new Pair<View, String>(logotext, "logotext");
                pair[2] = new Pair<View, String>(signintext, "signintext");
                pair[3] = new Pair<View, String>(regmail, "emailtext");
                pair[4] = new Pair<View, String>(regpassword, "passwordtext");
                pair[5] = new Pair<View, String>(signup, "btntext");
                pair[6] = new Pair<View, String>(signin, "signuptext");

                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(sign_upActivity.this, pair);
                }
                startActivity(intent, options.toBundle());
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validatefirstname() | !validatelastname() | !validateusername() | !validateemail() | !validatepassword() | !validatephone()) {
                    return;
                }

                openPopupWindow();
                closeKeyboard();

                String firstname = fname.getEditText().getText().toString();
                String lastname = lname.getEditText().getText().toString();
                String username = uname.getEditText().getText().toString();
                String email = regmail.getEditText().getText().toString();
                String password = regpassword.getEditText().getText().toString();
                String phone = regphone.getEditText().getText().toString();
                String photo = null;
                save_name(firstname,lastname,username);
                savephoto(photo);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(sign_upActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    popUpWindow.fa.finish();
                                    Intent intent = new Intent(sign_upActivity.this, phoneverification.class);
                                    intent.putExtra("firstname", firstname);
                                    intent.putExtra("lastname", lastname);
                                    intent.putExtra("username", username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("photo", photo);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    popUpWindow.fa.finish();
                                    Toast.makeText(sign_upActivity.this, "Account not created", Toast.LENGTH_SHORT).show();
                                    Log.d("key", String.valueOf(task.getException()));
                                }

                            }
                        });


                //UserHelperClass helperClass = new UserHelperClass(firstname,lastname,username,phone,email,password);
                //reference.child(username).setValue(helperClass);

                //Intent intent = new Intent(getApplicationContext(), sign_inActivity.class);
                //startActivity(intent);

            }
        });
    }

    private void openPopupWindow() {
        Intent popup = new Intent(getApplicationContext(), popUpWindow.class);
        startActivity(popup);
    }

    private void save_name(String user_fname, String user_lname, String user_uname) {
        sharedPreferences2 = getSharedPreferences("shared_prefs2", MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();

        editor2.putString("savedfname", user_fname);
        editor2.putString("savedlname", user_lname);
        editor2.putString("saveduname", user_uname);
        editor2.apply();

    }

    private void savephoto(String user_photo) {
        sharedPreferences3 = getSharedPreferences("shared_prefs3", MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();

        editor3.putString("savedphoto", user_photo);
        editor3.apply();
    }

    private boolean validatefirstname() {
        String val = fname.getEditText().getText().toString();

        if (val.isEmpty()) {
            fname.setError("Field cannot be empty");
            return false;
        } else {
            fname.setError(null);
            fname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatelastname() {
        String val = lname.getEditText().getText().toString();

        if (val.isEmpty()) {
            lname.setError("Field cannot be empty");
            return false;
        } else {
            lname.setError(null);
            lname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateusername() {
        String val = uname.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            uname.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            uname.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            uname.setError("White Spaces are not allowed");
            return false;
        } else {
            uname.setError(null);
            uname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateemail() {
        String val = regmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regmail.setError("Inavalid email id");
            return false;
        } else {
            regmail.setError(null);
            regmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatepassword() {
        String val = regpassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regpassword.setError("Password should contain atleast 4 characters including 1 special character");
            return false;
        } else {
            regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatephone() {
        String val = regphone.getEditText().getText().toString();

        if (val.isEmpty()) {
            regphone.setError("Field cannot be empty");
            return false;
        } else {
            regphone.setError(null);
            regphone.setErrorEnabled(false);
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
