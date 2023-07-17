package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.commerce.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commerce.adapter.ItemsRecyclerAdapter;
import com.example.commerce.domain.Items;
import com.example.commerce.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Fragment homeFragment;
    private Toolbar Toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth Auth;
    private EditText Searchtext;
    private FirebaseFirestore Store;
    private List<Items> ItemsList;
    private RecyclerView ItemRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeFragment=new HomeFragment();
        loadFragment(homeFragment);
        Auth=FirebaseAuth.getInstance();
        Toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(Toolbar);
        bottomNavigationView=findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
                if(id == R.id.home) {
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
                }else if(id == R.id.person) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                }
            return false;
        });
        Searchtext=findViewById(R.id.search_text);
        Store=FirebaseFirestore.getInstance();
        ItemsList=new ArrayList<>();
        ItemRecyclerView=findViewById(R.id.search_recycler);
        ItemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerAdapter=new ItemsRecyclerAdapter(this,ItemsList);
        ItemRecyclerView.setAdapter(itemsRecyclerAdapter);


        Searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    ItemsList.clear();
                    itemsRecyclerAdapter.notifyDataSetChanged();
                } else {
                    searchItem(s.toString());

                }
            }
        });

//        Button btn=findViewById(R.id.logout);
//        btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//              FirebaseAuth.getInstance().signOut();
//              startActivity(new Intent(HomeActivity.this,MainActivity.class));
//              finish();
//              }
//          });
    }

    private void searchItem(String text) {
        if(!text.isEmpty()){
            Store.collection("All").whereGreaterThanOrEqualTo("name",text).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult()!=null){
                                for(DocumentSnapshot doc:task.getResult().getDocuments()){
                                    Items items=doc.toObject(Items.class);
                                    ItemsList.add(items);
                                    itemsRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu,menu);
         getMenuInflater().inflate(R.menu.navigation_menu,menu);
         return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_btn){
            Auth.signOut();
            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.cart){
            Intent intent=new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.fav){
            Intent intent=new Intent(HomeActivity.this,FavoriteActivity.class);
            startActivity(intent);
        }
        return true;
    }
}