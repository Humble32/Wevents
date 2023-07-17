package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.commerce.adapter.ItemsRecyclerAdapter;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private FirebaseFirestore Store;
     private List<Items> ItemsList;
    private RecyclerView itemRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;
    private Toolbar Toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        String name=getIntent().getStringExtra("name");
        String type=getIntent().getStringExtra("type");
        Store=FirebaseFirestore.getInstance();
        ItemsList=new ArrayList<>();
        Toolbar=findViewById(R.id.item_toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setTitle("Items");
        itemRecyclerView=findViewById(R.id.items_recycler);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        itemsRecyclerAdapter=new ItemsRecyclerAdapter(this,ItemsList);
        itemRecyclerView.setAdapter(itemsRecyclerAdapter);

        if(name==null || name.isEmpty()){
            Store.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            ItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(name!=null && name.equalsIgnoreCase("cinema")){
            Store.collection("All").whereEqualTo("type","cinema").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            ItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(name!=null && name.equalsIgnoreCase("concerts")){
            Store.collection("All").whereEqualTo("type","concerts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            ItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(name!=null && name.equalsIgnoreCase("football")){
            Store.collection("All").whereEqualTo("type","football").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            ItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(name!=null && name.equalsIgnoreCase("basketball")){
            Store.collection("All").whereEqualTo("type","basketball").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            ItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item= menu.findItem(R.id.search_it);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchItem(String newText) {
        ItemsList.clear();
        Store.collection("All").whereGreaterThanOrEqualTo("name",newText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Log.i("TAG", "onComplete: " + doc.toString());
                        Items items = doc.toObject(Items.class);
                        ItemsList.add(items);
                        itemsRecyclerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
}