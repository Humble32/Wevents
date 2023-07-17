package com.example.commerce.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.commerce.AddAddressActivity;
import com.example.commerce.AddressActivity;
import com.example.commerce.ProfileActivity;
import com.example.commerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ContactActivity extends AppCompatActivity {

    private ImageView Contactback;
    private RelativeLayout Callbtn;
    private RelativeLayout Sendmail;
    FirebaseFirestore Store;
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Contactback=findViewById(R.id.contact_back);
        Callbtn=findViewById(R.id.call_us);
        Sendmail=findViewById(R.id.send_mail);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();

        Contactback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ContactActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        Callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+39 351 6310375"));
                startActivity(intent);

            }
        });
        Sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]  TO_EMAILS={"example@gmail.com"};
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mail to"));
                intent.putExtra(Intent.EXTRA_EMAIL, TO_EMAILS);

                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
                intent.putExtra(Intent.EXTRA_TEXT,"Body");

                startActivity(Intent.createChooser(intent,"Choose one application"));
            }
        });
    }
}