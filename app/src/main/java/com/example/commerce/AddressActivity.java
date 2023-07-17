package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.commerce.adapter.AddressAdapter;
import com.example.commerce.domain.Address;
import com.example.commerce.domain.BestSell;
import com.example.commerce.domain.Category;
import com.example.commerce.domain.Feature;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private RecyclerView AddressRecyclerView;
    private AddressAdapter AddressAdapter;
    private Button AddAddress;
    private Button paymentBtn;
    private List<Address> AddressList;
    private FirebaseFirestore Store;
    private FirebaseAuth Auth;
    String address="";
    private androidx.appcompat.widget.Toolbar Toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final Object obj=getIntent().getSerializableExtra("item");
        List<Items> itemsList= (ArrayList<Items>) getIntent().getSerializableExtra("itemList");
        //Toast.makeText(this, itemsList.get(null).getName(), Toast.LENGTH_SHORT).show();
        AddressRecyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);
        AddAddress=findViewById(R.id.add_address_btn);
        Toolbar=findViewById(R.id.address_toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Auth=FirebaseAuth.getInstance();
        Store=FirebaseFirestore.getInstance();
        AddressList=new ArrayList<>();
        AddressAdapter=new AddressAdapter(getApplicationContext(),AddressList,this);
        AddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AddressRecyclerView.setAdapter(AddressAdapter);
        Store.collection("Users").document(Auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc: task.getResult()){
                                Address address=doc.toObject(Address.class);
                                AddressList.add(address);
                                AddressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);

            }
        });
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount=0.0;
                String url="";
                String name="";

                if(obj instanceof Feature){
                    Feature f=(Feature) obj;
                    amount=f.getPrice();
                    url=f.getImg_url();
                    name=f.getName();
                }
                if(obj instanceof Category){
                    BestSell bs=(BestSell) obj;
                    amount=bs.getPrice();
                    url=bs.getImg_url();
                    name=bs.getName();
                }
                if(obj instanceof Items){
                    Items i=(Items) obj;
                    amount=i.getPrice();
                    url=i.getImg_url();
                    name=i.getName();
                }
                if(itemsList!=null && itemsList.size()>=0){
                    Intent intent=new Intent(AddressActivity.this,PaymentActivity.class);
                    intent.putExtra("itemsList", (Serializable) itemsList);
                    startActivity(intent);

                }else {
                    Intent intent=new Intent(AddressActivity.this,PaymentActivity.class);
                    intent.putExtra("amount",amount);
                    intent.putExtra("img_url",url);
                    intent.putExtra("name",name);
                    intent.putExtra("address",address);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void setAddress(String s) {
        address=s;
    }
}