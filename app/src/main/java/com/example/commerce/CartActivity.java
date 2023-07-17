package com.example.commerce;

import static android.content.ContentValues.TAG;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commerce.adapter.CartItemAdapter;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.ItemRemoved {

    FirebaseFirestore Store;
    FirebaseAuth Auth;
    List<Items> itemsList;
    RecyclerView cartRecyclerView;
    CartItemAdapter cartItemAdapter;
    Button buyItemButton;
    TextView totalAmount;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();
        itemsList=new ArrayList<>();
        cartRecyclerView=findViewById(R.id.cart_item_container);
        buyItemButton=findViewById(R.id.cart_item_buy_now);
        totalAmount=findViewById(R.id.total_amount);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);
        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id == R.id.cart) {
                return true;
            }else if(id == R.id.fav){
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                finish();
                return true;
            }else if(id == R.id.home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
                return true;
            }else if(id == R.id.person) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                finish();
                return true;
            }
            return false;
        });
        buyItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,AddressActivity.class);
                intent.putExtra("itemList",(Serializable) itemsList);
                startActivity(intent);

            }
        });

        cartItemAdapter=new CartItemAdapter(itemsList, this);
        cartRecyclerView.setAdapter(cartItemAdapter);

        Store.collection("Users").document(Auth.getCurrentUser().getUid())
                .collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult()!=null){
                                for (DocumentChange doc :task.getResult().getDocumentChanges()){
                                    String documentId= doc.getDocument().getId();
                                    Items item=doc.getDocument().toObject(Items.class);
                                    item.setDocId(documentId);
                                    itemsList.add(item);
                                }
                                calculateAmount(itemsList);
                                cartItemAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(CartActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onItemRemoved(List<Items> itemsList) {
        calculateAmount(itemsList);
    }

    private void calculateAmount(List<Items> itemsList) {
        double totalAmountInDouble = 0.0;
        for(Items item:itemsList){
            totalAmountInDouble += item.getPrice();
        }
        totalAmount.setText("Total Amount: "+totalAmountInDouble+"€");
    }

}