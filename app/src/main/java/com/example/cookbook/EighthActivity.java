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

public class EighthActivity extends AppCompatActivity {
    Intent gotoninth,gototenth,gotoeleventh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);
        Intent callthird = getIntent();
        String food = callthird.getStringExtra("food");
        if (food.contains("Breakfast")) {
            ListView listViewnonveg = (ListView) findViewById(R.id.listview_nonveg);
            final ArrayAdapter<CharSequence> adapterbreakfastnonveg = ArrayAdapter.createFromResource(this,
                    R.array.breakfastnonveg,
                    android.R.layout.simple_list_item_1);
            listViewnonveg.setAdapter(adapterbreakfastnonveg);
            listViewnonveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterbreakfastnonveg.getItem(position);
                    gotoninth = new Intent();
                    gotoninth.setClass(getApplicationContext(), NinthActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gotoninth.putExtra("position", position);
                    gotoninth.putExtra("item", x);
                    startActivity(gotoninth);
                }
            });
        }

        if (food.contains("Lunch")) {
            ListView listViewnonveg = (ListView) findViewById(R.id.listview_nonveg);
            final ArrayAdapter<CharSequence> adapterlunchnonveg = ArrayAdapter.createFromResource(this,
                    R.array.lunchnonveg,
                    android.R.layout.simple_list_item_1);
            listViewnonveg.setAdapter(adapterlunchnonveg);
            listViewnonveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterlunchnonveg.getItem(position);
                    gototenth = new Intent();
                    gototenth.setClass(getApplicationContext(),TenthActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gototenth.putExtra("position", position);
                    gototenth.putExtra("item", x);
                    startActivity(gototenth);
                }
            });
        }

        if (food.contains("Dinner")) {
            ListView listViewnonveg = (ListView) findViewById(R.id.listview_nonveg);
            final ArrayAdapter<CharSequence> adapterdinnernonveg = ArrayAdapter.createFromResource(this,
                    R.array.dinnernonveg,
                    android.R.layout.simple_list_item_1);
            listViewnonveg.setAdapter(adapterdinnernonveg);
            listViewnonveg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence x = adapterdinnernonveg.getItem(position);
                    gotoeleventh = new Intent();
                    gotoeleventh.setClass(getApplicationContext(), EleventhActivity.class);
                    Toast.makeText(getApplicationContext(), "You chose " + x, Toast.LENGTH_SHORT).show();
                    gotoeleventh.putExtra("position", position);
                    gotoeleventh.putExtra("item", x);
                    startActivity(gotoeleventh);
                }
            });
        }
    }
}
