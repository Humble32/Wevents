package com.example.commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String name=getIntent().getStringExtra("name");
        String type=getIntent().getStringExtra("type");
        Toast.makeText(this,""+name,Toast.LENGTH_SHORT).show();
    }
}