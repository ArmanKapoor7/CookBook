package com.example.cookbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
    public void veg(View v) {
        Intent callsecond = getIntent();
        String choice = callsecond.getStringExtra("choice");
        Toast.makeText(getApplicationContext(), "You chose " + choice + " Veg", Toast.LENGTH_SHORT).show();
        Intent gotofourth = new Intent();
        if(choice.contains("Breakfast")) {
            String item =("Breakfast");
            gotofourth.setClass(this, FourthActivity.class);
            gotofourth.putExtra("food",item);
            startActivity(gotofourth);
        }
        else if(choice.contains("Lunch")) {
            String item =("Lunch");
            gotofourth.setClass(this, FourthActivity.class);
            gotofourth.putExtra("food",item);
            startActivity(gotofourth);
        }
        else if(choice.contains("Dinner")) {
            String item =("Dinner");
            gotofourth.setClass(this, FourthActivity.class);
            gotofourth.putExtra("food",item);
            startActivity(gotofourth);

        }
    }

    public void nonveg(View v){
        Intent callsecond = getIntent();
        String choice = callsecond.getStringExtra("choice");
        Toast.makeText(getApplicationContext(),"You chose "+choice+" Non-Veg",Toast.LENGTH_SHORT).show();
        Intent gotoeighth = new Intent();
        if(choice.contains("Breakfast")) {
            String item =("Breakfast");
            gotoeighth.setClass(this, EighthActivity.class);
            gotoeighth.putExtra("food",item);
            startActivity(gotoeighth);
        }
        else if(choice.contains("Lunch")) {
            String item =("Lunch");
            gotoeighth.setClass(this, EighthActivity.class);
            gotoeighth.putExtra("food",item);
            startActivity(gotoeighth);
        }
        else if(choice.contains("Dinner")) {
            String item =("Dinner");
            gotoeighth.setClass(this, EighthActivity.class);
            gotoeighth.putExtra("food",item);
            startActivity(gotoeighth);

        }
    }


    }

