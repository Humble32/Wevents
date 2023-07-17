package com.example.commerce;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commerce.fragment.AboutActivity;
import com.example.commerce.fragment.ContactActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    /*private TextView UserName;
    private TextView UserEmail;*/
    private RelativeLayout Exitbtn;
    private RelativeLayout Linkbtn;
    private RelativeLayout Contactbtn;
    private RelativeLayout Aboutbtn;
    private RelativeLayout Ordersbtn;
    private FirebaseFirestore Store;
    private FirebaseAuth Auth;
    private BottomNavigationView bottomNavigationView;
    private  AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /*UserName=findViewById(R.id.user_name);
        UserEmail=findViewById(R.id.user_email);*/
        builder =new AlertDialog.Builder(this);
        Exitbtn=findViewById(R.id.logout_btn);
        Linkbtn=findViewById(R.id.link_visit);
        Contactbtn=findViewById(R.id.contact_us);
        Aboutbtn=findViewById(R.id.about_us);
        Ordersbtn=findViewById(R.id.purchases);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();


        Exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Alert!")
                        .setMessage("Do you want to disconnect you?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Auth.signOut();
                                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        })
                        .show();
            }
        });
        
        Linkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://saw21.dibris.unige.it/~S4549372/Finale/index.php");
            }
        });

        Contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, ContactActivity.class);
                startActivity(intent);

            }
        });

        Aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        Ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.person);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id == R.id.person) {
                return true;
            }else if(id == R.id.fav){
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                finish();
                return true;
            }else if(id == R.id.cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
                return true;
            }else if(id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
                return true;
            }
            return false;
        });
    }

    private void gotoUrl(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}