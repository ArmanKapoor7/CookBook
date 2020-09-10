package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class FourthActivity extends AppCompatActivity {
    Intent gotofifth,gotosixth,gotoseventh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Intent callthird = getIntent();
        String food = callthird.getStringExtra("food");
        if (food.contains("Breakfast")) {
            ListView listViewveg = (ListView) findViewById(R.id.listview_veg);
            final ArrayAdapter<CharSequence> adapterbreakfastveg = ArrayAdapter.createFromResource(this,
                    R.array.breakfastveg,
                    android.R.layout.simple_list_item_1);
            listViewveg.setAdapter(adapterbreakfastveg);
            listViewveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterbreakfastveg.getItem(position);
                    gotofifth = new Intent();
                    gotofifth.setClass(getApplicationContext(), FifthActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gotofifth.putExtra("position", position);
                    gotofifth.putExtra("item", x);
                    startActivity(gotofifth);
                }
            });
        }

        if (food.contains("Lunch")) {
            ListView listViewveg = (ListView) findViewById(R.id.listview_veg);
            final ArrayAdapter<CharSequence> adapterlunchveg = ArrayAdapter.createFromResource(this,
                    R.array.lunchveg,
                    android.R.layout.simple_list_item_1);
            listViewveg.setAdapter(adapterlunchveg);
            listViewveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterlunchveg.getItem(position);
                    gotosixth = new Intent();
                    gotosixth.setClass(getApplicationContext(), SixthActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gotosixth.putExtra("position", position);
                    gotosixth.putExtra("item", x);
                    startActivity(gotosixth);
                }
            });
        }

        if (food.contains("Dinner")) {
            ListView listViewveg = (ListView) findViewById(R.id.listview_veg);
            final ArrayAdapter<CharSequence> adapterdinnerveg = ArrayAdapter.createFromResource(this,
                    R.array.dinnerveg,
                    android.R.layout.simple_list_item_1);
            listViewveg.setAdapter(adapterdinnerveg);
            listViewveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterdinnerveg.getItem(position);
                    gotoseventh = new Intent();
                    gotoseventh.setClass(getApplicationContext(), SeventhActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gotoseventh.putExtra("position", position);
                    gotoseventh.putExtra("item", x);
                    startActivity(gotoseventh);
                }
            });
        }
    }
}
