package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.commerce.adapter.FavItemAdapter;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    FirebaseFirestore Store;
    FirebaseAuth Auth;
    List<Items> itemsList;
    RecyclerView favRecyclerView;
    FavItemAdapter favItemAdapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();
        itemsList=new ArrayList<>();
        favRecyclerView=findViewById(R.id.fav_item_container);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favRecyclerView.setHasFixedSize(true);

        Store.collection("Users").document(Auth.getCurrentUser().getUid())
                .collection("Fav").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (task.getResult()!=null){
                                for(DocumentChange doc:task.getResult().getDocumentChanges()){
                                    String documentId=doc.getDocument().getId();
                                    Items item=doc.getDocument().toObject(Items.class);
                                    item.setDocId1(documentId);
                                    itemsList.add(item);
                                }
                                favItemAdapter=new FavItemAdapter(itemsList);
                                favRecyclerView.setAdapter(favItemAdapter);
                            }

                        } else {
                            Toast.makeText(FavoriteActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.fav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id == R.id.fav) {
                return true;
            }else if(id == R.id.home){
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                finish();
                return true;
            }else if(id == R.id.cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
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
    }
}