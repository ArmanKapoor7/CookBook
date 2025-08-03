package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.cookbook.sign_inActivity.j;


public class Profile extends AppCompatActivity {

    String user_fname;
    String user_lname;
    String user_uname;
    String user_email;
    String user_phone;
    String user_password;
    String userenteredEmail, user_photo;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    TextView name;
    TextInputLayout fname;
    TextInputLayout lname;
    TextView uname;
    TextInputLayout email;
    TextInputLayout phone;
    TextInputLayout password;
    DatabaseReference reference;
    public static int i;
    CircleImageView imageView;
    ImageView editimage;
    StorageReference storageReference;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(i!=1&&j!=1){
            openPopupWindow();
            Log.d("value", String.valueOf(i));
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }


        loadUseremail();


        name = findViewById(R.id.name);
        fname = findViewById(R.id.firstname);
        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname.setError(null);
                fname.setErrorEnabled(false);
            }
        });
        lname = findViewById(R.id.lastname);
        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lname.setError(null);
                lname.setErrorEnabled(false);
            }
        });
        uname = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                email.setErrorEnabled(false);
            }
        });
        password = findViewById(R.id.password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setError(null);
                password.setErrorEnabled(false);
            }
        });
        Button update = findViewById(R.id.update);
        Button logout = findViewById(R.id.logout);

        imageView = findViewById(R.id.profileimage);
        editimage = findViewById(R.id.editImage);
        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent opengallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                CropImage.activity().setAspectRatio(9, 10).start(Profile.this);
                //startActivityForResult(opengallery, 1000);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("profile");
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (userenteredEmail.equals(snapshot1.child("email").getValue(String.class))) {
                        user_fname = snapshot1.child("firstname").getValue(String.class);
                        user_lname = snapshot1.child("lastname").getValue(String.class);
                        user_uname = snapshot1.child("username").getValue(String.class);
                        user_email = snapshot1.child("email").getValue(String.class);
                        user_phone = snapshot1.child("phone").getValue(String.class);
                        user_password = snapshot1.child("password").getValue(String.class);
                        user_photo = snapshot1.child("profileImage").getValue(String.class);
                        popUpWindow.fa.finish();
                        i=1;
                        Log.d("value", String.valueOf(i));
                        name.setText(user_fname.concat(" ").concat(user_lname));
                        uname.setText("@".concat(user_uname));
                        fname.getEditText().setText(user_fname);
                        lname.getEditText().setText(user_lname);
                        email.getEditText().setText(user_email);
                        phone.getEditText().setText(user_phone);
                        password.getEditText().setText(user_password);
                        if(user_photo!=null){
                            Picasso.get().load(user_photo).into(imageView);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                popUpWindow.fa.finish();
            }
        });




        /* Intent intent = getIntent();
        user_fname = intent.getStringExtra("fname");
        user_lname = intent.getStringExtra("lname");
        user_uname = intent.getStringExtra("uname");
        user_email = intent.getStringExtra("email");
        user_phone = intent.getStringExtra("phone");
        user_password = intent.getStringExtra("password"); */


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                name.setText("Full Name");
                uname.setText("@username");
                fname.getEditText().setText("");
                lname.getEditText().setText("");
                email.getEditText().setText("");
                phone.getEditText().setText("");
                password.getEditText().setText("");
                Intent intent2 = new Intent(getApplicationContext(), sign_inActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstnamechanged() || lastnamechanged()) {
                    Toast.makeText(Profile.this, "Data successfully updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Profile.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            openPopupWindow();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri imageUri = result.getUri();
            //imageView.setImageURI(imageUri);
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            uploadtofirebase(imageUri);
        }
    }

    protected void uploadtofirebase(Uri imageUri) {

        StorageReference ref = storageReference.child(user_fname +"."+ getfileextension(imageUri));
        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       reference.child(user_uname).child("profileImage").setValue(uri.toString());
                       savephoto(uri);
                       Log.d("value2", uri.toString());
                       popUpWindow.fa.finish();
                       imageView.bringToFront();
                       Toast.makeText(Profile.this, "Profile image Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void savephoto(Uri imageUri) {
        sharedPreferences3 = getSharedPreferences("shared_prefs3", MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();

        editor3.putString("savedphoto", imageUri.toString() );
        editor3.apply();
    }

    private String getfileextension(Uri imageUri) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(imageUri));
        return extension;
    }

    private void openPopupWindow() {
        Intent popup = new Intent(getApplicationContext(), popUpWindow.class);
        startActivity(popup);
    }

    public void back(View v) {
        onBackPressed();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }

    private boolean lastnamechanged() {
        if (!user_lname.equals(lname.getEditText().getText().toString())) {
            if (!validatelastname()) {
                return false;
            } else {
                reference.child(user_uname).child("lastname").setValue(lname.getEditText().getText().toString());
                user_lname = lname.getEditText().getText().toString();
                name.setText(user_fname.concat(" ").concat(user_lname));
                return true;
            }

        } else
            return false;

    }

    private boolean firstnamechanged() {
        if (!user_fname.equals(fname.getEditText().getText().toString())) {
            if (!validatefirstname()) {
                return false;
            } else {
                reference.child(user_uname).child("firstname").setValue(fname.getEditText().getText().toString());
                user_fname = fname.getEditText().getText().toString();
                name.setText(user_fname.concat(" ").concat(user_lname));
                return true;
            }

        } else
            return false;

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void loadUseremail() {
        sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        userenteredEmail = sharedPreferences.getString("savedEmail", "");

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

    private boolean validatepassword() {
        String val = password.getEditText().getText().toString();
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
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password should contain atleast 4 characters including 1 special character");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatephone() {
        String val = phone.getEditText().getText().toString();

        if (val.isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }

    }
}
