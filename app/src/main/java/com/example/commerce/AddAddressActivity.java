package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Address;
    private EditText City;
    private EditText Code;
    private EditText Number;
    private Button AddAddressbtn;
    private Toolbar Toolbar;

    private FirebaseFirestore Store;
    private FirebaseAuth Auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Name=findViewById(R.id.add_name);
        Address=findViewById(R.id.add_address);
        City=findViewById(R.id.add_city);
        Code=findViewById(R.id.add_code);
        Number=findViewById(R.id.add_phone);
        AddAddressbtn=findViewById(R.id.ad_add_address);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();
        Toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AddAddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Name.getText().toString();
                String address=Address.getText().toString();
                String city=City.getText().toString();
                String code=Code.getText().toString();
                String number=Number.getText().toString();
                String final_address="";

                if(!name.isEmpty()){
                    final_address+=name+", ";
                }
                if(!address.isEmpty()){
                    final_address+=address+", ";
                }
                if(!city.isEmpty()){
                    final_address+=city+", ";
                }
                if(!code.isEmpty()){
                    final_address+=code+", ";
                }
                if(!number.isEmpty()){
                    final_address+=number+", ";
                }
                Map<String,String> Map=new HashMap<>();
                Map.put("address",final_address);
                if(!name.isEmpty() && !address.isEmpty() && !city.isEmpty() && !code.isEmpty() && !number.isEmpty()){
                Store.collection("Users").document(Auth.getCurrentUser().getUid())
                        .collection("Address").add(Map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                }
                else {
                    Toast.makeText(AddAddressActivity.this,"Please fill empty field",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}