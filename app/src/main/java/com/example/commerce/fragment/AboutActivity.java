package com.example.commerce.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.commerce.ProfileActivity;
import com.example.commerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AboutActivity extends AppCompatActivity {

    private ImageView Aboutback;
    private  FirebaseFirestore Store;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Aboutback=findViewById(R.id.about_back);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();

        Aboutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}