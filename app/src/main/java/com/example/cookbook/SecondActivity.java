package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }
    public void breakfast(View v)  {
        String option =String.format("Breakfast");
        Intent gotothird = new Intent();
        gotothird.setClass(this,ThirdActivity.class);
        gotothird.putExtra("choice",option);
        Toast.makeText(getApplicationContext(),"You chose Breakfast",Toast.LENGTH_SHORT).show();
        startActivity(gotothird);
    }
    public void lunch(View v)  {
        String option =String.format("Lunch");
        Intent gotothird = new Intent();
        gotothird.setClass(this,ThirdActivity.class);
        gotothird.putExtra("choice",option);
        Toast.makeText(getApplicationContext(),"You chose Lunch",Toast.LENGTH_SHORT).show();
        startActivity(gotothird);
    }

    public void dinner(View v)  {
        String option =String.format("Dinner");
        Intent gotothird = new Intent();
        gotothird.setClass(this,ThirdActivity.class);
        gotothird.putExtra("choice",option);
        Toast.makeText(getApplicationContext(),"You chose Dinner",Toast.LENGTH_SHORT).show();
        startActivity(gotothird);
    }
}
