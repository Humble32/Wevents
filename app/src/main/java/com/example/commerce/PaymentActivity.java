package com.example.commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity{
  private   TextView Total;
  private ImageView Paymentbtn;
  private  Button payBtn;
  private double amount=0.0;
  private  String name="";
  private String img_url="";
  private List<Items> itemsList;
  private FirebaseFirestore Store;
  private FirebaseAuth Auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amount=getIntent().getDoubleExtra("amount",0.0);
        name=getIntent().getStringExtra("name");
        img_url=getIntent().getStringExtra("img_url");
        List<Items> itemsList= (ArrayList<Items>) getIntent().getSerializableExtra("itemsList");
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();
        Total=findViewById(R.id.sub_total);
        payBtn=findViewById(R.id.pay_btn);
        Paymentbtn=findViewById(R.id.payment_back);

        Paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });

        if(itemsList!=null && itemsList.size()>0){
            amount=0.0;
            for(Items item: itemsList){
                amount+=item.getPrice();
            }
            Total.setText(amount+""+"€");

        }else {
            Total.setText(amount+""+"€");

        }
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Payment Successfully", Toast.LENGTH_SHORT).show();
                if(itemsList!=null && itemsList.size()>0){
                    for(Items items:itemsList){
                        Map<String,Object> Map=new HashMap<>();
                        Map.put("name",items.getName());
                        Map.put("img_url",items.getImg_url());
                        //Map.put("payment_id",s);
                        Store.collection("Users").document(Auth.getCurrentUser().getUid())
                                .collection("Orders").add(Map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Cart").document().delete();

                }else {
                    Map<String,Object> Map=new HashMap<>();
                    Map.put("name",name);
                    Map.put("img_url",img_url);
                    //Map.put("payment_id",s);
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Orders").add(Map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }
}