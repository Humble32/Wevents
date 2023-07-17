package com.example.commerce.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commerce.ItemsActivity;
import com.example.commerce.R;
import com.example.commerce.adapter.BestSellAdapter;
import com.example.commerce.adapter.CategoryAdapter;
import com.example.commerce.adapter.FeatureAdapter;
import com.example.commerce.domain.BestSell;
import com.example.commerce.domain.Category;
import com.example.commerce.domain.Feature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class   HomeFragment extends Fragment {
    private FirebaseFirestore Store;
    //For Category
    private List<Category> CategoryList;
    private CategoryAdapter CategoryAdapter;
    private RecyclerView CatRecyclerView;

    //For Feature
    private List<Feature> FeatureList;
    private FeatureAdapter FeatureAdapter;
    private RecyclerView FeatureRecyclerView;

    //For BestSell
    private List<BestSell> BestSellList;
    private BestSellAdapter BestSellAdapter;
    private RecyclerView BestSellRecyclerView;
    private TextView SeeAll;
    private TextView Feature;
    private TextView BestSell;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Store=FirebaseFirestore.getInstance();
        SeeAll=view.findViewById(R.id.see_all);
        Feature=view.findViewById(R.id.fea_see_all);
        BestSell=view.findViewById(R.id.best_sell);


        CatRecyclerView=view.findViewById(R.id.category_recycler);
        FeatureRecyclerView=view.findViewById(R.id.feature_recycler);
        BestSellRecyclerView=view.findViewById(R.id.bestsell_recycler);


        //For Category
        CategoryList=new ArrayList<>();
        CategoryAdapter=new CategoryAdapter(getContext(),CategoryList);
        CatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        CatRecyclerView.setAdapter(CategoryAdapter);

        //For Feature
        FeatureList=new ArrayList<>();
        FeatureAdapter=new FeatureAdapter(getContext(),FeatureList);
        FeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        FeatureRecyclerView.setAdapter(FeatureAdapter);

        //For BestSell
        BestSellList=new ArrayList<>();
        BestSellAdapter=new BestSellAdapter(getContext(),BestSellList);
        BestSellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        BestSellRecyclerView.setAdapter(BestSellAdapter);


        Store.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category=document.toObject(Category.class);
                                CategoryList.add(category);
                                CategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        Store.collection("Feature")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Feature feature=document.toObject(Feature.class);
                                FeatureList.add(feature);
                                FeatureAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        Store.collection("BestSell")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BestSell bestSell=document.toObject(BestSell.class);
                                BestSellList.add(bestSell);
                                BestSellAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        BestSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        Feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}